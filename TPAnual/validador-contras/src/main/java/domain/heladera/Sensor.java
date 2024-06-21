package domain.heladera;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter @NoArgsConstructor
public abstract class Sensor {
    List<RegistroSensor> registros;
    Heladera heladeraAsignada;

    public Sensor(Heladera hel){
        this.registros = new ArrayList<RegistroSensor>();
        this.heladeraAsignada = hel;
    }

    public void enviarAlerta(){
        this.registros.getLast(); //Enviar
    }

    public abstract void nuevoRegistro(RegistroSensor registro);
}