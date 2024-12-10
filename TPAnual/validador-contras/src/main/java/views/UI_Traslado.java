package views;


import domain.heladera.Heladera;
import domain.vianda.EnumEstadoVianda;
import domain.vianda.Vianda;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import persistence.Repos.RepoHeladera;
import persistence.Repos.RepoVianda;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class UI_Traslado implements Handler{

  @Override
  public void handle(Context ctx) throws Exception {
    RepoHeladera hela = new RepoHeladera();

    Map<String, Object> model = new HashMap<>();
    model.put("hela", hela.getAll_Heladera());
    ctx.render("traslado.hbs", model);
  }

  public void trasladarCantViandas(Context ctx){
    System.out.println("estoy en UI_Traslado::trasladarCantViandas");

    // Obtener parámetros del formulario (datos enviados en la solicitud)
    String cantidad = ctx.formParam("cantidad");
    System.out.println("cantidad= "+cantidad);
    String heladeraId = ctx.formParam("heladeraID");
    System.out.println("heladeraId= "+heladeraId);

    // Convertir parámetros necesarios
    Integer heladeraID = Integer.parseInt(heladeraId);

    // Busca la heladera en la BD
    RepoHeladera hela = new RepoHeladera();
    Heladera heladera = hela.findById_Heladera(heladeraID);
    System.out.println("HelaName= "+heladera.getNombre());

    // Crea Traslado
    // Funcion de traslado @Todo

    ctx.render("index.hbs");
  }


}
