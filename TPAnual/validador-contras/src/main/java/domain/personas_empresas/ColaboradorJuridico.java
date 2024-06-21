package domain.personas_empresas;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ColaboradorJuridico extends PersonaColaboradora {
    private String razonSocial;
    private EnumTipoPersonaJuridica tipo;
    private String rubro;
}