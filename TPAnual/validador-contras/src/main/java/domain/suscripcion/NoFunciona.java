package domain.suscripcion;

import domain.heladera.Heladera;
import domain.persona.MedioDeContacto;
import domain.servicios.TwilioSendGrid;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.io.IOException;

import static domain.heladera.EnumEstadoHeladera.INACTIVA_POR_ALERTA;
import static domain.heladera.EnumEstadoHeladera.INACTIVA_POR_FALLA;

@Getter @Setter
@Entity
public class NoFunciona extends Suscripcion {

    public NoFunciona() {
    }
    @Override
    void notificar() throws IOException {
        String subject =
                "Suscripcion a heladeraID "+ this.getHeladera();
        String mensaje =
            "<h1>Hola <strong>" + this.getColaborador().getPersona().getNombre() + "</strong></h1>," +
                "<p>La heladera <strong>"+ this.getHeladera().getNombre() +"</strong>"+
                " no funciona.";

    this.notificadores.notificar(subject,mensaje);

    }

    @Override
    boolean condicion(Integer cantidad) {
        return heladera.getEstado()==INACTIVA_POR_FALLA || heladera.getEstado()==INACTIVA_POR_ALERTA;
    }

    public NoFunciona(Heladera heladera, MedioDeContacto notificadores){
        this.heladera=heladera;
        this.notificadores=notificadores;
        header="Notificacion por suscripcion";
        mensaje="La heladera sufrió un desperfecto y las viandas deben ser llevadas a otras heladeras a la brevedad para que las mismas no se echen a perder";
    }
    public NoFunciona(Heladera heladera){
        this.heladera=heladera;
        this.notificadores=null;
        header="Notificacion por suscripcion";
        mensaje="La heladera sufrió un desperfecto y las viandas deben ser llevadas a otras heladeras a la brevedad para que las mismas no se echen a perder";
    }
}