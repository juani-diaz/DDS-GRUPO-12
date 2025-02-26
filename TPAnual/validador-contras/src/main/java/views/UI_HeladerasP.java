package views;


import com.fasterxml.jackson.databind.ObjectMapper;
import domain.heladera.Heladera;
import domain.persona.MedioDeContacto;
import domain.rol.Colaborador;
import domain.servicios.TwilioSendGrid;
import domain.suscripcion.*;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import obs.RespuestaCliente;
import persistence.BDUtils;
import persistence.Repos.RepoColaborador;
import persistence.Repos.RepoHeladera;
import persistence.Repos.RepoSuscripcion;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class UI_HeladerasP extends UI_Navegable implements Handler{

    @Override
    public void handle(Context ctx) throws Exception {
        this.validarUsuario(ctx);

        RepoHeladera hela = RepoHeladera.getInstance();
        model.put("hela", hela.getHeladeras());
        this.model.put("apiKey",System.getenv("GOOGLE_KEY"));
        ctx.render("heladeras-p.hbs", this.model);
    }

    public void botonSuscribe(Context ctx) throws IOException{
        /*
        Colaborador colaborador = (Colaborador) this.getUsuario().getRol();

        if(colaborador.getSuscripciones().stream().anyMatch(
            s -> hela.equals(Integer.toString(s.getHeladera().getId())) &&
                ("domain.suscripcion."+tipo_sub).equals(s.getClass().getName()))){
            this.desuscribirse(colaborador, hela, tipo_sub);
        } else this.suscribirse(colaborador, hela, tipo_sub);

        ctx.render("index.hbs");

         */
        String jsonBody = ctx.body();

        // Parsear el JSON a un objeto Java
        ObjectMapper objectMapper = new ObjectMapper();
        SuscripcionRequest suscripcionRequest = objectMapper.readValue(jsonBody, SuscripcionRequest.class);

        // Acceder a los datos
        String heladeraId = suscripcionRequest.getHeladeraId();
        List<TipoSuscripcion> tiposSuscripcion = suscripcionRequest.getTiposSuscripcion();

        Colaborador colaborador = (Colaborador) this.getUsuario().getRol();

        //extracted es funcion que devuelve heladera en base a su ID
        Heladera heladera = extracted(heladeraId);

        for(Suscripcion s : new ArrayList<>(colaborador.getSuscripciones())){

            if(s.getClass() == PocasViandas.class){
                if(tiposSuscripcion.stream().noneMatch(t -> Objects.equals(t.tipo, "PocasViandas"))){
                    desuscribirse(colaborador, heladera, "PocasViandas");
                }
            } else if (s.getClass() == MuchasViandas.class) {
                if(tiposSuscripcion.stream().noneMatch(t -> Objects.equals(t.tipo, "MuchasViandas"))){
                    desuscribirse(colaborador, heladera, "MuchasViandas");
                }
            } else {
                if(tiposSuscripcion.stream().noneMatch(t -> Objects.equals(t.tipo, "NoFunciona"))){
                    desuscribirse(colaborador, heladera, "NoFunciona");
                }
            }
        }

        for(TipoSuscripcion t : tiposSuscripcion) {
            if(colaborador.getSuscripciones().stream().noneMatch(s -> s.getClass().getName().equals("domain.suscripcion."+t.tipo))){
                int cant = 0;
                if(t.cantidad != null)
                    cant = Integer.parseInt(t.cantidad);

                suscribirse(colaborador, heladera, t.tipo, cant);
            } else if (!Objects.equals(t.tipo, "NoFunciona")) {
                List<Suscripcion> actualizar = colaborador.getSuscripciones().stream().filter(s -> s.getClass().getName().equals("domain.suscripcion."+t.tipo)).toList();
                for(int i = 0; i < actualizar.size(); i++){
                    if (Objects.equals(t.tipo, "PocasViandas")){
                        PocasViandas pocas = (PocasViandas) actualizar.get(i);
                        pocas.setNumeroMinimo(Integer.valueOf(t.cantidad));
                        RepoSuscripcion.getInstance().update_Suscripcion(pocas);
                    } else {
                        MuchasViandas muchas = (MuchasViandas) actualizar.get(i);
                        muchas.setNumeroMaximo(Integer.valueOf(t.cantidad));
                        RepoSuscripcion.getInstance().update_Suscripcion(muchas);
                    }
                }
            }
        }

        RespuestaCliente.fetchSub(getUsuario(), "/heladeras-p", "Suscripcion actualizada", ctx);
    }

    private void suscribirse(Colaborador c, Heladera hela, String tipo_sub, int cantidad) throws IOException {
        System.out.println("Estoy en UI_HeladerasP::subscribirse");

        List<MedioDeContacto> medios = c.getPersona().getMediosDeContacto();

        if (medios.isEmpty()) {
            System.out.println("El colaborador no tiene medios de contacto.");
            return;
        }

        Publicador publisher = new Publicador();

        for (MedioDeContacto medio : medios) {
            Suscripcion suscripcion;

            switch (tipo_sub) {
                case "PocasViandas":
                    suscripcion = new PocasViandas(hela, cantidad);
                    break;
                case "MuchasViandas":
                    suscripcion = new MuchasViandas(hela, cantidad);
                    break;
                default:
                    suscripcion = new NoFunciona(hela);
            }

            suscripcion.setColaborador(c);
            suscripcion.setNotificadores(medio);

            RepoSuscripcion.getInstance().add_Suscripcion(suscripcion);
            c.getSuscripciones().add(suscripcion);

            String subject = "Suscripción a heladera '" + hela.getNombre()+"'";
            String mensaje =
                "<h1>En hora buena <strong>" + this.getUsuario().getRol().getPersona().getNombre() + "</strong>,</h1> " +
                    "<p>Acabas de suscribirse a la heladera "
                    + "<strong>'" + hela.getNombre()+"' </strong> " +
                    "de ahora en más podrá recibir todas las notificaciones "
                    + "pertinentes a su suscripción del tipo <strong>"+tipo_sub+"</strong>!</p>";

            publisher.addObservable(suscripcion);
            TwilioSendGrid.sendEmail(medio.getContacto(), subject, mensaje);
        }

        RepoColaborador.getInstance().actualizarColaborador(c);
    }


    private void desuscribirse(Colaborador colaborador, Heladera hela, String tipo_sub) throws IOException {

        System.out.println("Estoy en DESUSCRIBIRSE");

        Suscripcion sus = colaborador.getSuscripciones().stream().filter(
            s -> Integer.toString(hela.getId()).equals(Integer.toString(s.getHeladera().getId())) &&
                ("domain.suscripcion."+tipo_sub).equals(s.getClass().getName()))
            .reduce((first, second) -> first)
            .orElse(null);

        colaborador.getSuscripciones().remove(sus);
        RepoColaborador.getInstance().actualizarColaborador(colaborador);

        RepoSuscripcion.getInstance().remove_Suscripcion(sus);

        String subject =
            "Desuscribido de heladera '"+ hela.getNombre()+"'";
        String mensaje =
            "Hola <strong>" + this.getUsuario().getRol().getPersona().getNombre() + "</strong>,</h1>" +
                "<h1>Acabas de dessuscribirte de la heladera "
                + "<strong>'" + hela.getNombre()+"' </strong> " +
                "de ahora en mas no recibiras notificaciones "
                + "pertinentes a su suscripción del tipo <strong>"+tipo_sub+"</strong>!</p> de dihca heladera.";

        System.out.println(this.getUsuario().getRol().getPersona().getMediosDeContacto().get(0).getContacto());
        TwilioSendGrid.sendEmail(this.getUsuario().getRol().getPersona().getMediosDeContacto().get(0).getContacto(), subject, mensaje);
    }

    private Heladera extracted(String heladeraId) {
        // Convertir parámetros necesarios
        Integer heladeraID = Integer.parseInt(heladeraId);

        // Busca la heladera en la BD
        RepoHeladera hela = RepoHeladera.getInstance();
        Heladera heladera = hela.findById_Heladera(heladeraID);
        System.out.println("HelaName= "+heladera.getNombre());
        return heladera;
    }

    // Clase para representar la solicitud de suscripción
    static class SuscripcionRequest {
        private String heladeraId;
        private List<TipoSuscripcion> tiposSuscripcion;

        // Getters y setters
        public String getHeladeraId() {
            return heladeraId;
        }

        public void setHeladeraId(String heladeraId) {
            this.heladeraId = heladeraId;
        }

        public List<TipoSuscripcion> getTiposSuscripcion() {
            return tiposSuscripcion;
        }

        public void setTiposSuscripcion(List<TipoSuscripcion> tiposSuscripcion) {
            this.tiposSuscripcion = tiposSuscripcion;
        }
    }

    // Clase para representar un tipo de suscripción
    static class TipoSuscripcion {
        private String tipo;
        private String cantidad;

        // Getters y setters
        public String getTipo() {
            return tipo;
        }

        public void setTipo(String tipo) {
            this.tipo = tipo;
        }

        public String getCantidad() {
            return cantidad;
        }

        public void setCantidad(String cantidad) {
            this.cantidad = cantidad;
        }
    }

}



