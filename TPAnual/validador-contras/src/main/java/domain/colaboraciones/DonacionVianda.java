package domain.colaboraciones;

import domain.heladera.Heladera;
import domain.registro.SingletonSeguidorEstadistica;
import domain.rol.Colaborador;
import domain.rol.Tarjeta;
import domain.vianda.Vianda;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
public class DonacionVianda extends Colaboracion {
    public final static Float multiplicador = 1.5F;
    @ManyToOne
    private Vianda vianda;
    @ManyToOne
    private Heladera destino;

    public DonacionVianda(Colaborador colaborador, LocalDate fecha,
                          Vianda vianda, Heladera destino){
        this.colaborador = colaborador;
        this.fecha = fecha;
        this.vianda = vianda;
        this.destino = destino;
    }

    public void ejecutar(){
        destino.ingresarViandas(Collections.singletonList(vianda));

        SingletonSeguidorEstadistica se = SingletonSeguidorEstadistica.getInstance();
        se.getDonacionViandas().add(this);
    }

    public Float puntosObtenidos(){
        return multiplicador;
    }

    public void entregarTarjetas(List<Tarjeta> list_tarjetas){
        this.colaborador.recibirTarjetas(list_tarjetas);
    }
}


