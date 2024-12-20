package domain.servicios;

import com.twilio.Twilio;
import com.twilio.converter.Promoter;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.net.URI;
import java.math.BigDecimal;

//NO USAR -> POCOS CREDITOS
public class TwilioWhatsapp {
    // Find your Account Sid and Token at twilio.com/console
    public static final String ACCOUNT_SID = "";
    public static final String AUTH_TOKEN = "";

  public static void main(String[] args) {
    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    Message message = Message.creator(
        new com.twilio.type.PhoneNumber("whatsapp:+5491136586529"),
        new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
        "Your appointment is coming up on July 21 at 3PM")
            .create();

    System.out.println(message.getSid());
  }
}
