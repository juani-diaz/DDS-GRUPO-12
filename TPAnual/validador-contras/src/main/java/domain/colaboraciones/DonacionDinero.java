package domain.colaboraciones;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class DonacionDinero extends Colaboracion {
    public final static Float multiplicador = 0.5F;

    private Float monto;
    private String frecuencia;

    public void ejecutar(){
        //TODO

    }

    public Float puntosObtenidos(){
        return multiplicador * monto;
    }
}
