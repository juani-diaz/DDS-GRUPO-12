package domain.persona;

import domain.servicios.Mailer;

import lombok.Getter;
import lombok.Setter;
@Getter @Setter
public class Email extends MedioDeContacto {
public void notificar(String header,String mensaje){

    Mailer mailer= new Mailer();
    mailer.enviarMail(header,mensaje,this.direccion);
}
}
