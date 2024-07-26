package domain.suscripcion;

import domain.heladera.Heladera;
import domain.persona.MedioDeContacto;

public abstract class Suscripcion {
  Heladera heladera;
  MedioDeContacto notificadores;

  private void notificar(){}//TODO:hacer funcion
}
