package domain.colaboraciones;

import domain.heladera.Heladera;
import domain.vianda.Vianda;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class DistribucionVianda extends Colaboracion {//TODO: Chequear q extend este este bien
    private Heladera origen;
    private Heladera destino;
    private List<Vianda> viandasMovidas;
    private EnumMotivosMovimientoVianda motivo;
    private Float multiplicador;

    private void cantidadViandas(){} //TODO: Implementar la fucnon cantidadViandas
}
