package domain.colaboraciones;

import domain.heladera.Heladera;
import domain.vianda.Vianda;
import lombok.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class DistribucionVianda extends Colaboracion {
    public final static Float multiplicador = 1F;

    private Heladera origen;
    private Heladera destino;
    private List<Integer> viandasMovidas;
    private EnumMotivosMovimientoVianda motivo;

    public int cantidadViandas(){
        return this.viandasMovidas.size();
    }

    public void ejecutar(){
        Collections.sort(viandasMovidas, Collections.reverseOrder());
        List<Vianda> viandasAMover = new ArrayList<Vianda>();

        for(Integer indice : viandasMovidas){
            viandasAMover.add(origen.sacarVianda(indice));
        }

        destino.ingresarViandas(viandasAMover);
    }

    public Float puntosObtenidos(){
        return multiplicador;
    }
}
