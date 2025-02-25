package domain.auth;

import domain.persona.Persona;
import domain.persona.PersonaFisica;
import domain.persona.PersonaJuridica;
import domain.rol.Administrador;
import domain.rol.Colaborador;
import domain.rol.Rol;
import domain.rol.Tecnico;
import io.javalin.http.Context;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccesoUsuarios {
    private Map<String, PermisosMetodo> rutas;

    private static AccesoUsuarios instance;
    
    public void agregarRuta(PermisosMetodo ruta){
        this.rutas.put(ruta.getHref(), ruta);
    }

    private AccesoUsuarios(){
        rutas = new HashMap<String, PermisosMetodo>();
    }

    public List<LinkMenu> getMenuParaUsuario(Usuario u){
        List<LinkMenu> menu = new ArrayList<LinkMenu>();
        menu = (List<LinkMenu>) (List<?>) rutas.values().stream().filter(r -> r.getClass() == LinkMenu.class && u.puedeAcceder(r)).toList();
        return menu;
    }

    public void revisarPermiso(Context ctx) {
        String path = ctx.path();
        path = path.substring(1);
        PermisosMetodo ruta = rutas.get(path);
        if(ruta != null){
            String token = ctx.cookie("Auth");
            if (token != null) {
                token = token.replace("Bearer", "");
                String decodedToken = URLDecoder.decode(token, StandardCharsets.UTF_8);
                Usuario u = JwtUtil.validateTokenAndGetUser(decodedToken);
                if(!u.puedeAcceder(ruta)){
                    ctx.status(403).result("No tenés permiso para acceder a esta página...");
                    System.out.println("Denegué en AccesoUsuarios");
                    ctx.redirect("/denegado");
                    return;
                }
            }
        }
    }

    public static AccesoUsuarios getInstance(){
        if(instance == null){
            instance = new AccesoUsuarios();
        }
        return instance;
    }
}
