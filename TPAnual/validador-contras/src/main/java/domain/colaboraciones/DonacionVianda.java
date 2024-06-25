package domain.colaboraciones;

import domain.vianda.Vianda;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class DonacionVianda extends Colaboracion {
    public final static Float multiplicador = 1.5F;

    private Vianda vianda;

    public void ejecutar(){
        //TODO
    }

    public Float puntosObtenidos(){
        return multiplicador;
    }
}
