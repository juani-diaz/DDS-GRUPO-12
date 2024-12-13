package views;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class UI_Ofertas extends UI_Navegable implements Handler {
    @Override
    public void handle(Context ctx) throws Exception {
        this.validarUsuario(ctx);
        if (this.sesionValida(ctx)) {
            ctx.render("ofertas.hbs");
        }
    }
}
