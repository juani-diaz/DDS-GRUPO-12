package domain.persona;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.XSlf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PersonaJuridica extends Persona{

    @Column
    private String razonSocial;

    @Enumerated(value = EnumType.STRING)
    private EnumTipoPersonaJuridica tipo;

    @Column
    private String rubro;
}
