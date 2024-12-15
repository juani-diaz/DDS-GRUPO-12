package domain.persona;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Setter
@AllArgsConstructor
@Entity
@DiscriminatorValue("telefono")
public class Telefono extends MedioDeContacto {

    public Telefono(String telefono) {
        this.setContacto(telefono);
        this.setMedio(Medio.TELEFONO);
    }
}
