package domain.heladera;

import domain.registro.RegistroSensor;
import domain.registro.RegistroTemperatura;
import lombok.*;

@Getter @Setter @NoArgsConstructor
public class SensorDeTemperatura extends Sensor{

    public SensorDeTemperatura(Heladera hel){
        super(hel);
    }

    @Override
    public void nuevoRegistro(RegistroSensor registro) {
        Float temp = ((RegistroTemperatura) registro).getTemperatura(); // TODO cosas raras abstract Java
        if(temp < this.heladeraAsignada.getTemperaturaMinima() || temp > this.heladeraAsignada.getTemperaturaMaxima()) {
            this.enviarAlerta(registro);
            this.heladeraAsignada.setEstado(EnumEstadoHeladera.TEMPERATURA_FUERA_DE_RANGO);
        } else {
            this.heladeraAsignada.setEstado(EnumEstadoHeladera.DISPONIBLE);
        }
    }
}