package views;

import domain.auth.JwtUtil;
import domain.auth.Usuario;
import domain.rol.Tecnico;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import lombok.NoArgsConstructor;
import obs.RespuestaCliente;
import persistence.Repos.RepoUsuarios;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
public class UI_Login  implements Handler {

    public void handle(Context ctx) throws Exception {
        String token = ctx.cookie("Auth");
        if(token != null) {
            token = token.replace("Bearer", "");
            String decodedToken = URLDecoder.decode(token, StandardCharsets.UTF_8);
            JwtUtil.invalidateToken(decodedToken);
        }
        ctx.removeCookie("Auth");
        Map<String, Object> model = new HashMap();
        ctx.render("page-login.hbs", model);
    }

    public void login(Context ctx) throws Exception {
        String usuario = ctx.formParam("usuario");
        String contra = ctx.formParam("contra");
        RepoUsuarios r = RepoUsuarios.getInstance(); //linea 27, donde ocurre el error
        Usuario u = r.findByUsuario(usuario);

        if (u != null && u.getContra().equals(contra)) {
            if(u.getRol().getClass() == Tecnico.class){
                Tecnico t = (Tecnico) u.getRol();
                if(t.getAprobadoPorAdmin() == null || !t.getAprobadoPorAdmin()){
                    RespuestaCliente.error(u, "/page-login", "Cuenta no aprobada por el administrador", ctx);
                    return;
                }
            }
            String token = JwtUtil.generateToken(usuario,u.getRol().getId());
            String encodedToken = URLEncoder.encode(token, StandardCharsets.UTF_8);
            ctx.cookie("Auth", "Bearer" + encodedToken);
            RespuestaCliente.exito(u, "/index", "Logeado correctamente", ctx);
        } else {
            RespuestaCliente.error(u, "/page-login", "Usuario inexistente o contraseña incorrecta", ctx);
        }

    }

    public void logout(Context ctx) {
        String token = ctx.cookie("Auth");
        if(token != null) {
            token = token.replace("Bearer", "");
            String decodedToken = URLDecoder.decode(token, StandardCharsets.UTF_8);
            JwtUtil.invalidateToken(decodedToken);
        }
        ctx.removeCookie("Auth");
        ctx.redirect("/page-login");
    }
}
