package domain.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class PermisosMetodo {
    private String href;
    private boolean vFisica;
    private boolean vOrganizacion;
    private boolean vTecnico;
    private boolean vAdministrador;
}
