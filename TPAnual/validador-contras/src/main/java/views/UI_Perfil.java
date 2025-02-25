package views;

import domain.auth.JwtUtil;
import domain.persona.EnumTipoPersonaJuridica;
import domain.persona.PersonaFisica;
import domain.persona.PersonaJuridica;
import domain.rol.*;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.jsonwebtoken.Claims;
import lombok.NoArgsConstructor;
import persistence.BDUtils;
import persistence.Repos.RepoColaborador;
import persistence.Repos.RepoUsuarios;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@NoArgsConstructor
public class UI_Perfil extends UI_Navegable implements Handler {

    @Override
    public void handle(Context ctx) throws Exception {
        this.validarUsuario(ctx);

        model.put("listadoLocalidades", new LocalidadesTecnico());

        String tipoUsuario = "?";
        if (getUsuario().getRol().getClass().equals(Administrador.class)) {
            tipoUsuario = "admin";

            model.put("colaboraciones", null);
        } else if (getUsuario().getRol().getClass().equals(Colaborador.class) && getUsuario().getRol().getPersona().getClass() == PersonaFisica.class) {
            tipoUsuario = "persona";

            Colaborador c = (Colaborador) getUsuario().getRol();

            model.put("colaboraciones", c.getColaboraciones());

            model.put("cantidadTarjetas", c.getTarjetasParaEntregar().size());
        } else if (getUsuario().getRol().getClass().equals(Colaborador.class) && getUsuario().getRol().getPersona().getClass() == PersonaJuridica.class) {
            tipoUsuario = "organizacion";

            PersonaJuridica personaJuridica = (PersonaJuridica) getUsuario().getRol().getPersona();
            if(personaJuridica.getTipo() == EnumTipoPersonaJuridica.ONG){
                model.put("tipoJurOriginal", "ONG");
            } else if(personaJuridica.getTipo() == EnumTipoPersonaJuridica.EMPRESA){
                model.put("tipoJurOriginal", "EMPRESA");
            } else if(personaJuridica.getTipo() == EnumTipoPersonaJuridica.GUBERNAMENTAL){
                model.put("tipoJurOriginal", "GUBERNAMENTAL");
            } else {
                model.put("tipoJurOriginal", "INSTITUCION");
            }

            Colaborador c = (Colaborador) getUsuario().getRol();

            model.put("colaboraciones", c.getColaboraciones());

        } else if (getUsuario().getRol().getClass().equals(Tecnico.class)) {
            tipoUsuario = "tecnico";

            Tecnico t = (Tecnico) getUsuario().getRol();
            model.put("localidadesRegistradas", t.getAreaCobertura());

            model.put("colaboraciones", null);
        }
        this.model.put("tipoUsuario", tipoUsuario);

        System.out.println(getUsuario().getRol().getPersona().getMediosDeContacto());
        this.model.put("persona", getUsuario().getRol().getPersona());

        ctx.render("app-profile.hbs", this.model);
    }

    public void actualizarColaboradorHumano(Context ctx) throws Exception {
        String nombre = ctx.formParam("nombre");
        String apellido = ctx.formParam("apellido");
        String sexo = ctx.formParam("sexo");
        String genero = ctx.formParam("genero");
        String fecha = ctx.formParam("fecha");
        String direccion = ctx.formParam("direccion");
        String tipoDocumento = ctx.formParam("tipoDocumento");
        String documento = ctx.formParam("documento");

        Colaborador c = (Colaborador) getUsuario().getRol();
        PersonaFisica p = (PersonaFisica) c.getPersona();

        p.setNombre(nombre);
        p.setApellido(apellido);
        p.setSexo(sexo);
        p.setGenero(genero);
        p.setFechaNacimiento(LocalDate.parse(fecha));
        p.setDireccion(direccion);
        p.getDocumento().setTipo(tipoDocumento);
        p.getDocumento().setNumero(documento);

        // faltan medios

        RepoUsuarios.getInstance().update_Usuario(getUsuario());

        ctx.redirect("/app-profile");
    }

