package domain.servicios;

@Getter @Setter
public class Mailer {
    private String body;
    private String header;
    private String destinatary;

    public void enviarMail() {
        // TODO leer el csv y crear los colaboradores
    }
}