package domain.persona;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonaJuridica extends Persona{
    private String razonSocial;
    private EnumTipoPersonaJuridica tipo;
    private String rubro;
}
