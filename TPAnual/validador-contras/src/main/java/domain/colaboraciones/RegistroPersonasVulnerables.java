package domain.colaboraciones;

import domain.personas_empresas.Tarjeta;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RegistroPersonasVulnerables extends  Colaboracion{
    private List<Tarjeta> tarjetas;


}
