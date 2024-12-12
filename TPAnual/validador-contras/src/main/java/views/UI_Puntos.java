package views;

import domain.auth.JwtUtil;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.jsonwebtoken.Claims;
import persistence.BDUtils;
import persistence.Repos.RepoColaborador;
import persistence.Repos.RepoHeladera;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UI_Puntos extends UI_Navegable implements Handler {


    EntityManager em = BDUtils.getEntityManager();
    @Override
    public void handle(Context ctx) throws Exception {
        this.validarUsuario(ctx);
        if (this.sesionValida(ctx)) {
            String token = ctx.cookie("Auth");
            Claims claims= JwtUtil.getClaimsFromToken(token);
            RepoColaborador cola = new RepoColaborador(em);

            Float puntos=cola.obtenerPuntosxColaborador((Integer) claims.get("roleId"));
            Map<String, Object> model = new HashMap<>();
            model.put("colaPuntos", puntos);

            System.out.println(model);
            ctx.render("puntos.hbs", model);
        }
    }



}






