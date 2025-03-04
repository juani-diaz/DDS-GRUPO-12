package domain.colaboraciones;

import domain.heladera.Heladera;
import domain.persona.PersonaFisica;
import domain.registro.SingletonSeguidorEstadistica;
import domain.rol.Colaborador;
import domain.rol.Tarjeta;
import domain.vianda.Vianda;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import persistence.Repos.RepoVianda;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Entity
public class DistribucionVianda extends Colaboracion {

    //@Column
    public final static Float multiplicador = 1F;

    @ManyToOne
    private Heladera origen;

    @ManyToOne
    private Heladera destino;


    private Integer cantidadViandasMovidas;

    @JoinTable(
            name = "distribucion_vianda_viandas",
            joinColumns = @JoinColumn(name = "distribucion_vianda_id",referencedColumnName = "id", nullable = false, unique = false),
            inverseJoinColumns = @JoinColumn(name = "vianda_movida_id",referencedColumnName = "id", nullable = false, unique = false))
    @ManyToMany
    private List<Vianda> viandasMovidas;

    @Enumerated(value = EnumType.STRING)
    private EnumMotivosMovimientoVianda motivo;

    public DistribucionVianda(Colaborador colaborador, LocalDate fecha,
                              Heladera origen, Heladera destino, int cantidadViandasMovidas,
                              EnumMotivosMovimientoVianda motivo){
        this.colaborador = colaborador;
        this.fecha = fecha;
        this.origen = origen;
        this.destino = destino;
        this.cantidadViandasMovidas = cantidadViandasMovidas;
        this.motivo = motivo;
    }

    public int cantidadViandas(){
        return this.cantidadViandasMovidas;
    }



    public void ejecutar() {
        Colaborador colaborador = getColaborador();
        if (!(colaborador.getPersona() instanceof PersonaFisica)) {
            throw new IllegalArgumentException("El colaborador debe ser una persona humana");
        }

        List<Vianda> shuffledList = new ArrayList<>(this.origen.getViandasEnHeladera());
        Collections.shuffle(shuffledList);
        this.viandasMovidas = shuffledList.subList(0, this.cantidadViandasMovidas);
        this.origen.sacarViandas(this.viandasMovidas);
        for(Vianda vianda : viandasMovidas){
            vianda.setHeladera(destino);
            RepoVianda.getInstance().updateVianda(vianda);
        }
        this.destino.ingresarViandas(this.viandasMovidas);

        SingletonSeguidorEstadistica se = SingletonSeguidorEstadistica.getInstance();
        se.addDistribucionVianda(this);
    }
    public void entregarTarjetas(List<Tarjeta> list_tarjetas){ //osea recibe tarjetas
        this.colaborador.recibirTarjetas(list_tarjetas);
    }

    public Float puntosObtenidos(){ return multiplicador * viandasMovidas.size(); }
}
