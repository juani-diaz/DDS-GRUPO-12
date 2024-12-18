package views;


import domain.auth.JwtUtil;
import domain.colaboraciones.DistribucionVianda;
import domain.colaboraciones.EnumMotivosMovimientoVianda;
import domain.heladera.Heladera;
import domain.persona.EnumTipoPersonaJuridica;
import domain.rol.Colaborador;
import domain.vianda.Vianda;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.jsonwebtoken.Claims;
import persistence.BDUtils;
import persistence.EntidadPersistente;
import persistence.Repos.RepoColaborador;
import persistence.Repos.RepoHeladera;
import persistence.Repos.RepoVianda;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class UI_Traslado extends UI_Navegable implements Handler{

  @Override
  public void handle(Context ctx) throws Exception {
    this.validarUsuario(ctx);
    if (this.sesionValida(ctx)) {
      RepoHeladera hela = RepoHeladera.getInstance();
      model.put("hela", hela.getHeladeras());
      ctx.render("traslado.hbs", this.model);
    }
  }

  public void trasladarCantViandas(Context ctx){

    System.out.println("estoy en UI_Traslado::trasladarCantViandas");

    // Obtener parámetros del formulario (datos enviados en la solicitud)
    String cantidad = ctx.formParam("cantidad");
    System.out.println("cantidad= "+cantidad);
    Integer cantidadViandas= Integer.parseInt(cantidad);
    String heladeraIdProviene = ctx.formParam("heladeraID");
    System.out.println("heladeraId= "+heladeraIdProviene);
    String heladeraIdHacia = ctx.formParam("heladeraIDHacia");
    System.out.println("heladeraIDHacia= "+heladeraIdHacia);
    String motivoMovimiento = ctx.formParam("motivoTraslado");
    System.out.println("el motivo de traslado es: "+ motivoMovimiento);

    EnumMotivosMovimientoVianda motivo=null;
    //motivo = EnumMotivosMovimientoVianda.valueOf(motivoMovimiento)


      if(Objects.equals(motivoMovimiento, "FALTA_DE_VIANDAS")){
          motivo = EnumMotivosMovimientoVianda.FALTA_DE_VIANDAS;
          System.out.println("el motivo enum es: "+ motivo);
      } else if(Objects.equals(motivoMovimiento, "DESPERFECTO_HELADERA")){
          motivo = EnumMotivosMovimientoVianda.DESPERFECTO_HELADERA;
          System.out.println("el motivo enum es: "+ motivo);
      }


    // Convertir parámetros necesarios
    Integer heladeraID = Integer.parseInt(heladeraIdProviene);
    Integer heladeraIDVa = Integer.parseInt(heladeraIdHacia);
    //if(motivoMovimiento = "")

    // Busca la heladera en la BD
    RepoHeladera hela = RepoHeladera.getInstance();
    Heladera heladera = hela.findById_Heladera(heladeraID);
    System.out.println("HelaName= "+heladera.getNombre());
    Heladera heladeraHacia = hela.findById_Heladera(heladeraIDVa);
    System.out.println("Hela2Name= "+heladeraHacia.getNombre());


    Integer i;
    Vianda viandaHeladera;
    List<Vianda> viandasMover= new ArrayList<>();
    System.out.println("Cantidad de viandas de heladera origen= "+ heladera.cantidadViandas());
    RepoVianda repoVianda = RepoVianda.getInstance();


    if (heladera == heladeraHacia)
    {
      throw new IllegalArgumentException("No se puede mover a la misma heladera");
    }

    if(cantidadViandas<=heladera.cantidadViandas()) {
        List<Vianda> shuffledList = new ArrayList<>(heladera.getViandasEnHeladera());
        Collections.shuffle(shuffledList);
        viandasMover = shuffledList.subList(0, cantidadViandas);
        //heladera.sacarViandas(viandasMover);
        //heladeraHacia.ingresarViandas(viandasMover);


        System.out.println("Se movieron las viandas con exito hacia la heladera" + heladeraHacia.getNombre());

        String token = ctx.cookie("Auth");
        Claims claims = JwtUtil.getClaimsFromToken(token);
        RepoColaborador repoColaborador = RepoColaborador.getInstance();
        Colaborador colapinto = repoColaborador.findById_Colaborador((Integer) claims.get("roleId"));

        DistribucionVianda distribucion = new DistribucionVianda(colapinto, LocalDate.now(), heladera, heladeraHacia, cantidadViandas, motivo);


        colapinto.realizarColaboracion(distribucion);

        EntityManager em = BDUtils.getEm();
        BDUtils.comenzarTransaccion(em);

        em.persist(distribucion);
        BDUtils.commit(em);

        repoVianda.cambiarHeladeraPlural(heladeraHacia,viandasMover);





    }else  System.out.println("La heladera no puede mover mas viandas de las que tiene");

      // Crea Traslado
    // Funcion de traslado @Todo
    // a la funcion de traslado se le puede pasar el usuario de la secion activa con
    // this.getUsuario()

    ctx.render("index.hbs");
  }


}
