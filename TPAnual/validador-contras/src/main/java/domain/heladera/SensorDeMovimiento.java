package domain.heladera;
import domain.registro.RegistroSensor;
import lombok.*;

import javax.persistence.Entity;


@Getter @Setter @NoArgsConstructor
@Entity
public class SensorDeMovimiento extends Sensor{

    public SensorDeMovimiento(Heladera hel){
        super(hel);
    }

    @Override
    public void nuevoRegistro(RegistroSensor registro) {
        this.enviarAlerta(registro);
    }
}