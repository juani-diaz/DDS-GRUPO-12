package domain.persona;

import domain.servicios.Mailer;
import domain.servicios.TwilioSendGrid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.IOException;

@Getter @Setter @AllArgsConstructor
@Entity
@DiscriminatorValue("emailDir")
public class EmailDir extends MedioDeContacto {

    public EmailDir(String mail) {
        this.setContacto(mail);
        this.setMedio(Medio.EMAIL);
    }
    public void notificar(String header,String mensaje) throws IOException {
        TwilioSendGrid.sendEmail(contacto,header, mensaje);
    }
}
