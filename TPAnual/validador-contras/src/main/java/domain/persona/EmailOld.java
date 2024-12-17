package domain.persona;

import domain.servicios.Mailer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter @Setter @AllArgsConstructor
@Entity
@DiscriminatorValue("email")
public class EmailOld extends MedioDeContacto {

    public EmailOld(String mail) {
        this.setContacto(mail);
        this.setMedio(Medio.EMAIL);
    }
    public void notificar(String header,String mensaje){
        Mailer mailer= new Mailer();
        mailer.enviarMail(header,mensaje,this.getContacto());
    }
}
