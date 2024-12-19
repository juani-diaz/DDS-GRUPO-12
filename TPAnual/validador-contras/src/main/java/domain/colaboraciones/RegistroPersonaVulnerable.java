package domain.colaboraciones;

import domain.persona.PersonaFisica;
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
    private Tarjeta tarjetaEntregada = null;
    @OneToOne
    private Vulnerable vulnerable;

    public RegistroPersonaVulnerable(Colaborador colaborador, LocalDate fecha,
                                     Vulnerable vulnerable){
        this.colaborador = colaborador;
        this.fecha = fecha;
        this.vulnerable = vulnerable;
    }

    public void ejecutar(){
        if (colaborador.getTarjetasParaEntregar() == null || colaborador.getTarjetasParaEntregar().isEmpty()) {
            throw new IllegalStateException("El colaborador no tiene tarjetas disponibles para entregar.");
        }

        if (!(colaborador.getPersona() instanceof PersonaFisica)) {
            throw new IllegalArgumentException("El que entrega la tarjeta debe ser una persona física");
        }
        //Tarjeta tarjetaEntregada = colaborador.getTarjetasParaEntregar().remove(0);

        // Asignar la tarjeta al vulnerable
        vulnerable.setearTarjeta(tarjetaEntregada);
        this.tarjetaEntregada= tarjetaEntregada;

        colaborador.setCantidadPuntos(colaborador.getCantidadPuntos() + puntosObtenidos());
    }

    public Float puntosObtenidos(){
        return multiplicador;
    }
}
