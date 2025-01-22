package views;


import domain.heladera.Heladera;
import domain.rol.Colaborador;
import domain.servicios.TwilioSendGrid;
import domain.suscripcion.MuchasViandas;
import domain.suscripcion.NoFunciona;
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

/*    public void botonesInfo(Context ctx) throws IOException {
        System.out.println("estoy en UI_HeladerasP::subscribirse");

        // Obtener parámetros del formulario (datos enviados en la solicitud)
        String tipo_sub_dropdown = ctx.formParam("buton_sub");
        System.out.println("tipo_sub_dropdown = " + tipo_sub_dropdown);

        String helaID = ctx.formParam("helaID");
        System.out.println("helaID = " + helaID);

        String heladeraID_buton_falla = ctx.formParam("buton_falla");
        System.out.println("heladeraID_buton_falla = " + heladeraID_buton_falla);


        if (tipo_sub_dropdown != null) {
            System.out.println("pasajeSub = " + tipo_sub_dropdown);

            //Heladera heladera = extracted(helaID);

            this.botonSuscribe(helaID, tipo_sub_dropdown);
        }
        if (heladeraID_buton_falla != null) {
            System.out.println("pasajeFalla = " + heladeraID_buton_falla);
            Heladera heladera = extracted(heladeraID_buton_falla);

            //this.falla(heladera);
        }

        //ctx.render("index.hbs");
    }*/

    public void falla(Context ctx) throws IOException{
        System.out.println("estoy en UI_HeladerasP::falla");

        String heladeraID_buton_falla = ctx.formParam("buton_falla");
        Heladera hela = extracted(heladeraID_buton_falla);

        System.out.println("estoy en UI_HeladerasP::falla con la heladera -> "+ hela.getNombre() + " con el User rol: "+ this.getUsuario().getRol().getPersona().getNombre());

        ctx.render("index.hbs");
    }

    public void botonSuscribe(Context ctx) throws IOException{
        System.out.println("estoy en UI_HeladerasP::botonSuscribe");

        // Obtener parámetros del formulario (datos enviados en la solicitud)
        String tipo_sub = ctx.formParam("buton_sub");
        System.out.println("tipo_sub_dropdown = " + tipo_sub);

        String hela = ctx.formParam("helaID");
        System.out.println("helaID = " + hela);

        System.out.println("estoy en UI_HeladerasP::botonSuscribe con la heladeraID -> "+ hela + " con el User rol: "+ this.getUsuario().getRol().getPersona().getNombre());
        Colaborador colaborador = (Colaborador) this.getUsuario().getRol();

        System.out.println(colaborador.getPersona().getNombre() +
            " tiene alguna suscripcion de tipo "+ tipo_sub+ " en la heladeraID "+hela+"? -> "+
            colaborador.getSuscripciones().stream().anyMatch(s -> s.getHeladera().getId() == Long.parseLong(hela) && s.getClass().getName()==tipo_sub));//TODO:Esta pregunta responde mal

        System.out.println(colaborador.getPersona().getNombre() +
            " tiene alguna suscripcion en la heladera con Id "+ hela+"? ->"+ colaborador.getSuscripciones().stream().anyMatch(s->Integer.toString(s.getHeladera().getId())== hela));//TODO:Esta pregunta responde mal

        System.out.println(colaborador.getPersona().getNombre() +
            " tiene alguna suscripcion en la heladera con Id "+ hela+" de tipo "+tipo_sub+"? ->"+ colaborador.getSuscripciones().stream().anyMatch(s->s.getClass().getName()==tipo_sub));//TODO:Esta pregunta responde mal


        if(colaborador.getSuscripciones().stream().anyMatch(s -> Integer.toString(s.getHeladera().getId()) == hela && s.getClass().getName()==tipo_sub)){
            this.desuscribirse(colaborador, hela, tipo_sub);
        } else this.suscribirse(colaborador, hela, tipo_sub);

        ctx.render("index.hbs");
    }

    private void suscribirse(Colaborador c, String hela, String tipo_sub) throws IOException {
        System.out.println("Estoy en UI_HeladerasP::subscribirse");

        Suscripcion suscripcion = null;

        System.out.println(extracted(hela).getTamanioEnViandas()/5);

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

        //TwilioSendGrid.sendEmail("jpolito@frba.utn.edu.ar", subject, mensaje);
    }

    private void desuscribirse(Colaborador colaborador, String hela, String tipo_sub) throws IOException {

        System.out.println("Estoy en DESUSCRIBIRSE");

        Stream<Suscripcion> suscripcionList = colaborador.getSuscripciones().stream().filter(
                                                        suscripcion -> suscripcion.getHeladera().getId() == Long.parseLong(hela) &&
                                                                       suscripcion.getClass().getName()== tipo_sub);

        Suscripcion sus = suscripcionList.toList().get(0); //TODO: Que filtre por tipo de suscripcion

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

        //TwilioSendGrid.sendEmail("jpolito@frba.utn.edu.ar", subject, mensaje);
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



