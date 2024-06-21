package domain.heladera;
import lombok.*;

import java.util.Date;


@Getter @Setter @NoArgsConstructor
public class SensorDeMovimiento extends Sensor{

    public SensorDeMovimiento(Heladera hel){
        super(hel);
    }

    @Override
    public void nuevoRegistro(RegistroSensor registro) {
        this.registros.add(registro);
        this.enviarAlerta();
    }
}