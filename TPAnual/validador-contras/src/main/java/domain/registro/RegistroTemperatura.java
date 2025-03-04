package domain.registro;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
public class RegistroTemperatura extends RegistroSensor {
    @Column
    private Float temperatura;
}