package domain.heladera;

import java.time.LocalTime;
import java.time.LocalDate;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class RegistroTemperatura extends RegistroSensor {
    private Float temperatura;
}