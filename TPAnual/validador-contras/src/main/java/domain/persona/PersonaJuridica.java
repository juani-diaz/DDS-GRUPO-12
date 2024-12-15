package domain.persona;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

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

    public PersonaJuridica(String razonSocial, EnumTipoPersonaJuridica tipo, String rubro, String direccion, Documento documento, List<MedioDeContacto> mediosDeContacto) {
        super(razonSocial,direccion, documento, mediosDeContacto);
        this.razonSocial = razonSocial;
        this.tipo = tipo;
        this.rubro = rubro;
    }

}
