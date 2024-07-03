package domain.colaboraciones;

import domain.rol.Tarjeta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class RegistroPersonasVulnerables extends  Colaboracion{
    public final static Float multiplicador = 2F;

    private Tarjeta tarjetaEntregada;

    public void ejecutar(){
        //TODO
    }

    public Float puntosObtenidos(){
        return multiplicador;
    }
}
