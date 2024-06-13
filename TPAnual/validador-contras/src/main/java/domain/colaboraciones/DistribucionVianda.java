package domain.colaboraciones;

import domain.heladera.Heladera;
import domain.vianda.Vianda;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class DistribucionVianda extends Colaboracion {
    private Heladera origen;
    private Heladera destino;
    private List<Vianda> viandasMovidas;
    private EnumMotivosMovimientoVianda motivo;
    private Float multiplicador;

    public int cantidadViandas(){
        return this.viandasMovidas.size();
    }
}
