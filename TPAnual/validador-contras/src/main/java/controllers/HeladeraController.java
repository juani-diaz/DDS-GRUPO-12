package controllers;

import domain.heladera.*;
import domain.vianda.EnumEstadoVianda;
import domain.vianda.Vianda;
import io.javalin.http.Context;
import persistence.BDUtils;
import persistence.Repos.RepoHeladera;

import javax.persistence.EntityManager;
import java.time.LocalDate;


public class HeladeraController {

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

      //String estadoStr = ctx.formParam("estado");
      //String colaboradorId = ctx.formParam("colaboradorId");

      // Convertir parámetros necesarios
      LocalDate fechaVencimiento = LocalDate.parse(fechaVencimientoStr);
      LocalDate fechaDonacion = LocalDate.parse(fechaDonacionStr);
      float peso = Float.parseFloat(pesoStr);
      Integer heladeraID = Integer.parseInt(heladeraId);
      EnumEstadoVianda estado = EnumEstadoVianda.NO_ENTREGADO;

      //Se crea el EntityManager
      EntityManager em = BDUtils.getEntityManager();
      BDUtils.comenzarTransaccion(em);

      // Busca la heladera en la BD
      RepoHeladera hela = new RepoHeladera();
      System.out.println("HelaID= "+heladeraID);
      Heladera heladera = hela.findById_Heladera(heladeraID);
      System.out.println("HelaName= "+heladera.getNombre());
      Vianda vianda = new Vianda(comida, fechaVencimiento, fechaDonacion, calorias, peso, estado);
      vianda.setHeladera(heladera);

      try {
        em.persist(vianda);
      } catch (Exception e) {
        System.out.println("Error al agregar la vianda: " + e);
      }

      BDUtils.commit(em);

      ctx.render("index.hbs");
    }
}






