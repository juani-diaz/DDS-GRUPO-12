package domain.heladera;

import domain.registro.RegistroSensor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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