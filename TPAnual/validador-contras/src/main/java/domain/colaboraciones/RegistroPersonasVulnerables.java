package domain.colaboraciones;

import domain.personas_empresas.Tarjeta;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class RegistroPersonasVulnerables extends  Colaboracion{
    private Tarjeta tarjetaEntregada;
}
