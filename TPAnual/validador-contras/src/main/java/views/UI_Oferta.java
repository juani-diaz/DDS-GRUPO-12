package views;

import domain.colaboraciones.PresentacionOferta;
import domain.rol.Colaborador;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.UploadedFile;
import persistence.ArchivosUtils;
import persistence.Repos.RepoColaborador;

import java.time.LocalDate;

public class UI_Oferta extends UI_Navegable implements Handler {

    @Override
    public void handle(Context ctx) throws Exception {
        this.validarUsuario(ctx);

        ctx.render("oferta.hbs", this.model);
    }

    public void nuevaOferta(Context ctx) throws Exception {
        String nombre = ctx.formParam("nombre");
        String descripcion = ctx.formParam("descripcion");
        String rubro = ctx.formParam("rubro");
        Float puntosNecesarios = Float.parseFloat(ctx.formParam("puntosNecesarios"));

        UploadedFile uploadedFile = ctx.uploadedFile("imagen");

        String imagen = null;
        if (uploadedFile != null) {
            imagen = ArchivosUtils.getInstance().guardarArchivo("ofertas", uploadedFile);
        } else {
            System.out.println("No se subio una imagen");
        }

        Colaborador c = RepoColaborador.getInstance().findByUsuario(getUsuario().getUsuario());

        PresentacionOferta po = new PresentacionOferta(c, LocalDate.now(), rubro, nombre, descripcion, puntosNecesarios, imagen);

        c.realizarColaboracion(po);

        ctx.redirect("/ofertas");
    }
}
