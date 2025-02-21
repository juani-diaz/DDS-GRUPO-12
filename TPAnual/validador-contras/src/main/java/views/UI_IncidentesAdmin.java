package views;

import domain.heladera.EnumEstadoHeladera;
import domain.incidente.EnumEstadoDeIncidente;
import domain.incidente.Incidente;
import domain.registro.SingletonSeguidorEstadistica;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import persistence.Repos.RepoColaborador;
import persistence.Repos.RepoHeladera;


public class UI_IncidentesAdmin extends UI_Navegable implements Handler {

    @Override
    public void handle(Context ctx) throws Exception {
        this.validarUsuario(ctx);

        model.put("incidentes", SingletonSeguidorEstadistica.getInstance().getIncidentes());

        ctx.render("incidentes-a.hbs", this.model);
    }

    public void desasignar(Context ctx) throws Exception {
        String incidenteId = ctx.formParam("incidenteId");

        Incidente i = SingletonSeguidorEstadistica.getInstance().findIncidenteById(Integer.parseInt(incidenteId));
        i.setTecnico(null);
        i.setEstadoDeIncidente(EnumEstadoDeIncidente.PENDIENTE_A_SOLUCIONAR);
        SingletonSeguidorEstadistica.getInstance().updateIncidente(i);

        ctx.redirect("/index");
    }

    public void forzarSolucion(Context ctx) throws Exception {
        String incidenteId = ctx.formParam("incidenteId");

        Incidente i = SingletonSeguidorEstadistica.getInstance().findIncidenteById(Integer.parseInt(incidenteId));
        i.setEstadoDeIncidente(EnumEstadoDeIncidente.SOLUCIONADO);
        i.getHeladera().setEstado(EnumEstadoHeladera.DISPONIBLE);
        RepoHeladera.getInstance().updateHeladera(i.getHeladera());
        SingletonSeguidorEstadistica.getInstance().updateIncidente(i);

        ctx.redirect("/incidentes-a");
    }
}
