package domain.colaboraciones;

import domain.heladera.Heladera;
import domain.registro.SingletonSeguidorEstadistica;
import domain.rol.Tarjeta;
import domain.vianda.Vianda;
import lombok.*;

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

//    public DistribucionVianda(Colaborador colaborador, LocalDate fecha,
//                              Heladera origen, Heladera destino, List<Integer> viandasMovidas,
//                              EnumMotivosMovimientoVianda motivo){
//        this.colaborador = colaborador;
//        this.fecha = fecha;
//        this.origen = origen;
//        this.destino = destino;
//        this.viandasMovidas = viandasMovidas;
//        this.motivo = motivo;
//    }

    public int cantidadViandas(){
        return this.viandasMovidas.size();
    }

    public void ejecutar(){
        List<Integer> viandasMovidasMutable = new ArrayList<>(viandasMovidas);
        viandasMovidasMutable.sort(Collections.reverseOrder());
        List<Vianda> viandasAMover = new ArrayList<Vianda>();

        for(Integer indice : viandasMovidasMutable){
            viandasAMover.add(origen.sacarVianda(indice));
        }

        destino.ingresarViandas(viandasAMover);

        SingletonSeguidorEstadistica se = new SingletonSeguidorEstadistica(); // TODO singleton?
        se.getDistribucionViandas().add(this);

    }

    public void entregarTarjetas(List<Tarjeta> list_tarjetas){
        this.colaborador.recibirTarjetas(list_tarjetas);
    }

    public Float puntosObtenidos(){
        return multiplicador;
    }
}
