package views;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UI_Index extends UI_Navegable implements Handler {
    public void handle(Context ctx) throws Exception {
        this.validarUsuario(ctx);
        if (this.sesionValida()) {
            ctx.render("index.hbs", this.model);
        }

    }
}
