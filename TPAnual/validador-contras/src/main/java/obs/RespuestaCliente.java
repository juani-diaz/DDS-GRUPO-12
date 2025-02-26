package obs;

import domain.auth.Usuario;
import io.javalin.http.Context;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class RespuestaCliente {
    public static void exito(Usuario u, String url, String mensaje, Context ctx){
        String exitoMessage = URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
        ctx.cookie("obs_exito", exitoMessage);
        Logger.print(u, mensaje);
        ctx.redirect(url);
    }

    public static void error(Usuario u, String url, String mensaje, Context ctx){
        String errorMessage = URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
        ctx.cookie("obs_error", errorMessage);
        Logger.print(u, mensaje);
        ctx.redirect(url);
    }

    public static void fetchSub(Usuario u, String url, String mensaje, Context ctx){
        String exitoMessage = URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
        ctx.cookie("obs_exito", exitoMessage);
        Logger.print(u, mensaje);

        // Responder JSON en lugar de redirigir
        ctx.json(Map.of("status", "success", "message", mensaje, "redirectUrl", url));
    }
}
