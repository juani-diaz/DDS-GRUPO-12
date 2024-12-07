package views;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import domain.heladera.Heladera;
import domain.vianda.EnumEstadoVianda;
import domain.vianda.Vianda;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import persistence.Repos.RepoHeladera;
import persistence.Repos.RepoVianda;

public class UI_Vianda implements Handler{

  @Override
  public void handle(Context ctx) throws Exception {
    RepoHeladera hela = new RepoHeladera();

    Map<String, Object> model = new HashMap<>();
    model.put("hela", hela.getAll_Heladera());
    ctx.render("vianda.hbs", model);
  }

  // Metodo para agregar una vianda a la heladera
  public void agregarVianda(Context ctx) {
    System.out.println("estoy en agregarVianda");

    // Obtener parámetros del formulario (datos enviados en la solicitud)
    String comida = ctx.formParam("comida");
    String fechaVencimientoStr = ctx.formParam("fechaVencimiento");
    String fechaDonacionStr = ctx.formParam("fechaDonacion");
    String calorias = ctx.formParam("calorias");
    String pesoStr = ctx.formParam("peso");
    String heladeraId = ctx.formParam("heladeraId");

    // Convertir parámetros necesarios
    LocalDate fechaVencimiento = LocalDate.parse(fechaVencimientoStr);
    LocalDate fechaDonacion = LocalDate.parse(fechaDonacionStr);
    float peso = Float.parseFloat(pesoStr);
    Integer heladeraID = Integer.parseInt(heladeraId);
    EnumEstadoVianda estado = EnumEstadoVianda.NO_ENTREGADO;

    // Busca la heladera en la BD
    RepoHeladera hela = new RepoHeladera();
    Heladera heladera = hela.findById_Heladera(heladeraID);
    System.out.println("HelaName= "+heladera.getNombre());

    // Crea vianda
    Vianda vianda = new Vianda(comida, fechaVencimiento, fechaDonacion, calorias, peso, estado);
    vianda.setHeladera(heladera);

    RepoVianda vian = new RepoVianda();
    vian.add_Vianda(vianda);

    ctx.render("index.hbs");
  }

}
