package views;

import domain.auth.Usuario;
import domain.heladera.EnumEstadoHeladera;
import domain.heladera.Heladera;
import domain.rol.Tecnico;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import lombok.NoArgsConstructor;
import obs.RespuestaCliente;
import persistence.Repos.RepoHeladera;
import persistence.Repos.RepoUsuarios;

import java.util.List;

@NoArgsConstructor
public class UI_TecnicosA extends UI_Navegable implements Handler {

    @Override
    public void handle(Context ctx) throws Exception {
        this.validarUsuario(ctx);

        List<Usuario> tec = RepoUsuarios.getInstance().getUsuarios().stream().filter(u -> u.getRol().getClass() == Tecnico.class).toList();

        model.put("usuariosTecnico", tec);

        ctx.render("tecnicos-a.hbs", this.model);
    }

    public void habilitarTecnico(Context ctx) {
        Integer usuarioId = Integer.valueOf(ctx.formParam("usuarioId"));

        Usuario u = RepoUsuarios.getInstance().findById_Usuario(usuarioId);
        Tecnico t = (Tecnico) u.getRol();
        t.setAprobadoPorAdmin(true);

        RepoUsuarios.getInstance().update_Usuario(u);

        RespuestaCliente.exito(getUsuario(), "/tecnicos-a", "Tecnico habilitado", ctx);
    }

    public void deshabilitarTecnico(Context ctx) {
        Integer usuarioId = Integer.valueOf(ctx.formParam("usuarioId"));

        Usuario u = RepoUsuarios.getInstance().findById_Usuario(usuarioId);
        Tecnico t = (Tecnico) u.getRol();
        t.setAprobadoPorAdmin(false);

        RepoUsuarios.getInstance().update_Usuario(u);

        RespuestaCliente.exito(getUsuario(), "/tecnicos-a", "Tecnico deshabilitado", ctx);
    }
}
