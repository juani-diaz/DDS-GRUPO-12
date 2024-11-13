package controllers;

import domain.colaboraciones.*;

import domain.heladera.Heladera;
import domain.rol.Colaborador;
import domain.rol.Tarjeta;
import domain.vianda.Vianda;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.time.LocalDate;
import java.util.*;

public class ColaboracionController {

    private static Map<String, Colaborador> colaboradores = new HashMap<>();
    private static Map<String, Vianda> viandas = new HashMap<>();
    private static Map<String, Heladera> heladeras = new HashMap<>();

    public void DonacionDinero(Context ctx) {
         String colaboradorId = ctx.formParam("colaboradorId");
         Colaborador colaborador = colaboradores.get(colaboradorId);
         String fechaDonacionString = ctx.formParam("fechaDonacion");
         String montoString = ctx.formParam("monto");
         String frecuenciaString = ctx.formParam("frecuencia");

         // Convertimos los Strings a los tipos requeridos
         // Convertir a Colaborador
         // Convertir a LocalDate
         LocalDate fecha = LocalDate.parse(fechaDonacionString);
         Float monto = Float.parseFloat(montoString);

         //Crear donacion de dinero con los parametros recibidos
         DonacionDinero donacionDinero = new DonacionDinero(colaborador,fecha,monto,frecuenciaString);

         donacionDinero.ejecutar();

        // Crear la donación con los parámetros obtenidos
        DonacionDinero donacion = new DonacionDinero(colaborador, fecha, monto, frecuenciaString);

        // Ejecuta la lógica para guardar o procesar la donación (puedes agregar lógica aquí)
        // Ejemplo: guardar donación en base de datos o en una lista
    }

    private List<DonacionVianda> donaciones = new ArrayList<>();
    private int idCounter = 1;



    public void donacionVianda (Context ctx) {


        String colaboradorId = ctx.formParam("colaboradorId");

        LocalDate fecha = LocalDate.parse(ctx.formParam("fecha"));
        Colaborador colaborador = colaboradores.get(colaboradorId); // Aquí se instancia Colaborador

        //Recibo el vianda Id como string para despues conseguir la vianda a traves de la funcion de hash
        String viandaId = ctx.formParam("vianda");
        Vianda vianda = viandas.get(viandaId); // Obtengo la vianda

        //Recibo el heladera Id como string para despues conseguir la heladera a traves de la funcion de hash
        String heladeraId = ctx.formParam("heladera");
        Heladera heladeraDestino = heladeras.get(heladeraId); // y el destino (Heladera)

        DonacionVianda donacion = new DonacionVianda(colaborador, fecha, vianda, heladeraDestino);

        donaciones.add(donacion);

        donacion.ejecutar();
    };

}
