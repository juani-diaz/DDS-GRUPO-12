package domain.colaboraciones;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class DonacionDinero extends Colaboracion {
    private Float monto;
    private String frecuencia;
}
