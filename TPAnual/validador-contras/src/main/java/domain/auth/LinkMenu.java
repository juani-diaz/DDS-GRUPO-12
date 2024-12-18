package domain.auth;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LinkMenu extends PermisosMetodo {
    private String titulo;
    private String icono;

    public LinkMenu(String href, String titulo, String icono, boolean vFisica, boolean vOrganizacion, boolean vTecnico, boolean vAdministrador) {
        super(href, vFisica, vOrganizacion, vTecnico, vAdministrador);
        this.titulo = titulo;
        this.icono = icono;
    }
}
