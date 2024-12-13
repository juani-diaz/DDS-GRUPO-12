package views;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import persistence.BDUtils;
import persistence.Repos.RepoColaborador;
import persistence.Repos.RepoHeladera;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UI_Reporte extends UI_Navegable implements Handler {


    EntityManager em = BDUtils.getEntityManager();
    @Override
    public void handle(Context ctx) throws Exception {

        this.validarUsuario(ctx);
        System.out.println(ctx);
        if (this.sesionValida(ctx)) {
            RepoHeladera hela = RepoHeladera.getInstance();
            RepoColaborador cola = RepoColaborador.getInstance();

            Map<String, Object> model = new HashMap<>();
            model.put("helaFallos", hela.obtenerFallasxHeladera());
            model.put("colaboradorDatos", cola.obtenerDonacionesxColaborador());

            System.out.println(model);
            ctx.render("reportes.hbs", this.model);
        }

    }


}






