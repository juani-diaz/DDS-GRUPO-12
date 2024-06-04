package domain.colaboraciones;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class DonacionDinero extends Colaboracion{ //TODO: Chequear q extend este este bien
    private LocalDate fecha;
    private Float monto;
    private String frecuencia;
    private Float multiplicador;
}
