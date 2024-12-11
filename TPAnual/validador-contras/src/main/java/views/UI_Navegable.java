package views;

import domain.auth.JwtUtil;
import domain.auth.Usuario;
import io.javalin.http.Context;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
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
            this.model.put("usuario", u);
            this.usuario = u;
        }

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
}
