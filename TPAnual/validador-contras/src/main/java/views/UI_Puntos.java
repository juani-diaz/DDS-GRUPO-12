package views;

import domain.auth.JwtUtil;
import domain.colaboraciones.PresentacionOferta;
import domain.rol.Colaborador;
import domain.servicios.Catalogo;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.jsonwebtoken.Claims;
import obs.RespuestaCliente;
import persistence.Repos.RepoColaborador;


public class UI_Puntos extends UI_Navegable implements Handler {

    @Override
    public void handle(Context ctx) throws Exception {
        this.validarUsuario(ctx);

        Colaborador cola = (Colaborador) getUsuario().getRol();

        cola.actualizarPuntos();

        model.put("colaPuntos", cola.getCantidadPuntos());
        this.model.put("ofertas", Catalogo.getInstance().getOfertas());

        System.out.println(model);
        ctx.render("puntos.hbs", model);
    }

    public void canjearPuntos(Context ctx) throws Exception {
        this.validarUsuario(ctx);

        Colaborador colaborador = (Colaborador) getUsuario().getRol();

        int ofertaId = Integer.parseInt(ctx.formParam("ofertaId"));

        PresentacionOferta o = Catalogo.getInstance().getOfertaById(ofertaId);

        colaborador.realizarCanje(o);

        colaborador.actualizarPuntos();

        RepoColaborador.getInstance().actualizarColaborador(colaborador);

        RespuestaCliente.exito(getUsuario(), "/puntos", "Canje exitoso", ctx);
    }
}
