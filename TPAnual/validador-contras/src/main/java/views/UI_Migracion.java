package views;

import domain.auth.JwtUtil;
import domain.persona.Documento;
import domain.persona.Persona;
import domain.persona.PersonaFisica;
import domain.rol.Colaborador;
import domain.servicios.Catalogo;
import domain.servicios.LectorCsv;
import domain.servicios.Mailer;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.UploadedFile;
import io.jsonwebtoken.Claims;
import obs.RespuestaCliente;
import persistence.BDUtils;
import persistence.Repos.RepoColaborador;

import javax.persistence.EntityManager;
import java.io.InputStream;
import java.util.List;


public class UI_Migracion extends UI_Navegable implements Handler {



    @Override
    public void handle(Context ctx) throws Exception {
        this.validarUsuario(ctx);
        if (this.sesionValida(ctx)) {


            ctx.render("migracion.hbs", model);
        }
    }

    public void cargarArchivoCSV(Context ctx) throws Exception {
        UploadedFile archivoSubido = ctx.uploadedFile("archivo");

        if (archivoSubido == null) {
            ctx.status(400).result("No se ha subido ningún archivo.");
            return;
        }

        Mailer mailer = new Mailer();


        InputStream archivoStream = archivoSubido.content();

        LectorCsv lector = new LectorCsv(archivoStream, mailer);
        List<Colaborador> colaboradores = lector.cargarArchivo();
        EntityManager em = BDUtils.getEntityManager();
        BDUtils.comenzarTransaccion(em);


        colaboradores.forEach(colaborador-> {
            Persona persona= colaborador.getPersona();
            Documento documento=persona.getDocumento();
            em.persist(documento);
            em.persist(persona);
            em.persist(colaborador);
        });

        BDUtils.commit(em);


        RespuestaCliente.exito(getUsuario(), "/index", "CSV Cargado", ctx);
    }


}






