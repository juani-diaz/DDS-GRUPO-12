package domain.suscripcion;

import domain.heladera.Heladera;
import domain.persona.MedioDeContacto;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ExcesoViandas extends Suscripcion{
  private Integer numeroMaximo;

  public ExcesoViandas(Heladera heladera, MedioDeContacto notificadores, Integer numeroMaximo){
    this.heladera=heladera;
    this.notificadores=notificadores;
    this.numeroMaximo = numeroMaximo;
    header="Notificacion por suscripcion";
    this.mensaje="Faltan "+numeroMaximo+" viandas para que la heladera esté llena y no se puedan ingresar más viandas";
  }
}
