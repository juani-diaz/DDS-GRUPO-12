package domain.colaboraciones;

import domain.rol.Tarjeta;

import domain.rol.Vulnerable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class RegistroPersonaVulnerable extends  Colaboracion{
    public final static Float multiplicador = 2F;

    private Tarjeta tarjetaEntregada;
    private Vulnerable vulnerable;

    public void ejecutar(){
        vulnerable.setTarjeta(tarjetaEntregada);
    }

    public Float puntosObtenidos(){
        return multiplicador;
    }
}
