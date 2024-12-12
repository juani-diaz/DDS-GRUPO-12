package views;


import domain.heladera.Heladera;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import persistence.Repos.RepoHeladera;

import java.util.HashMap;
import java.util.Map;

public class UI_HeladerasP extends UI_Navegable implements Handler{

    @Override
    public void handle(Context ctx) throws Exception {

        this.validarUsuario(ctx);
        if (this.sesionValida(ctx)) {

            RepoHeladera hela = new RepoHeladera();

            model.put("hela", hela.getAll_Heladera());
            ctx.render("heladeras-p.hbs", this.model);

        }
    }

    public void botonesInfo(Context ctx) {
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

    private void subscribir(Heladera hela) {

        System.out.println("estoy en UI_HeladerasP::subscribir con la heladera -> "+ hela.getNombre() + " con el User rol: "+ this.getUsuario().getRol().getPersona().getNombre());
    }

    private Heladera extracted(String heladeraId) {
        // Convertir parámetros necesarios
        Integer heladeraID = Integer.parseInt(heladeraId);

        // Busca la heladera en la BD
        RepoHeladera hela = new RepoHeladera();
        Heladera heladera = hela.findById_Heladera(heladeraID);
        System.out.println("HelaName= "+heladera.getNombre());
        return heladera;
    }
}



