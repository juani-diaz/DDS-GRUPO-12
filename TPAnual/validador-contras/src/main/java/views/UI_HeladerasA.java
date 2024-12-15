package views;


import io.javalin.http.Context;
import io.javalin.http.Handler;
import persistence.Repos.RepoHeladera;

public class UI_HeladerasA extends UI_Navegable implements Handler{

    @Override
    public void handle(Context ctx) throws Exception {
        this.validarUsuario(ctx);
        if (this.sesionValida(ctx)) {
            RepoHeladera hela = RepoHeladera.getInstance();
            model.put("hela", hela.getHeladeras());
            ctx.render("heladeras-a.hbs", this.model);
        }
    }

    public void botonInfo(Context ctx) {
        System.out.println("estoy en UI_HeladerasA::botonInfo");

        // Obtener parámetros del formulario (datos enviados en la solicitud)
        String buton_contactar = ctx.formParam("buton_contactar");
        System.out.println("HeladeraID: " + buton_contactar);

        ctx.render("index.hbs");
    }
}



