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

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
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

    public void ejecutar() {
        Colaborador colaborador = getColaborador();
        if (!(colaborador.getPersona() instanceof PersonaFisica)) {
            throw new IllegalArgumentException("El colaborador debe ser una persona humana");
        } else {
            destino.ingresarViandas(Collections.singletonList(vianda));

            SingletonSeguidorEstadistica se = SingletonSeguidorEstadistica.getInstance();
            se.addDonacionVianda(this);
        }
        colaborador.setCantidadPuntos(colaborador.getCantidadPuntos() + puntosObtenidos());
    }
    public Float puntosObtenidos(){

        return multiplicador;
    }

    public void entregarTarjetas(List<Tarjeta> list_tarjetas){
        this.colaborador.recibirTarjetas(list_tarjetas);
    }
}


