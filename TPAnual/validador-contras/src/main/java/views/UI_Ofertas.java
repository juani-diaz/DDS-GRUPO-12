package views;

import domain.servicios.Catalogo;
import io.javalin.http.Context;
import io.javalin.http.Handler;

public class UI_Ofertas extends UI_Navegable implements Handler {

    @Override
    public void handle(Context ctx) throws Exception {
        this.validarUsuario(ctx);
        if (this.sesionValida(ctx)) {
            this.model.put("ofertas", Catalogo.getOfertas());
            ctx.render("ofertas.hbs", this.model);
        }
    }
}
