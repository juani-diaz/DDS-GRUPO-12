package views;

import domain.auth.JwtUtil;

import domain.auth.Usuario;
import domain.colaboraciones.*;
import domain.incidente.EnumEstadoDeIncidente;
import domain.incidente.Incidente;
import domain.persona.EnumTipoPersonaJuridica;
import domain.rol.*;

import io.javalin.http.Context;

import io.jsonwebtoken.Claims;

import persistence.Repos.RepoColaborador;


import domain.heladera.Heladera;
import domain.heladera.Ubicacion;
import persistence.Repos.RepoUsuarios;


import java.util.List;
import java.util.Objects;

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
            case PENDIENTE_INSTALACION:
                return "Propuesta";
            default:
                return "?";
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
            case PENDIENTE_INSTALACION:
                return "dark";
            default:
                return "light";
        }
    }

    public String botonSubscribirActivo(Usuario user, Heladera h, String tipo_sub){

        Colaborador colaborador = (Colaborador) user.getRol();

        String hela = Integer.toString(h.getId());

        if(colaborador.getSuscripciones().stream().anyMatch(
            s -> hela.equals(Integer.toString(s.getHeladera().getId())) &&
                ("domain.suscripcion."+tipo_sub).equals(s.getClass().getName()))){
            return "active";
        } else return "";
    }

    public String botonSubscribirStyle(Usuario user, Heladera h){ //Al final no lo uso
        System.out.println("usuario "+ user);
        System.out.println("heladera "+ h.getNombre());

        Colaborador colaborador = (Colaborador) user.getRol();

        if(colaborador.getSuscripciones().stream().anyMatch(s -> s.getHeladera() == h)){
            return "warning";
        } else return "success";

    }

    public String get_archivo(String urlBase, String path) {
        String s = urlBase + path;
        System.out.println("Busco http: " + s);
        return s;
    }

    public Boolean notEqual(Object o1, Object o2){
        return !o1.equals(o2);
    }

    public Boolean equal(Object o1, Object o2){
        return o1.equals(o2);
    }

    public Boolean tengoAsignado(Incidente incidente, Usuario usuario){
        if(incidente == null || incidente.getTecnico() == null)
            return false;
        return incidente.getTecnico().getId() == usuario.getRol().getId();
    }

    public Boolean noTengoAsignado(Incidente incidente, Usuario usuario){
        return !tengoAsignado(incidente, usuario);
    }

    public String estadoStringIncidentes(Incidente i){
        switch (i.getEstadoDeIncidente()) {
            case SOLUCIONADO:
                return "Solucionado";
            case TECNICO_ASIGNADO:
                return "Asignado";
            case PENDIENTE_A_SOLUCIONAR:
                return "Sin asignar";
            default:
                return "?";
        }
    }
    public String estadoStringStyleIncidentes(Incidente i){
        switch (i.getEstadoDeIncidente()) {
            case SOLUCIONADO:
                return "success";
            case TECNICO_ASIGNADO:
                return "warning";
            case PENDIENTE_A_SOLUCIONAR:
                return "danger";
            default:
                return "light";
        }
    }

    public String nombreTecnico(Incidente i){
        if(i.getTecnico() == null)
            return "-";
        return i.getTecnico().getPersona().getNombre();
    }

    public String ultimaFechaIncidente(Incidente i){
        if(i.getEvolucionDeIncidente() == null || i.getEvolucionDeIncidente().isEmpty())
            return String.valueOf(i.getFecha());
        return String.valueOf(i.getEvolucionDeIncidente().get(i.getEvolucionDeIncidente().size()-1).getFechaVisita());
    }

    public Boolean puedoForzarSolucion(Incidente i) {
        return i.getEstadoDeIncidente() != EnumEstadoDeIncidente.SOLUCIONADO;
    }

    public Boolean puedoDesasignar(Incidente i) {
        return i.getEstadoDeIncidente() == EnumEstadoDeIncidente.TECNICO_ASIGNADO;
    }

    public Boolean lt(Float a, Float b) {
        return a < b;
    }

    public Boolean estaEnListado(String a, List<String> l) {
        return l.contains(a);
    }

    public String tipoColaboracion(Colaboracion c){
        if(c.getClass() == DistribucionVianda.class){
            return "Distribución de viandas";
        } else if(c.getClass() == DonacionDinero.class){
            return "Donación de dinero";
        } else if(c.getClass() == DonacionVianda.class){
            return "Donación de una vianda";
        } else if(c.getClass() == PresentacionOferta.class){
            return "Pesentación de oferta para canje";
        } else if(c.getClass() == RegistroPersonaVulnerable.class){
            return "Registro de persona vulnerable";
        } else if(c.getClass() == ResponsableHeladera.class){
            return "Puesta en marcha de heladera";
        } else {
            return "?";
        }
    }

    public String detalleColaboracion(Colaboracion c){
        if(c.getClass() == DistribucionVianda.class){

            DistribucionVianda d = (DistribucionVianda) c;
            return d.getViandasMovidas().size() + " viandas desde " + d.getOrigen().getNombre() + " hacia " + d.getDestino().getNombre();

        } else if(c.getClass() == DonacionDinero.class){

            DonacionDinero d = (DonacionDinero) c;
            String s = "$" + d.getMonto();
            if(d.getFrecuencia() != null){
                s = s + " (" + d.getFrecuencia() + ")";
            }
            return s;

        } else if(c.getClass() == DonacionVianda.class){

            DonacionVianda d = (DonacionVianda) c;
            return d.getVianda().getComida() + " en la heladera " + d.getDestino().getNombre();

        } else if(c.getClass() == PresentacionOferta.class){

            PresentacionOferta p = (PresentacionOferta) c;
            return "Pesentación de " + p.getNombre();

        } else if(c.getClass() == RegistroPersonaVulnerable.class){

            RegistroPersonaVulnerable r = (RegistroPersonaVulnerable) c;
            return "Registro de " + r.getVulnerable().getPersona().getNombre();

        } else if(c.getClass() == ResponsableHeladera.class){

            ResponsableHeladera r = (ResponsableHeladera) c;
            return "Puesta en marcha de la heladera " + r.getHeladera().getNombre();

        } else {
            return "?";
        }
    }

}

