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

    //Como no está el ORM tenemos que usar algo para guardar
    private static Map<String, Colaborador> colaboradores = new HashMap<>();
    private static Map<String, Heladera> heladeras = new HashMap<>();

    public Vianda sacarVianda(Context ctx) {
        String heladeraId = ctx.formParam("heladeraId");
        int indice = Integer.parseInt(ctx.formParam("indice"));

        Heladera heladera = HeladeraController.obtenerHeladera(heladeraId);

        //Buscar la heladera en el almacenamiento en memoria
        //Heladera heladera = heladeras.get(heladeraId);

        // Validación: verificar que la heladera exista
       // if (heladera == null) {
        //    ctx.result("Heladera no encontrada");
        //    return null;
        //}
        // Sacar la vianda en el índice especificado
        Vianda vianda = heladera.sacarVianda(indice);

        // Retornar la vianda sacada y enviar una respuesta
        ctx.result("Vianda retirada exitosamente.");
        return vianda;
    }

      private static Heladera obtenerHeladera(String heladeraId) {
        //Buscar la heladera en el almacenamiento en memoria
        Heladera heladera = heladeras.get(heladeraId);

        // Validación: verificar que la heladera exista
        if (heladera == null) {
          System.out.println("la heladera" + heladeraId + "no existe");
          return null;
        }
        return heladera; // Simulación: obtén la heladera desde la base de datos o un repositorio
    }

    // Metodo para agregar una vianda a la heladera
    public void agregarVianda(Context ctx) {
      System.out.println("estoy en agregarVianda");

      // Obtener parámetros del formulario (datos enviados en la solicitud)
      String comida = ctx.formParam("comida");
      System.out.println(comida);
      String fechaVencimientoStr = ctx.formParam("fechaVencimiento");
      String fechaDonacionStr = ctx.formParam("fechaDonacion");
      String calorias = ctx.formParam("calorias");
      String pesoStr = ctx.formParam("peso");
      //String colaboradorId = ctx.formParam("colaboradorId");
      String heladeraId = ctx.formParam("heladeraId");
      //String estadoStr = ctx.formParam("estado");

      // Convertir parámetros necesarios
      LocalDate fechaVencimiento = LocalDate.parse(fechaVencimientoStr);
      LocalDate fechaDonacion = LocalDate.parse(fechaDonacionStr);
      float peso = Float.parseFloat(pesoStr);
      EnumEstadoVianda estado = EnumEstadoVianda.NO_ENTREGADO;


      // Buscar la heladera en el almacenamiento en BD
      EntityManager em = BDUtils.getEntityManager();
      BDUtils.comenzarTransaccion(em);
      Heladera heladera = HeladeraController.obtenerHeladera(heladeraId);
      try {
        System.out.println("try  1111");
        heladera = em.find(Heladera.class, heladeraId);
      } catch (Exception e) {
        System.out.println("Error al agregar la heladera: " + e);
      }

      Vianda vianda = new Vianda(comida, fechaVencimiento, fechaDonacion, calorias, peso, estado);
      vianda.setHeladera(heladera);

      try {
        System.out.println("try  2");
        em.persist(vianda);
      } catch (Exception e) {
        System.out.println("Error al agregar la vianda: " + e);
      }

      BDUtils.commit(em);

      //ctx.result("Vianda agregada exitosamente.");
    }

    //Metodo para obtener el colaborador por su ID
    //La idea es implementar esto para cuando haya una base de datos
    //private Colaborador obtenerColaboradorPorId(String colaboradorId) {
      //  return new Colaborador();
    //}
}






