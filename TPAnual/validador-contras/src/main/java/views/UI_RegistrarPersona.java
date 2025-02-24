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

    System.out.println("estoy en UI_RegistrarPersona");
    String token = ctx.cookie("Auth");
    Claims claims= JwtUtil.getClaimsFromToken(token);
    RepoColaborador repoColaborador = RepoColaborador.getInstance();
    Colaborador colapinto=repoColaborador.findById_Colaborador((Integer) claims.get("roleId"));

    Integer tarjetasDisponibles=colapinto.getTarjetasParaEntregar().size();


    System.out.println("tarjetas de colapa: "+ tarjetasDisponibles);
    model.put("tarjetasEntregables", colapinto.getTarjetasParaEntregar());
    model.put("tarjetasDisponibles",tarjetasDisponibles);
    model.put("botonDeshabilitado", tarjetasDisponibles <= 0);
    ctx.render("registrar-persona.hbs", this.model);
  }

  public void agregarPersona(Context ctx) {
    System.out.println("estoy en agregarPersona");

    // Agarro al colaborador actual
    String token = ctx.cookie("Auth");
    Claims claims= JwtUtil.getClaimsFromToken(token);
    RepoColaborador repoColaborador = RepoColaborador.getInstance();
    Colaborador colapinto=repoColaborador.findById_Colaborador((Integer) claims.get("roleId"));

    // Obtener parámetros del formulario (datos enviados en la solicitud)
    String nombre = ctx.formParam("nombre");
    String fechaNacimiento = ctx.formParam("fechaNacimiento");
    String situacionCalle = ctx.formParam("situacionCalle");
    String direccion = ctx.formParam("direccion");
    String tipoDocumento = ctx.formParam("tipoDocumento");
    String documento = ctx.formParam("documento");
    String numMenoresACargo =ctx.formParam("numMenoresACargo");
    String tarjetaIdentificadorVulnerable = ctx.formParam("tarjeta");

    System.out.println("mi nombre es: " + nombre);
    System.out.println("naci: " + fechaNacimiento);
    System.out.println("mi tipo de doc es: " + tipoDocumento);
    System.out.println("mi doc es: " + documento);
    System.out.println("tengo "+ numMenoresACargo + " menores a cargo");
    System.out.println("el identificador es: "+ tarjetaIdentificadorVulnerable);

    System.out.println("tarjetas de colapa: "+colapinto.getTarjetasParaEntregar().size());
    System.out.println("tarjetas de colapa 2: "+colapinto.getTarjetasParaEntregar());

    if(colapinto.getTarjetasParaEntregar().isEmpty()){
      throw new IllegalArgumentException("El colaborador no tiene tarjetas para entregar");
    }

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
    //vulnerable.setTarjeta(unaTarjetinha);

    //Asigno tarjeta

    //colapinto.entregarTarjeta(vulnerable);


    if (situacionCalle == null){
      vulnerable.setSituacionCalle(EnumSituacionCalle.POSEE_HOGAR);
    }else {
      vulnerable.setSituacionCalle(EnumSituacionCalle.NO_POSEE_HOGAR);
    }

    for(Integer i =0; i<colapinto.getTarjetasParaEntregar().size(); i++){
      Tarjeta tarjetita = colapinto.getTarjetasParaEntregar().get(i);
      System.out.println("Las tarjetas de colapa son: " + tarjetita.getIdentificador());
    }


    Tarjeta tarjetaVulnerable = colapinto.getTarjetasParaEntregar().stream().filter(t -> t.getIdentificador().equals(tarjetaIdentificadorVulnerable)).findFirst().get();
    System.out.println("la tarjeta es awkdwakd: " + tarjetaVulnerable.getIdentificador());


    RegistroPersonaVulnerable registroPersonaVulnerable=new RegistroPersonaVulnerable(tarjetaVulnerable,vulnerable);

    colapinto.getTarjetasParaEntregar().remove(tarjetaVulnerable);

    registroPersonaVulnerable.setColaborador(colapinto);
    colapinto.realizarColaboracion(registroPersonaVulnerable);

    //ORM
    persistirEntidades(docu, persona, vulnerable);

    EntityManager em = BDUtils.getEntityManager();
    BDUtils.comenzarTransaccion(em);

    // lo hago a parte porque no hice la de persistirEntidades y me da cosa cambiarla xd
    em.persist(registroPersonaVulnerable);

    em.merge(colapinto);
    BDUtils.commit(em);

    //repoColaborador.actualizarColaborador(colapinto);

    ctx.redirect("/index");
  }

  public void solicitarTarjeta(Context ctx) {
    System.out.println("estoy en solicitarTarjetas");


    // Agarro al colaborador actual
    String token = ctx.cookie("Auth");
    Claims claims= JwtUtil.getClaimsFromToken(token);
    RepoColaborador repoColaborador = RepoColaborador.getInstance();
    Colaborador colapinto=repoColaborador.findById_Colaborador((Integer) claims.get("roleId"));

    // Obtener parámetros del formulario (datos enviados en la solicitud)
    String cantidadTarjetas = ctx.formParam("cantidad");
    Integer tarjetas = Integer.parseInt(cantidadTarjetas);


    System.out.println("La cantidad de tarjetas es: " + tarjetas);
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
    ctx.redirect("/registrar-persona");
  }


  private static void persistirEntidades(Documento docu, PersonaFisica persona, Vulnerable vulnerable) {
    EntityManager em = BDUtils.getEntityManager();
    BDUtils.comenzarTransaccion(em);


    em.persist(docu);
    em.persist(persona);
    em.persist(vulnerable);
    //A CHEQUEAR FUNCIONAMIENTO DE ESTO
    em.merge(vulnerable.getTarjeta());
    BDUtils.commit(em);
  }
  private static void persistirColabTarj(Colaborador colab, Tarjeta tarjeta){
    EntityManager em = BDUtils.getEntityManager();
    BDUtils.comenzarTransaccion(em);

    em.persist(tarjeta);

    //A CHEQUEAR FUNCIONAMIENTO DE ESTO
    em.merge(colab);

    BDUtils.commit(em);

  }
}
