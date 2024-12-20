package domain.servicios;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import java.io.IOException;

public class TwilioSendGrid {
  public static void main(String[] args) throws IOException {
    Email from = new Email("juanpolito02@gmail.com");
    String subject = "Sending with SendGrid is Fun2";
    Email to = new Email("juanpolito02+01@gmail.com");
    Content content = new Content("text/plain", "and easy to do anywhere, even with Java22");
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
