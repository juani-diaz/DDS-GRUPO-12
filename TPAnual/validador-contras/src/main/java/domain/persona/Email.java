package domain.persona;

import domain.servicios.Mailer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@Getter @Setter @AllArgsConstructor
public class Email extends MedioDeContacto {

    public Email(String mail) {
        this.setEmail(mail);
    }
public void notificar(String header,String mensaje){

    Mailer mailer= new Mailer();
    mailer.enviarMail(header,mensaje,this.direccion);
}
}