    public void actualizarColaboradorJuridico(Context ctx) throws Exception {
        String razonSocial = ctx.formParam("razonSocial");
        String rubro = ctx.formParam("rubro");
        String tipoJur = ctx.formParam("tipoJur");
        EnumTipoPersonaJuridica tipoJuridica = null;
        if(Objects.equals(tipoJur, "ONG")){
            tipoJuridica = EnumTipoPersonaJuridica.ONG;
        } else if(Objects.equals(tipoJur, "EMPRESA")){
            tipoJuridica = EnumTipoPersonaJuridica.EMPRESA;
        } else if(Objects.equals(tipoJur, "INSTITUCION")){
            tipoJuridica = EnumTipoPersonaJuridica.INSTITUCION;
        } else if(Objects.equals(tipoJur, "GUBERNAMENTAL")){
            tipoJuridica = EnumTipoPersonaJuridica.GUBERNAMENTAL;
        }
        String direccion = ctx.formParam("direccion");
        String tipoDocumento = ctx.formParam("tipoDocumento");
        String documento = ctx.formParam("documento");

        Colaborador c = (Colaborador) getUsuario().getRol();
        PersonaJuridica p = (PersonaJuridica) c.getPersona();

        p.setNombre(razonSocial);
        p.setRubro(rubro);
        p.setTipo(tipoJuridica);
        p.setRazonSocial(razonSocial);
        p.setDireccion(direccion);
        p.getDocumento().setTipo(tipoDocumento);
        p.getDocumento().setNumero(documento);

        // faltan medios

        RepoUsuarios.getInstance().update_Usuario(getUsuario());

        ctx.redirect("/app-profile");
    }

    public void actualizarTecnico(Context ctx) throws Exception {
        String nombre = ctx.formParam("nombre");
        String apellido = ctx.formParam("apellido");
        String sexo = ctx.formParam("sexo");
        String genero = ctx.formParam("genero");
        String fecha = ctx.formParam("fecha");
        String direccion = ctx.formParam("direccion");
        String tipoDocumento = ctx.formParam("tipoDocumento");
        String documento = ctx.formParam("documento");

        Tecnico t = (Tecnico) getUsuario().getRol();
        PersonaFisica p = (PersonaFisica) t.getPersona();

        p.setNombre(nombre);
        p.setApellido(apellido);
        p.setSexo(sexo);
        p.setGenero(genero);
        p.setFechaNacimiento(LocalDate.parse(fecha));
        p.setDireccion(direccion);
        p.getDocumento().setTipo(tipoDocumento);
        p.getDocumento().setNumero(documento);

        // faltan medios

        LocalidadesTecnico lt = new LocalidadesTecnico();
        List<String> area = new ArrayList<>(ctx.formParamMap().keySet().stream().filter(param -> lt.nombres().contains(param)).toList());

        t.setAreaCobertura(area);

        RepoUsuarios.getInstance().update_Usuario(getUsuario());

        ctx.redirect("/app-profile");
    }

    public void solicitarTarjeta(Context ctx) {

        // Agarro al colaborador actual
        String token = ctx.cookie("Auth");
        Claims claims= JwtUtil.getClaimsFromToken(token);
        RepoColaborador repoColaborador = RepoColaborador.getInstance();
        Colaborador colapinto=repoColaborador.findById_Colaborador((Integer) claims.get("roleId"));

        // Obtener parámetros del formulario (datos enviados en la solicitud)
        String cantidadTarjetas = ctx.formParam("cantidad");
        Integer tarjetas = Integer.parseInt(cantidadTarjetas);

        Random random= new Random();

        EntityManager em = BDUtils.getEm();
        BDUtils.comenzarTransaccion(em);

        for (Integer i=0; i<tarjetas; i++)
        {
            int numeroAleatorio = random.nextInt(1000000000);
            Tarjeta tarjeta = new Tarjeta("aa"+Integer.toString(numeroAleatorio));
            colapinto.recibirUnaTarjeta(tarjeta);
            em.persist(tarjeta);
        }
        em.merge(colapinto);

        BDUtils.commit(em);
        ctx.redirect("/app-profile");
    }
}
