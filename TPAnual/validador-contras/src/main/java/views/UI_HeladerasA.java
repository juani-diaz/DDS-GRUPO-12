package views;


import domain.heladera.EnumEstadoHeladera;
import domain.heladera.Heladera;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import obs.RespuestaCliente;
import persistence.Repos.RepoHeladera;

public class UI_HeladerasA extends UI_Navegable implements Handler{

    @Override
    public void handle(Context ctx) throws Exception {
        this.validarUsuario(ctx);

        RepoHeladera hela = RepoHeladera.getInstance();
        model.put("hela", hela.getHeladeras());
        ctx.render("heladeras-a.hbs", this.model);
    }

    public void instalarHeladera(Context ctx) {
        RepoHeladera repo = RepoHeladera.getInstance();
        Integer helaId = Integer.valueOf(ctx.formParam("helaId"));
        Heladera hela = repo.findById_Heladera(helaId);
        hela.setEstado(EnumEstadoHeladera.DISPONIBLE);

        repo.updateHeladera(hela);

        RespuestaCliente.exito(getUsuario(), "/heladeras-a", "Se aprobo la instalacion de la heladera", ctx);
    }
}



