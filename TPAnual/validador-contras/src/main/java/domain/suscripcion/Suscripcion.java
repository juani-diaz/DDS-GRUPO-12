package domain.suscripcion;

import domain.heladera.Heladera;
import domain.persona.MedioDeContacto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor @Getter @Setter
public abstract class Suscripcion {
  Heladera heladera;
  MedioDeContacto notificadores;

  private void notificar(){
    notificadores.noificar();
  }//TODO:hacer funcion
}
