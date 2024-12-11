package views;


import io.javalin.http.Context;
import io.javalin.http.Handler;
import persistence.Repos.RepoHeladera;

public class UI_HeladerasO extends UI_Navegable implements Handler{

    @Override
    public void handle(Context ctx) throws Exception {

        this.validarUsuario(ctx);
        if (this.sesionValida(ctx)) {

            RepoHeladera hela = new RepoHeladera();

            this.model.put("hela", hela.getAll_Heladera());
            ctx.render("heladeras-o.hbs", this.model);

        }
    }

}



