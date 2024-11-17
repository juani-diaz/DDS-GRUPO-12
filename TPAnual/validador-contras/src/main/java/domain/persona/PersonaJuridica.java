package domain.persona;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@Entity
public class PersonaJuridica extends Persona{
    private String razonSocial;
    //@Enumerated(value = EnumType.STRING)
    private EnumTipoPersonaJuridica tipo;
    private String rubro;
}
