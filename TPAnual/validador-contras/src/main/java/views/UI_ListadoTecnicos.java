package views;

import domain.heladera.Heladera;
import domain.incidente.Incidente;
import domain.incidente.IncidenteFallaTecnica;
import domain.registro.SingletonSeguidorEstadistica;
import domain.rol.Colaborador;
import domain.rol.Tecnico;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.UploadedFile;
import persistence.ArchivosUtils;
import persistence.Repos.RepoColaborador;
import persistence.Repos.RepoHeladera;

import java.time.LocalDate;

public class UI_ListadoTecnicos extends UI_Navegable implements Handler {

    @Override
    public void handle(Context ctx) throws Exception {
        this.validarUsuario(ctx);

        this.model.put("incidentes", SingletonSeguidorEstadistica.getInstance().getIncidentesEnArea((Tecnico) getUsuario().getRol()));
        this.model.put("incidentes-fuera", SingletonSeguidorEstadistica.getInstance().getIncidentesFueraArea((Tecnico) getUsuario().getRol()));

        ctx.render("listado-tecnicos.hbs", this.model);
    }

    public void asignarTecnico(Context ctx) throws Exception {
        String incidenteId = ctx.formParam("incidenteId");
        Tecnico t = (Tecnico) getUsuario().getRol();

        Incidente incidente = SingletonSeguidorEstadistica.getInstance().findIncidenteById(Integer.parseInt(incidenteId));

        incidente.asignarTecnico(t);

        ctx.redirect("/listado-tecnicos");
    }

}
