package domain.heladera;


import domain.registro.RegistroSensor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import persistence.EntidadPersistente;

import javax.persistence.*;

@Getter @Setter @NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Sensor{

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    Heladera heladeraAsignada;

    public Sensor(Heladera hel){
        this.heladeraAsignada = hel;
    }

    public void enviarAlerta(RegistroSensor registro){
        //Enviar
    }

    public abstract void nuevoRegistro(RegistroSensor registro);
}