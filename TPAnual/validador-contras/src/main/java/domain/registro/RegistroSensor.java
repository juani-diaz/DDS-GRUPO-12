package domain.registro;

import domain.heladera.Sensor;
import lombok.Getter;
import lombok.Setter;
import persistence.EntidadPersistente;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.Date;

@Getter
@Setter
@Entity
public class RegistroSensor extends EntidadPersistente {

    @Column
    private Date fecha;

    @OneToOne
    private Sensor sensor;
}
