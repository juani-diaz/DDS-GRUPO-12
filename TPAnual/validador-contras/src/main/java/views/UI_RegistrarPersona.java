package views;

import domain.auth.JwtUtil;
import domain.colaboraciones.RegistroPersonaVulnerable;
import domain.persona.Documento;
import domain.persona.PersonaFisica;
import domain.rol.Colaborador;
import domain.rol.EnumSituacionCalle;
import domain.rol.Tarjeta;
import domain.rol.Vulnerable;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.jsonwebtoken.Claims;
import obs.RespuestaCliente;
import persistence.BDUtils;
import persistence.Repos.RepoColaborador;

import javax.persistence.EntityManager;
import javax.print.Doc;
import java.time.LocalDate;
import java.util.Random;

public class UI_RegistrarPersona extends UI_Navegable implements Handler{

  @Override
  public void handle(Context ctx) throws Exception {
    this.validarUsuario(ctx);

    Colaborador colapinto = (Colaborador) getUsuario().getRol();

    Integer tarjetasDisponibles = colapinto.getTarjetasParaEntregar().size();

    model.put("tarjetasEntregables", colapinto.getTarjetasParaEntregar());
    model.put("tarjetasDisponibles",tarjetasDisponibles);
    model.put("botonDeshabilitado", tarjetasDisponibles <= 0);
    ctx.render("registrar-persona.hbs", this.model);
  }

  public void agregarPersona(Context ctx) {
    // Agarro al colaborador actual
    Colaborador colapinto = (Colaborador) getUsuario().getRol();

    if(colapinto.getTarjetasParaEntregar().isEmpty()){
      throw new IllegalArgumentException("El colaborador no tiene tarjetas para entregar");
    }
    // Obtener parámetros del formulario (datos enviados en la solicitud)
    String nombre = ctx.formParam("nombre");
    String fechaNacimiento = ctx.formParam("fechaNacimiento");
    String situacionCalle = ctx.formParam("situacionCalle");
    String direccion = ctx.formParam("direccion");
    String tipoDocumento = ctx.formParam("tipoDocumento");
    String documento = ctx.formParam("documento");
    String numMenoresACargo =ctx.formParam("numMenoresACargo");
    String tarjetaIdentificadorVulnerable = ctx.formParam("tarjeta");


    // Convertir parámetros necesarios
    LocalDate fechaNacimientoParsed = LocalDate.parse(fechaNacimiento);
    Integer numMenoresACargoInt = Integer.parseInt(numMenoresACargo);

    // Crear Documento
    Documento docu = new Documento();
    docu.setTipo(tipoDocumento);
    docu.setNumero(documento);

    // Crea persona
    PersonaFisica persona = new PersonaFisica();
    persona.setNombre(nombre);
    persona.setDireccion(direccion);
    persona.setFechaNacimiento(fechaNacimientoParsed);
    persona.setDocumento(docu);

    // Crea Rol vulnerable
    Vulnerable vulnerable = new Vulnerable();
    vulnerable.setPersona(persona);
    vulnerable.setFechaRegistro(LocalDate.now());
    vulnerable.setMenoresACargo(numMenoresACargoInt);

    if (situacionCalle == null){
      vulnerable.setSituacionCalle(EnumSituacionCalle.POSEE_HOGAR);
    } else {
      vulnerable.setSituacionCalle(EnumSituacionCalle.NO_POSEE_HOGAR);
    }

    Tarjeta tarjetaVulnerable = colapinto.getTarjetasParaEntregar().stream().filter(t -> t.getIdentificador().equals(tarjetaIdentificadorVulnerable)).findFirst().get();

    RegistroPersonaVulnerable registroPersonaVulnerable = new RegistroPersonaVulnerable(tarjetaVulnerable,vulnerable);


    registroPersonaVulnerable.setColaborador(colapinto);
    registroPersonaVulnerable.setFecha(LocalDate.now());
    colapinto.realizarColaboracion(registroPersonaVulnerable);

    colapinto.getTarjetasParaEntregar().remove(tarjetaVulnerable);

    EntityManager em = BDUtils.getEntityManager();
    BDUtils.comenzarTransaccion(em);

    em.merge(colapinto);
    BDUtils.commit(em);

    RespuestaCliente.exito(getUsuario(), "/index", "Persona registrada con exito", ctx);
  }
}
