package domain.colaboraciones;

import domain.heladera.Heladera;
import domain.rol.Colaborador;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.time.LocalDate;
import java.time.Period;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
public class ResponsableHeladera extends Colaboracion{
    @OneToOne
    private Heladera heladera;
    public final static Float multiplicador = 5F;

    public ResponsableHeladera(Colaborador colaborador, LocalDate fecha, Heladera heladera){
        this.colaborador = colaborador;
        this.fecha = fecha;
        this.heladera = heladera;
    }

    public void ejecutar(){
        this.heladera.setResponsable(this.colaborador);
    }

    public int calcularMesesDeFuncionamiento() {
        if (heladera.getFechaFuncionamiento() == null) {
            return 0; // Manejo del caso donde no haya fecha de funcionamiento
        }
        LocalDate fechaActual = LocalDate.now();
        Period periodoFuncionamiento = Period.between(heladera.getFechaFuncionamiento(), fechaActual);
        return periodoFuncionamiento.getYears() * 12 + periodoFuncionamiento.getMonths(); // Calcula los meses totales
    }

    public Float puntosObtenidos(){
        return calcularMesesDeFuncionamiento() *  multiplicador;
    }
}
