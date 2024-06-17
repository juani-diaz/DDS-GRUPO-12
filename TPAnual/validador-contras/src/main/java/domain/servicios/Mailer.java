package domain.servicios;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class Mailer {
    private String body;
    private String header;
    private String destinatary;

    public void enviarMail() {

    }
}