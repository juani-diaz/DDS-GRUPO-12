package views;

import domain.colaboraciones.PresentacionOferta;
import domain.heladera.Heladera;
import domain.incidente.IncidenteFallaTecnica;
import domain.rol.Colaborador;
import domain.suscripcion.Publicador;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.UploadedFile;
import obs.RespuestaCliente;
import persistence.ArchivosUtils;
import persistence.Repos.RepoColaborador;
import persistence.Repos.RepoHeladera;

import java.time.LocalDate;

public class UI_ReportarFalla extends UI_Navegable implements Handler {

    @Override
    public void handle(Context ctx) throws Exception {
        this.validarUsuario(ctx);

        String idHela = ctx.queryParam("id");
        if(idHela != null) {
            Heladera heladeraFalla = RepoHeladera.getInstance().findById_Heladera(Integer.parseInt(idHela));
            this.model.put("heladera", heladeraFalla);
            ctx.render("reportar-falla.hbs", this.model);
        } else {
            ctx.redirect("/index");
        }
    }

    public void reportarFalla(Context ctx) throws Exception {
        String id = ctx.formParam("id");
        Heladera heladera = RepoHeladera.getInstance().findById_Heladera(Integer.parseInt(id));

        String resumen = ctx.formParam("resumen");
        String descripcion = ctx.formParam("descripcion");
        descripcion = resumen + "\n\n= = = = =\n\n" + descripcion;

        UploadedFile uploadedFile = ctx.uploadedFile("imagen");

        String imagen = null;
        if (uploadedFile != null && uploadedFile.size() > 0) {
            imagen = ArchivosUtils.getInstance().guardarArchivo("archivos_fallas", uploadedFile);
        } else {
            System.out.println("No se subio una imagen");
        }

        Colaborador c = RepoColaborador.getInstance().findByUsuario(getUsuario().getUsuario());

        new IncidenteFallaTecnica(heladera, LocalDate.now(), c, descripcion, imagen);
        Publicador pub=new Publicador();
        pub.notifyObservers();

        RespuestaCliente.exito(getUsuario(), "/index", "Falla reportada", ctx);
    }
}
