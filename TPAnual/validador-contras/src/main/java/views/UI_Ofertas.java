package views;

import domain.colaboraciones.PresentacionOferta;
import domain.incidente.EnumEstadoDeIncidente;
import domain.incidente.Incidente;
import domain.registro.SingletonSeguidorEstadistica;
import domain.rol.Colaborador;
import domain.servicios.Catalogo;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import obs.RespuestaCliente;

import java.util.List;

public class UI_Ofertas extends UI_Navegable implements Handler {

    @Override
    public void handle(Context ctx) throws Exception {
        this.validarUsuario(ctx);

        Colaborador c = (Colaborador) getUsuario().getRol();
        List<PresentacionOferta> ofertas = Catalogo.getInstance().getOfertas().stream().filter(o -> o.getColaborador().equals(c)).toList();

        this.model.put("ofertas", ofertas);
        ctx.render("ofertas.hbs", this.model);
    }

    public void eliminarOferta(Context ctx) throws Exception {
        String ofertaId = ctx.formParam("ofertaId");
        Catalogo.getInstance().retirarDelCatalogoPorId(Integer.parseInt(ofertaId));

        RespuestaCliente.exito(getUsuario(), "/ofertas", "Se elimino la oferta", ctx);
    }
}
