package views;

import domain.auth.AccesoUsuarios;
import domain.auth.JwtUtil;
import domain.auth.LinkMenu;
import domain.auth.Usuario;
import domain.rol.Colaborador;
import domain.rol.Tecnico;
import io.javalin.http.Context;
import io.jsonwebtoken.Claims;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import persistence.Repos.RepoColaborador;
import persistence.Repos.RepoUsuarios;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor @Getter @Setter
public class UI_Navegable {
    Map<String, Object> model = new HashMap();
    private Usuario usuario;

    void validarUsuario(Context ctx) {
        String token = ctx.cookie("Auth");
        if (token != null) {
            token = token.replace("Bearer", "");
            String decodedToken = URLDecoder.decode(token, StandardCharsets.UTF_8);
            Usuario u = JwtUtil.validateTokenAndGetUser(decodedToken);
            this.usuario = u;
            this.model.put("usuario", u);
            String urlUploads = getUrlUploads(ctx);
            this.model.put("urlUploads", urlUploads);
            List<LinkMenu> menu = AccesoUsuarios.getInstance().getMenuParaUsuario(u);
            this.model.put("menu", menu);
        }
        validarSesion(ctx);
    }

    String getUrlUploads(Context ctx){
        String urlBase = ctx.scheme() + "://" + ctx.host();
        urlBase = urlBase + "/uploads";
        return urlBase;
    }

     boolean sesionValida(Context ctx) {
        String token = ctx.cookie("Auth");
        if (token != null) {
            token = token.replace("Bearer", "");
            String decodedToken = URLDecoder.decode(token, StandardCharsets.UTF_8);
            return JwtUtil.validateToken(decodedToken) != null;
        }
        return false;
    }

    boolean vieneDeGoogle(Context ctx) {
        String token = ctx.cookie("Auth");
        token = token.replace("Bearer", "");
        String decodedToken = URLDecoder.decode(token, StandardCharsets.UTF_8);
        Usuario u = JwtUtil.validateTokenAndGetUser(decodedToken);
        return u.getRol().getPersona().getDireccion()==null;
    }

     void validarSesion(Context ctx) {
        if(!sesionValida(ctx)){
            ctx.status(403).result("No tenés permiso para acceder a esta página...");
            System.out.println("Denegué en UI_Navegable");
            ctx.redirect("/denegado");
            return;
        } else if (vieneDeGoogle(ctx)) {
            ctx.status(403).result("Debes completar tus datos primero...");
            ctx.redirect("/page-register");
            return;
        }
     }
}
