package domain.heladera;
import lombok.*;

import java.util.Date;


@Getter @Setter @NoArgsConstructor @AllArgsConstructor

public class SensorDeMovimiento extends Sensor{
    private Date fecha;


}