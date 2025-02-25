package views;


import domain.heladera.Heladera;
import domain.rol.Colaborador;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import persistence.Repos.RepoHeladera;

import java.util.List;

public class UI_HeladerasO extends UI_Navegable implements Handler{

    @Override
    public void handle(Context ctx) throws Exception {
        this.validarUsuario(ctx);

        RepoHeladera hela = RepoHeladera.getInstance();

        List<Heladera> heladeras = hela.getHeladeras();

        Colaborador c = (Colaborador) getUsuario().getRol();

        this.model.put("hela", heladeras);
        this.model.put("helaPropia", heladeras.stream().filter(
                h -> h.getResponsable() != null && h.getResponsable().equals(c)
        ).toList());
        ctx.render("heladeras-o.hbs", this.model);
    }

}



