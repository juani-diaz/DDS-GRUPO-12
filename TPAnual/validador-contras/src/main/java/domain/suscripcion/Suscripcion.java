package domain.suscripcion;

import domain.heladera.Heladera;
import domain.persona.MedioDeContacto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class Suscripcion {
  Heladera heladera;
  MedioDeContacto notificadores;

  String header;

  String mensaje;

  void notificar(){
    notificadores.notificar(header,mensaje);
  }
}
