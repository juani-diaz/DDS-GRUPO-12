package domain.persona;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Setter
@AllArgsConstructor
@Entity
@DiscriminatorValue("whatsapp")
public class WhatsApp extends MedioDeContacto {

    public WhatsApp(String telefono) {
        this.setContacto(telefono);
        this.setMedio(Medio.WHATSAPP);
    }
}
