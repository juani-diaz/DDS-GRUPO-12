package domain.heladera;

import domain.registro.RegistroSensor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SensorApertura extends Sensor {

  public SensorApertura(Heladera hel){
    super(hel);
  }

  @Override
  public void nuevoRegistro(RegistroSensor registro) {

  }
}
