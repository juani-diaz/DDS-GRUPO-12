package domain.heladera;


import domain.registro.RegistroSensor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public abstract class Sensor {
    Heladera heladeraAsignada;

    public Sensor(Heladera hel){
        this.heladeraAsignada = hel;
    }

    public void enviarAlerta(RegistroSensor registro){
        //Enviar
    }

    public abstract void nuevoRegistro(RegistroSensor registro);
}