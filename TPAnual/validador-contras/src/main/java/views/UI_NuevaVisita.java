package views;

import domain.heladera.EnumEstadoHeladera;
import domain.heladera.Heladera;
import domain.incidente.EnumEstadoDeIncidente;
import domain.incidente.Incidente;
import domain.incidente.IncidenteFallaTecnica;
import domain.incidente.VisitasTecnicas;
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

public class UI_NuevaVisita extends UI_Navegable implements Handler {

    @Override
    public void handle(Context ctx) throws Exception {
        this.validarUsuario(ctx);

        String idIncidente = ctx.queryParam("id");
        if(idIncidente != null) {
            Incidente incidente = SingletonSeguidorEstadistica.getInstance().findIncidenteById(Integer.parseInt(idIncidente));
            this.model.put("incidente", incidente);
            ctx.render("nueva-visita.hbs", this.model);
        } else {
            ctx.redirect("/index");
        }
    }

    public void nuevaVisita(Context ctx) throws Exception {
        String idIncidente = ctx.formParam("id");
        Incidente incidente = SingletonSeguidorEstadistica.getInstance().findIncidenteById(Integer.parseInt(idIncidente));

        String descripcion = ctx.formParam("descripcion");

        UploadedFile uploadedFile = ctx.uploadedFile("imagen");

        String imagen = null;
        if (uploadedFile != null && uploadedFile.size() > 0) {
            imagen = ArchivosUtils.getInstance().guardarArchivo("fallas", uploadedFile);
        } else {
            System.out.println("No se subio una imagen");
        }

        Tecnico t = (Tecnico) getUsuario().getRol();
        VisitasTecnicas vt = new VisitasTecnicas(t, incidente, LocalDate.now(), descripcion, imagen);
        incidente.getEvolucionDeIncidente().add(vt);

        if(ctx.formParam("solucionado") != null){
            incidente.setEstadoDeIncidente(EnumEstadoDeIncidente.SOLUCIONADO);
            incidente.getHeladera().setEstado(EnumEstadoHeladera.DISPONIBLE);
            RepoHeladera.getInstance().updateHeladera(incidente.getHeladera());
        }

        SingletonSeguidorEstadistica.getInstance().updateIncidente(incidente);

        ctx.redirect("/index");
    }
}
