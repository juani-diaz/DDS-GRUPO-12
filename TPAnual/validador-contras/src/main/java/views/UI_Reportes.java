package views;


import domain.heladera.Heladera;
import domain.vianda.EnumEstadoVianda;
import domain.vianda.Vianda;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import persistence.Repos.RepoHeladera;
import persistence.Repos.RepoVianda;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class UI_Reportes implements Handler{

    @Override
    public void handle(Context ctx) throws Exception {

        Map<String, Object> model = new HashMap<>();
        //model.put("hela", hela.getAll_Heladera());
        ctx.render("reportes.hbs", model);
    }

}
