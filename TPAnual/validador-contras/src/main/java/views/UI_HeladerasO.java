package views;


import io.javalin.http.Context;
import io.javalin.http.Handler;
import persistence.Repos.RepoHeladera;

public class UI_HeladerasO extends UI_Navegable implements Handler{

    @Override
    public void handle(Context ctx) throws Exception {
        this.validarUsuario(ctx);

        RepoHeladera hela = RepoHeladera.getInstance();
        this.model.put("hela", hela.getHeladeras());
        ctx.render("heladeras-o.hbs", this.model);
    }

}



