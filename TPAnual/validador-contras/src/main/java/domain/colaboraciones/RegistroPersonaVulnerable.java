package domain.colaboraciones;

import domain.rol.Colaborador;
import domain.rol.Tarjeta;

import domain.rol.Vulnerable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
public class RegistroPersonaVulnerable extends  Colaboracion{

    public final static Float multiplicador = 2F;

    @OneToOne
    private Tarjeta tarjetaEntregada;
    @OneToOne
    private Vulnerable vulnerable;

    public RegistroPersonaVulnerable(Colaborador colaborador, LocalDate fecha,
                                     Tarjeta tarjetaEntregada, Vulnerable vulnerable){
        this.colaborador = colaborador;
        this.fecha = fecha;
        this.tarjetaEntregada = tarjetaEntregada;
        this.vulnerable = vulnerable;
    }

    public void ejecutar(){
        vulnerable.setTarjeta(tarjetaEntregada);
    }

    public Float puntosObtenidos(){
        return multiplicador;
    }
}
