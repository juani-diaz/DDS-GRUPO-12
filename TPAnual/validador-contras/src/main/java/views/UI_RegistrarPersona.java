package views;

import domain.persona.Documento;
import domain.persona.PersonaFisica;
import domain.rol.EnumSituacionCalle;
import domain.rol.Vulnerable;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import persistence.BDUtils;
import persistence.Repos.RepoVulnerable;

import javax.persistence.EntityManager;
import java.time.LocalDate;

public class UI_RegistrarPersona implements Handler{

  @Override
  public void handle(Context ctx) throws Exception {
    System.out.println("estoy en UI_RegistrarPersona");
    ctx.render("registrar-persona.hbs");
  }

  public void agregarPersona(Context ctx) {
    System.out.println("estoy en agregarPersona");

    // Obtener parámetros del formulario (datos enviados en la solicitud)
    String nombre = ctx.formParam("nombre");
    System.out.println("nombre= "+nombre);

    String fechaNacimiento = ctx.formParam("fechaNacimiento");
    System.out.println("fechaNacimiento= "+fechaNacimiento);

    String situacionCalle = ctx.formParam("situacionCalle");
    System.out.println("situacionCalle= "+situacionCalle);

    String direccion = ctx.formParam("direccion");
    System.out.println("direccion= "+direccion);

    String tipoDocumento = ctx.formParam("tipoDocumento");
    System.out.println("tipoDocumento= "+tipoDocumento);

    String documento = ctx.formParam("documento");

    String numMenoresACargo =ctx.formParam("numMenoresACargo");
    System.out.println("numMenoresACargo= "+numMenoresACargo);


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
    }else {
      vulnerable.setSituacionCalle(EnumSituacionCalle.NO_POSEE_HOGAR);
    }


    EntityManager em = BDUtils.getEntityManager();
    BDUtils.comenzarTransaccion(em);

    em.persist(docu);
    System.out.println("Commit Docu");
    em.persist(persona);
    System.out.println("Commit persona");
    em.persist(vulnerable);
    System.out.println("Commit vulnerabled");

    BDUtils.commit(em);



    //RepoVulnerable vulne = new RepoVulnerable();
    //vulne.add_Vulnerable(vulnerable);

    ctx.render("index.hbs");
  }

}
