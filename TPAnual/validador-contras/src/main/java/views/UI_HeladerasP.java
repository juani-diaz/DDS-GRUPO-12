package views;


import domain.heladera.Heladera;
import domain.rol.Colaborador;
import domain.servicios.TwilioSendGrid;
import domain.suscripcion.*;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import persistence.Repos.RepoColaborador;
import persistence.Repos.RepoHeladera;
import persistence.Repos.RepoSuscripcion;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public class UI_HeladerasP extends UI_Navegable implements Handler{

    @Override
    public void handle(Context ctx) throws Exception {
        this.validarUsuario(ctx);

        RepoHeladera hela = RepoHeladera.getInstance();
        model.put("hela", hela.getHeladeras());
        ctx.render("heladeras-p.hbs", this.model);
    }

    public void botonSuscribe(Context ctx) throws IOException{
        System.out.println("estoy en UI_HeladerasP::botonSuscribe");

        // Obtener parámetros del formulario (datos enviados en la solicitud)
        String tipo_sub = ctx.formParam("buton_sub");
        System.out.println("tipo_sub_dropdown = " + tipo_sub);

        String hela = ctx.formParam("helaID");
        System.out.println("helaID = " + hela);

        Colaborador colaborador = (Colaborador) this.getUsuario().getRol();

        if(colaborador.getSuscripciones().stream().anyMatch(
            s -> hela.equals(Integer.toString(s.getHeladera().getId())) &&
                ("domain.suscripcion."+tipo_sub).equals(s.getClass().getName()))){
            this.desuscribirse(colaborador, hela, tipo_sub);
        } else this.suscribirse(colaborador, hela, tipo_sub);

        ctx.render("index.hbs");
    }

    private void suscribirse(Colaborador c, String hela, String tipo_sub) throws IOException {
        System.out.println("Estoy en UI_HeladerasP::subscribirse");

        Suscripcion suscripcion = null;

        switch (tipo_sub){
            case "PocasViandas":
                suscripcion = new PocasViandas(extracted(hela));
                break;
            case "MuchasViandas":
                suscripcion = new MuchasViandas(extracted(hela));
                break;
            default:
                suscripcion = new NoFunciona(extracted(hela));
        }

        suscripcion.setColaborador(c);
        RepoSuscripcion.getInstance().add_Suscripcion(suscripcion);

        c.getSuscripciones().add(suscripcion);
        RepoColaborador.getInstance().actualizarColaborador(c);

        String subject =
            "Suscripcion a heladeraID "+ hela;
        String mensaje =
            "En hora buena "+ this.getUsuario().getRol().getPersona().getNombre() +
                " acaba de suscribirse a la heladeraID " + hela +
                " de ahora en mas podra recibir todas las notificaciones pertinentes a su suscripcion del tipo "
                + tipo_sub + "!";

        Publicador publisher= new Publicador();
        publisher.addObservable(suscripcion);
        System.out.println(this.getUsuario().getRol().getPersona().getMediosDeContacto());
        TwilioSendGrid.sendEmail("juanidiaz8260@gmail.com", subject, mensaje);

    }

    private void desuscribirse(Colaborador colaborador, String hela, String tipo_sub) throws IOException {

        System.out.println("Estoy en DESUSCRIBIRSE");

        Suscripcion sus = colaborador.getSuscripciones().stream().filter(
            s -> hela.equals(Integer.toString(s.getHeladera().getId())) &&
                ("domain.suscripcion."+tipo_sub).equals(s.getClass().getName()))
            .reduce((first, second) -> first)
            .orElse(null);

        System.out.println("suscripcion.getClass().getName()   "+ sus.getClass().getName());
        System.out.println(sus.getHeladera().getNombre() +"  "+ sus.getMensaje());

        colaborador.getSuscripciones().remove(sus);
        RepoColaborador.getInstance().actualizarColaborador(colaborador);

        RepoSuscripcion.getInstance().remove_Suscripcion(sus);

        String subject =
            "Desuscribido de heladeraID "+ hela;
        String mensaje =
            "Hola "+ this.getUsuario().getRol().getPersona().getNombre() +
                " acabas de dessuscribirse de la heladeraID " + hela+
                " de ahora en mas no recibiras notificaciones de dihca heladera";

        System.out.println(this.getUsuario().getRol().getPersona().getMediosDeContacto().get(0).getContacto());
        TwilioSendGrid.sendEmail(this.getUsuario().getRol().getPersona().getMediosDeContacto().get(0).getContacto(), subject, mensaje);
    }

    private Heladera extracted(String heladeraId) {
        // Convertir parámetros necesarios
        Integer heladeraID = Integer.parseInt(heladeraId);

        // Busca la heladera en la BD
        RepoHeladera hela = RepoHeladera.getInstance();
        Heladera heladera = hela.findById_Heladera(heladeraID);
        System.out.println("HelaName= "+heladera.getNombre());
        return heladera;
    }

}



