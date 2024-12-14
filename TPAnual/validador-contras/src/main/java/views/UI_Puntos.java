package views;

import domain.auth.JwtUtil;
import domain.rol.Colaborador;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.jsonwebtoken.Claims;
import persistence.BDUtils;
import persistence.Repos.RepoColaborador;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;


public class UI_Puntos extends UI_Navegable implements Handler {


    EntityManager em = BDUtils.getEntityManager();
    @Override
    public void handle(Context ctx) throws Exception {
        this.validarUsuario(ctx);
        if (this.sesionValida(ctx)) {
            String token = ctx.cookie("Auth");
            Claims claims= JwtUtil.getClaimsFromToken(token);
            RepoColaborador cola = RepoColaborador.getInstance();

            Float puntos=cola.obtenerPuntosxColaborador((Integer) claims.get("roleId"));
            Map<String, Object> model = new HashMap<>();
            model.put("colaPuntos", puntos);

            System.out.println(model);
            ctx.render("puntos.hbs", model);
        }
    }

    public void canjearPuntos(Context ctx) throws Exception {
        this.validarUsuario(ctx);
        if (this.sesionValida(ctx)) {
            String token = ctx.cookie("Auth");
            Claims claims= JwtUtil.getClaimsFromToken(token);
            RepoColaborador cola = RepoColaborador.getInstance();

            Colaborador colaborador=cola.findById_Colaborador((Integer) claims.get("roleId"));
            //necesito el id del catalogo que no esta implementado, implementar catalogo
            colaborador.realizarCanje(0); //pongo 0 asi no rompe


            ctx.render("puntos.hbs", model);
        }
    }




}






