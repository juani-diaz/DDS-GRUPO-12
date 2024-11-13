package controllers;

import domain.heladera.*;
import java.util.HashMap;
import java.util.Map;
import domain.rol.Colaborador;
import domain.vianda.EnumEstadoVianda;
import domain.vianda.Vianda;
import io.javalin.http.Context;
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

        // Buscar la heladera en el almacenamiento en memoria
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
    }

    //Esta la dejamos para obtener una heladera por ID cuando tengamos la base de datos
    //  private Heladera obtenerHeladera(Context ctx) {
    //    String heladeraId = ctx.formParam("heladeraId");
    //      Buscar la heladera por su ID
    //    return new Heladera(); // Simulación: obtén la heladera desde la base de datos o un repositorio
    //}

    // Metodo para agregar una vianda a la heladera
    public void agregarVianda(Context ctx) {
        // Obtener parámetros del formulario (datos enviados en la solicitud)
        String comida = ctx.formParam("comida");
        String fechaVencimientoStr = ctx.formParam("fechaVencimiento");
        String fechaDonacionStr = ctx.formParam("fechaDonacion");
        String calorias = ctx.formParam("calorias");
        String pesoStr = ctx.formParam("peso");
        String colaboradorId = ctx.formParam("colaboradorId");
        String heladeraId = ctx.formParam("heladeraId");
        String estadoStr = ctx.formParam("estado");


        // Convertir parámetros necesarios
        LocalDate fechaVencimiento = LocalDate.parse(fechaVencimientoStr);
        LocalDate fechaDonacion = LocalDate.parse(fechaDonacionStr);
        float peso = Float.parseFloat(pesoStr);
        EnumEstadoVianda estado = Enum.valueOf(EnumEstadoVianda.class, estadoStr);

        // Buscar el colaborador y la heladera en el almacenamiento en memoria
        Colaborador colaborador = colaboradores.get(colaboradorId);
        Heladera heladera = heladeras.get(heladeraId);

        // Validación: verificar que existan colaborador y heladera
        if (colaborador == null || heladera == null) {
            ctx.result("Colaborador o Heladera no encontrados");
            return;
        }

        // Crear una nueva vianda con los datos recibidos
        Vianda vianda = new Vianda();
        vianda.setComida(comida);
        vianda.setFechaVencimiento(fechaVencimiento);
        vianda.setFechaDonacion(fechaDonacion);
        vianda.setCalorias(calorias);
        vianda.setPeso(peso);
        vianda.setColaborador(colaborador);
        vianda.setHeladera(heladera);

        // Agregar la vianda a la heladera
        heladera.ingresarViandas(Collections.singletonList(vianda));
        ctx.result("Vianda agregada exitosamente.");
    }

    // Metodo para obtener el colaborador por su ID
    //La idea es implementar esto para cuando haya una base de datos
    //private Colaborador obtenerColaboradorPorId(String colaboradorId) {

      //  return new Colaborador();


    //}
}