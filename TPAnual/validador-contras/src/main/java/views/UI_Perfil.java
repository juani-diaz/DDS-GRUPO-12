package views;

import domain.persona.PersonaFisica;
import domain.persona.PersonaJuridica;
import domain.rol.Administrador;
import domain.rol.Colaborador;
import domain.rol.Tecnico;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UI_Perfil extends UI_Navegable implements Handler {

    @Override
    public void handle(Context ctx) throws Exception {
        this.validarUsuario(ctx);

        String tipoUsuario = "?";
        if (getUsuario().getRol().getClass().equals(Administrador.class)) {
            tipoUsuario = "admin";
        } else if (getUsuario().getRol().getClass().equals(Colaborador.class) && getUsuario().getRol().getPersona().getClass() == PersonaFisica.class) {
            tipoUsuario = "persona";
        } else if (getUsuario().getRol().getClass().equals(Colaborador.class) && getUsuario().getRol().getPersona().getClass() == PersonaJuridica.class) {
            tipoUsuario = "organizacion";
        } else if (getUsuario().getRol().getClass().equals(Tecnico.class)) {
            tipoUsuario = "tecnico";
        }
        this.model.put("tipoUsuario", tipoUsuario);

        System.out.println(getUsuario().getRol().getPersona().getMediosDeContacto());
        this.model.put("persona", getUsuario().getRol().getPersona());

        ctx.render("app-profile.hbs", this.model);
    }
}
