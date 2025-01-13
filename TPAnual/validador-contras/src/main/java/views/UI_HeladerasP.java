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

            this.subscribir(heladera);
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

    private void subscribir(Heladera hela) throws IOException {
        System.out.println("estoy en UI_HeladerasP::subscribir con la heladera -> "+ hela.getNombre() + " con el User rol: "+ this.getUsuario().getRol().getPersona().getNombre());

        PocasViandas suscripcion = new PocasViandas(hela);
        RepoSuscripcion.getInstance().add_Suscripcion(suscripcion);

        Colaborador c = RepoColaborador.getInstance().findByUsuario(getUsuario().getUsuario());
        c.getSuscripciones().add(suscripcion);
        RepoColaborador.getInstance().actualizarColaborador(c);

        String subject =
            "Suscripcion a heladera "+ hela.getNombre();
        String mensaje =
            "En hora buena "+ this.getUsuario().getRol().getPersona().getNombre() +
            " acaba de suscribirse a la heladera " + hela.getNombre() +
            " de ahora en mas podra recibir todas las notificaciones pertinentes a dicha heladera!";

        TwilioSendGrid.senEmail("jpolito@frba.utn.edu.ar", subject, mensaje);
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



