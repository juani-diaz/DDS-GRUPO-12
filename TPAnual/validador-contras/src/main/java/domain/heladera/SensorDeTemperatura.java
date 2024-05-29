package domain.heladera;

import domain.heladera.RegistroTemperatura;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class SensorDeTemperatura extends Sensor{
    private List<RegistroTemperatura> temperaturas;
}