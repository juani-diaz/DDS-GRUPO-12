package views;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import persistence.Repos.RepoColaborador;
import persistence.Repos.RepoHeladera;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;


public class UI_Reporte extends UI_Navegable implements Handler {

    private static LocalDate obtenerFechaDesde(String semanaISO) {
        String[] partes = semanaISO.split("-W");
        int anio = Integer.parseInt(partes[0]);
        int semana = Integer.parseInt(partes[1]);

        // Obtener la primera fecha de la semana
        return LocalDate.ofYearDay(anio, 1)
                .with(WeekFields.of(Locale.getDefault()).weekOfYear(), semana)
                .with(DayOfWeek.MONDAY);
    }

    @Override
    public void handle(Context ctx) throws Exception {
        this.validarUsuario(ctx);

        RepoHeladera hela = RepoHeladera.getInstance();
        RepoColaborador cola = RepoColaborador.getInstance();

        model.put("helaFallos", hela.obtenerFallasxHeladera());
        model.put("colaboradorDatos", cola.obtenerDonacionesxColaborador());

        System.out.println(model);
        ctx.render("reportes.hbs", this.model);
    }

    public void mostrarReportePorSemana(Context ctx){
        System.out.println("estoy en mostrarReportePorSemana");

        RepoHeladera hela = RepoHeladera.getInstance();
        RepoColaborador cola = RepoColaborador.getInstance();

        // Obtener parámetros del formulario (datos enviados en la solicitud)
        String semana = ctx.formParam("semana");
        LocalDate fechaDesde = obtenerFechaDesde(semana);
        LocalDate fechaHasta = fechaDesde.plusDays(6);
        String semanawhere= "'"+fechaDesde+ "' and '"+fechaHasta+"'";
        model.put("helaFallos", hela.obtenerFallasxHeladeraxSemana(semanawhere));
        model.put("colaboradorDatos", cola.obtenerDonacionesxColaboradorxSemana(semanawhere));
        System.out.println("semana: " + semana);
        System.out.println(model);
        ctx.render("reportes.hbs", this.model);
    }
}
