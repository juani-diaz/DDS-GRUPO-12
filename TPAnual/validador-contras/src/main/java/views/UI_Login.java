package views;

import domain.auth.JwtUtil;
import domain.auth.Usuario;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Jwts;
import lombok.NoArgsConstructor;
import persistence.Repos.RepoUsuarios;

@NoArgsConstructor
public class UI_Login  implements Handler {

    public void handle(Context ctx) throws Exception {

        Map<String, Object> model = new HashMap();
        ctx.render("page-login.hbs", model);

    }

    public void login(Context ctx) throws Exception {
        String usuario = ctx.formParam("usuario");
        String contra = ctx.formParam("contra");
        RepoUsuarios r = RepoUsuarios.getInstance();
        Usuario u = r.findByUsuario(usuario);
        if (u != null && u.getContra().equals(contra)) {
            String token = JwtUtil.generateToken(usuario,u.getRol().getId());
            String encodedToken = URLEncoder.encode(token, StandardCharsets.UTF_8);
            ctx.cookie("Auth", "Bearer" + encodedToken);
            ctx.redirect("/index");
        } else {
            ctx.redirect("/page-login");
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
