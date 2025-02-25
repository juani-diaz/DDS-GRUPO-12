package domain.colaboraciones;

import domain.rol.Colaborador;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import persistence.BDUtils;
import persistence.Repos.RepoColaborador;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
public class DonacionDinero extends Colaboracion {

    //@Column No se persiste por que es public, si fuera privado se persistiria
    public final static Float multiplicador = 0.5F;
    @Column
    private Float monto;
    @Column
    private String frecuencia;
    @ManyToOne
    private MedioDePago medioDePago;

    public DonacionDinero(Colaborador colaborador, LocalDate fecha,
                          Float monto, String frecuencia, MedioDePago medioDePago){
        this.colaborador = colaborador;
        this.fecha = fecha;
        this.monto = monto;
        this.medioDePago = medioDePago;
        this.frecuencia = frecuencia;
    }

    public void ejecutar(){
        // Transf monetaria

        EntityManager em = BDUtils.getEntityManager();

        BDUtils.comenzarTransaccion(em);
        em.persist(medioDePago);
        BDUtils.commit(em);
    }

    public Float puntosObtenidos(){
        if(Objects.equals(frecuencia, "unica") || frecuencia == null) {
            return multiplicador * monto;
        } else {
            long vecesUsado = switch (frecuencia) {
                case "semanal" -> ChronoUnit.WEEKS.between(fecha, LocalDate.now()) + 1;
                case "mensual" -> ChronoUnit.MONTHS.between(fecha, LocalDate.now()) + 1;
                case "anual" -> ChronoUnit.YEARS.between(fecha, LocalDate.now()) + 1;
                default -> 1;
            };
            return multiplicador * monto * vecesUsado;
        }
    }
}
