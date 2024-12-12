package views;


import domain.colaboraciones.DistribucionVianda;
import domain.heladera.Heladera;
import domain.vianda.EnumEstadoVianda;
import domain.vianda.Vianda;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import persistence.Repos.RepoHeladera;
import persistence.Repos.RepoVianda;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UI_Traslado extends UI_Navegable implements Handler{

  @Override
  public void handle(Context ctx) throws Exception {

    this.validarUsuario(ctx);
    if (this.sesionValida(ctx)) {

      RepoHeladera hela = new RepoHeladera();

      model.put("hela", hela.getAll_Heladera());
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

    // Convertir parámetros necesarios
    Integer heladeraID = Integer.parseInt(heladeraIdProviene);
    Integer heladeraIDVa = Integer.parseInt(heladeraIdHacia);

    // Busca la heladera en la BD
    RepoHeladera hela = new RepoHeladera();
    Heladera heladera = hela.findById_Heladera(heladeraID);
    System.out.println("HelaName= "+heladera.getNombre());
    RepoHeladera hela2 = new RepoHeladera();
    Heladera heladeraHacia = hela2.findById_Heladera(heladeraIDVa);
    System.out.println("Hela2Name= "+heladeraHacia.getNombre());


    Integer i;
    Vianda viandaHeladera;
    List<Vianda> viandasMover= new ArrayList<>();
    System.out.println("Cantidad de viandas de heladera2= "+ heladera.cantidadViandas());
    RepoVianda repoVianda = new RepoVianda();


    if (heladera == heladeraHacia)
    {
      throw new IllegalArgumentException("No se puede mover a la misma heladera");
    }

    if(cantidadViandas<=heladera.cantidadViandas())
    {
        for(i=0;i<cantidadViandas;i++)
        {
        viandaHeladera = heladera.sacarViandaPorIndice(i);
        viandasMover.add(viandaHeladera);
        repoVianda.cambiarHeladera(heladeraHacia,viandaHeladera.getId());
        }

        heladeraHacia.ingresarViandas(viandasMover);
        System.out.println("Se movieron las viandas con exito hacia la heladera" + heladeraHacia.getNombre());
    }else  System.out.println("La heladera no puede mover mas viandas de las que tiene");

    // Crea Traslado
    // Funcion de traslado @Todo
    // a la funcion de traslado se le puede pasar el usuario de la secion activa con
    // this.getUsuario()

    ctx.render("index.hbs");
  }


}
