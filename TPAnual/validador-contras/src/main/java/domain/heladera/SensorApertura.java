package domain.heladera;

import domain.registro.RegistroSensor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class SensorApertura extends Sensor {

  public SensorApertura(Heladera hel){
      super(hel);
  }

  @Override
  public void nuevoRegistro(RegistroSensor registro) {

  }
}
