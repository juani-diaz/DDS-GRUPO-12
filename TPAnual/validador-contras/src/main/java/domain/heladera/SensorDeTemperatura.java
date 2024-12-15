package domain.heladera;

import domain.registro.RegistroSensor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Getter @Setter @NoArgsConstructor
@Entity
public class SensorDeTemperatura extends Sensor{

    public SensorDeTemperatura(Heladera hel){
        super(hel);
    }

    @Override
    public void nuevoRegistro(RegistroSensor registro) {
/*        Float temp = ((RegistroTemperatura) registro).getTemperatura(); // TODO cosas raras abstract Java
        if(temp < this.heladeraAsignada.getTemperaturaMinima() || temp > this.heladeraAsignada.getTemperaturaMaxima()) {
            this.enviarAlerta(registro);
            this.heladeraAsignada.setEstado(EnumEstadoHeladera.INACTIVA_POR_ALERTA);
        } else {
            this.heladeraAsignada.setEstado(EnumEstadoHeladera.DISPONIBLE);
        }*/
    }
}