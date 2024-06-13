package domain.heladera;

import domain.heladera.RegistroTemperatura;
import lombok.Getter;
import lombok.Setter;


import java.util.List;
@Getter
@Setter
public class SensorDeTemperatura extends Sensor{
    private List<RegistroTemperatura> temperaturas;

    public Float getUltimaTemperatura() {
        return temperaturas.get(temperaturas.size()-1).getTemperatura();
    }
}