package domain.heladera;

import java.time.LocalTime;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class RegistroTemperatura {
    private LocalDate fecha;
    private LocalTime hora;
    private Float temperatura;
}