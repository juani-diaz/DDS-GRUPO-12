package views;

import domain.auth.JwtUtil;
import domain.rol.Colaborador;
import domain.servicios.Catalogo;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.jsonwebtoken.Claims;
import persistence.Repos.RepoColaborador;


public class UI_Puntos extends UI_Navegable implements Handler {

    @Override
    public void handle(Context ctx) throws Exception {
        this.validarUsuario(ctx);
        if (this.sesionValida(ctx)) {
            String token = ctx.cookie("Auth");
            Claims claims= JwtUtil.getClaimsFromToken(token);
            RepoColaborador cola = RepoColaborador.getInstance();

            Float puntos=cola.obtenerPuntosxColaborador((Integer) claims.get("roleId"));
            model.put("colaPuntos", puntos);
            this.model.put("ofertas", Catalogo.getInstance().getOfertas());

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






