package views;

import domain.auth.JwtUtil;

import domain.auth.Usuario;
import domain.rol.Colaborador;

import domain.rol.Tarjeta;

import io.javalin.http.Context;

import io.jsonwebtoken.Claims;

import persistence.Repos.RepoColaborador;


import domain.heladera.Heladera;
import domain.heladera.Ubicacion;
import persistence.Repos.RepoUsuarios;


import java.util.List;

public class HelperSource {
    public String lat_lon(Ubicacion u) {
        return u.getLatitud()+","+u.getLongitud();
    }

    public String viandas_del_total(Heladera h){
        return h.getViandasEnHeladera().size()+"/"+h.getTamanioEnViandas();
    }

    public String viandas_en_heladera (Heladera h){
        return String.valueOf(h.getViandasEnHeladera().size()) ;
    }

    public String estadoString(Heladera h){
        switch (h.getEstado()) {
            case DISPONIBLE:
                return "Disponible";
            case INACTIVA_POR_FALLA:
                return "Falla";
            case INACTIVA_POR_ALERTA:
                return "Alerta";
            default:
                return "Funcionando";
        }
    }
    public String estadoStringStyle(Heladera h){
        switch (h.getEstado()) {
            case DISPONIBLE:
                return "success";
            case INACTIVA_POR_FALLA:
                return "warning";
            case INACTIVA_POR_ALERTA:
                return "danger";
            default:
                return "danger";
        }
    }

    public String botonSubscribir(Usuario user, Heladera h){
        System.out.println("usuario "+ user);
        System.out.println("heladera "+ h.getNombre());

        Colaborador colaborador = (Colaborador) user.getRol();

        if(colaborador.getSuscripciones().stream().anyMatch(s -> s.getHeladera() == h)){
            return "Desuscribirse";
        } else return "Suscribirse";

    }

    public String botonSubscribirStyle(Usuario user, Heladera h){
        System.out.println("usuario "+ user);
        System.out.println("heladera "+ h.getNombre());

        Colaborador colaborador = (Colaborador) user.getRol();

        if(colaborador.getSuscripciones().stream().anyMatch(s -> s.getHeladera() == h)){
            return "warning";
        } else return "success";

    }

    public String get_archivo(String urlBase, String path) {
        return urlBase + path;
    }

}

