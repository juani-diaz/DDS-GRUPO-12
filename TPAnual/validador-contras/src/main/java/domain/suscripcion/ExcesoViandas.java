package domain.suscripcion;

import domain.heladera.Heladera;
import domain.persona.MedioDeContacto;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ExcesoViandas extends Suscripcion{
  private Integer numeroMaximo;

  public ExcesoViandas(Heladera heladera, MedioDeContacto notificadores, Integer numeroMaximo){
    super(heladera, notificadores);
    this.numeroMaximo = numeroMaximo;
  }
}
