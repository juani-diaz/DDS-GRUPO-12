package domain.colaboraciones;

import domain.heladera.Heladera;
import domain.vianda.Vianda;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class DonacionVianda extends Colaboracion {
    public final static Float multiplicador = 1.5F;

    private Vianda vianda;
    private Heladera destino;

    public void ejecutar(){
        destino.ingresarViandas(Collections.singletonList(vianda));
    }

    public Float puntosObtenidos(){
        return multiplicador;
    }
}
