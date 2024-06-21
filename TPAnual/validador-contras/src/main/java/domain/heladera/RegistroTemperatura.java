package domain.heladera;

import java.time.LocalTime;
import java.time.LocalDate;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class RegistroTemperatura  {
    private Date fecha;
    private Float temperatura;
}