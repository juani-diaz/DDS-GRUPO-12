package controllers;

import domain.heladera.*;
import java.util.HashMap;
import java.util.Map;
import domain.rol.Colaborador;
import domain.vianda.EnumEstadoVianda;
import domain.vianda.Vianda;
import io.javalin.http.Context;
import persistence.BDUtils;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class HeladeraController {

  //private Collection<Heladera> heladeras;

      private static Heladera obtenerHeladera(EntityManager em,Long heladeraID_Long) {

        Heladera heladera = null;

        try {
          heladera = em.getReference(Heladera.class, heladeraID_Long);
        } catch (Exception e) {
          System.out.println("Error al agregar la heladera: " + e + " en HeladeraController.obtenerHeladera");
        }
        return heladera;
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

      //String estadoStr = ctx.formParam("estado");
      //String colaboradorId = ctx.formParam("colaboradorId");

      // Convertir parámetros necesarios
      LocalDate fechaVencimiento = LocalDate.parse(fechaVencimientoStr);
      LocalDate fechaDonacion = LocalDate.parse(fechaDonacionStr);
      float peso = Float.parseFloat(pesoStr);
      Long heladeraID_Long = Long.parseLong(heladeraId);
      EnumEstadoVianda estado = EnumEstadoVianda.NO_ENTREGADO;

      //Se crea el EntityManager
      EntityManager em = BDUtils.getEntityManager();
      BDUtils.comenzarTransaccion(em);

      // Busca la heladera en la BD
      Heladera heladera = HeladeraController.obtenerHeladera(em, heladeraID_Long);

      Vianda vianda = new Vianda(comida, fechaVencimiento, fechaDonacion, calorias, peso, estado);
      vianda.setHeladera(heladera);

      try {
        em.persist(vianda);
      } catch (Exception e) {
        System.out.println("Error al agregar la vianda: " + e);
      }

      BDUtils.commit(em);

      //ctx.result("Vianda agregada exitosamente.");
    }

 /*public Vianda sacarVianda(Context ctx) {
        String heladeraId = ctx.formParam("heladeraId");
        int indice = Integer.parseInt(ctx.formParam("indice"));

        //Buscar la heladera en el almacenamiento en memoria
        Heladera heladera = heladeras.get(heladeraId);

        // Validación: verificar que la heladera exista
        if (heladera == null) {
            ctx.result("Heladera no encontrada");
            return null;
        }
        // Sacar la vianda en el índice especificado
        Vianda vianda = heladera.sacarVianda(indice);

        // Retornar la vianda sacada y enviar una respuesta
        ctx.result("Vianda retirada exitosamente.");
        return vianda;
    }*/

    //Metodo para obtener el colaborador por su ID
    //La idea es implementar esto para cuando haya una base de datos
    //private Colaborador obtenerColaboradorPorId(String colaboradorId) {
      //  return new Colaborador();
    //}
}






