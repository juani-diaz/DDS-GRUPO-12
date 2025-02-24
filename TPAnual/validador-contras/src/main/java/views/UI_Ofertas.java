package views;

import domain.incidente.EnumEstadoDeIncidente;
import domain.incidente.Incidente;
import domain.registro.SingletonSeguidorEstadistica;
import domain.servicios.Catalogo;
import io.javalin.http.Context;
import io.javalin.http.Handler;

public class UI_Ofertas extends UI_Navegable implements Handler {

    @Override
    public void handle(Context ctx) throws Exception {
        this.validarUsuario(ctx);

        this.model.put("ofertas", Catalogo.getInstance().getOfertas());
        ctx.render("ofertas.hbs", this.model);
    }

    public void eliminarOferta(Context ctx) throws Exception {
        String ofertaId = ctx.formParam("ofertaId");
        Catalogo.getInstance().retirarDelCatalogoPorId(Integer.parseInt(ofertaId));

        ctx.redirect("/ofertas");
    }
}
