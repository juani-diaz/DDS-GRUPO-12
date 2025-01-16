package views;


import domain.heladera.Heladera;
import domain.rol.Colaborador;
import domain.servicios.TwilioSendGrid;
import domain.suscripcion.PocasViandas;
import domain.suscripcion.Suscripcion;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import persistence.Repos.RepoColaborador;
import persistence.Repos.RepoHeladera;
import persistence.Repos.RepoSuscripcion;

import java.io.IOException;
import java.util.stream.Stream;

public class UI_HeladerasP extends UI_Navegable implements Handler{

    @Override
    public void handle(Context ctx) throws Exception {
        this.validarUsuario(ctx);

        RepoHeladera hela = RepoHeladera.getInstance();
        model.put("hela", hela.getHeladeras());
        ctx.render("heladeras-p.hbs", this.model);
    }

    public void botonesInfo(Context ctx) throws IOException {
        System.out.println("estoy en UI_HeladerasP::subscribirse");

        // Obtener parámetros del formulario (datos enviados en la solicitud)
        String heladeraID_buton_sub = ctx.formParam("buton_sub");
        System.out.println("heladeraID_buton_sub = " + heladeraID_buton_sub);

        // Obtener parámetros del formulario (datos enviados en la solicitud)
        String heladeraID_buton_falla = ctx.formParam("buton_falla");
        System.out.println("heladeraID_buton_falla = " + heladeraID_buton_falla);



        if (heladeraID_buton_sub != null) {
            System.out.println("pasajeSub = " + heladeraID_buton_sub);
            Heladera heladera = extracted(heladeraID_buton_sub);

            this.botonSuscribe(heladera);
        }
        if (heladeraID_buton_falla != null) {
            System.out.println("pasajeFalla = " + heladeraID_buton_falla);
            Heladera heladera = extracted(heladeraID_buton_falla);

            this.falla(heladera);
        }

        ctx.render("index.hbs");
    }

    private void falla(Heladera hela) {

        System.out.println("estoy en UI_HeladerasP::falla con la heladera -> "+ hela.getNombre() + " con el User rol: "+ this.getUsuario().getRol().getPersona().getNombre());
    }

    private void botonSuscribe(Heladera hela) throws IOException {
        System.out.println("estoy en UI_HeladerasP::subscribir con la heladera -> "+ hela.getNombre() + " con el User rol: "+ this.getUsuario().getRol().getPersona().getNombre());
        Colaborador colaborador = (Colaborador) this.getUsuario().getRol();

        if(colaborador.getSuscripciones().stream().anyMatch(s -> s.getHeladera() == hela)){
            this.desuscribirse(colaborador, hela);
        } else this.suscribirse(colaborador, hela);


    }

    private void suscribirse(Colaborador c, Heladera hela) throws IOException {
        PocasViandas suscripcion = new PocasViandas(hela);
        suscripcion.setColaborador(c);

        RepoSuscripcion.getInstance().add_Suscripcion(suscripcion);

        c.getSuscripciones().add(suscripcion);
        RepoColaborador.getInstance().actualizarColaborador(c);

        String subject =
            "Suscripcion a heladera "+ hela.getNombre();
        String mensaje =
            "En hora buena "+ this.getUsuario().getRol().getPersona().getNombre() +
                " acaba de suscribirse a la heladera " + hela.getNombre() +
                " de ahora en mas podra recibir todas las notificaciones pertinentes a dicha heladera!";

        TwilioSendGrid.sendEmail("jpolito@frba.utn.edu.ar", subject, mensaje);
    }

    private void desuscribirse(Colaborador colaborador, Heladera hela) throws IOException {

        Stream<Suscripcion> suscripcionList = colaborador.getSuscripciones().stream().filter(suscripcion -> suscripcion.getHeladera()==hela);
        Suscripcion sus = suscripcionList.toList().get(0); //TODO: Que filtre por tipo de suscripcion

        System.out.println(sus.getHeladera().getNombre() +"  "+ sus.getMensaje());

        colaborador.getSuscripciones().remove(sus);
        RepoColaborador.getInstance().actualizarColaborador(colaborador);

        RepoSuscripcion.getInstance().remove_Suscripcion(sus);

        String subject =
            "Desuscribido de heladera "+ hela.getNombre();
        String mensaje =
            "Hola "+ this.getUsuario().getRol().getPersona().getNombre() +
                " acabas de dessuscribirse de la heladera " + hela.getNombre() +
                " de ahora en mas no recibiras notificaciones de dihca heladera";

        TwilioSendGrid.sendEmail("jpolito@frba.utn.edu.ar", subject, mensaje);
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



