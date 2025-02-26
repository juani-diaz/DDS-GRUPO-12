package domain.servicios;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import java.io.IOException;

public class TwilioSendGrid {

  private Email from = new Email("juanpolito02@gmail.com");


  public static void sendEmail(String dirCorreo, String subject, String mensage) throws IOException {

    Email to = new Email(dirCorreo);
    Content content = new Content("text/html", mensage);
    System.out.println(dirCorreo);
    Email from = new Email("juanpolito02@gmail.com");

    Mail mail = new Mail(from, subject, to, content);

    SendGrid sg = new SendGrid("");//key en descripcion de grupo de Whatsapp
    Request request = new Request();
    try {
      request.setMethod(Method.POST);
      request.setEndpoint("mail/send");
      request.setBody(mail.build());
      Response response = sg.api(request);
      System.out.println(response.getStatusCode());
      System.out.println(response.getBody());
      System.out.println(response.getHeaders());
    } catch (IOException ex) {
      throw ex;
    }
  }
}
