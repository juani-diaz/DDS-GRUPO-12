package domain.servicios;

import lombok.Getter;
import lombok.Setter;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Setter @Getter
public class Mailer {
    private String header;
    private String body;
    private String destinatary;
    private String username; //habria que crear un mail para aca setear pass y user, talvez dockerizar para seguridad
    private String password;

    public Mailer(String header, String body, String destinatary) {
        this.header = header;
        this.body = body;
        this.destinatary = destinatary;
        this.username = username;
        this.password = password;
    }

    public void enviarMail() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatary));
            message.setSubject(header);
            message.setText(body);

            Transport.send(message);

            System.out.println("Correo enviado exitosamente");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
