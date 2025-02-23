package views;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import persistence.Repos.RepoColaborador;
import persistence.Repos.RepoHeladera;


public class UI_Reporte extends UI_Navegable implements Handler {

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

        // Obtener parámetros del formulario (datos enviados en la solicitud)
        String semana = ctx.formParam("semana");
        System.out.println("semana: " + semana);
    }
}
