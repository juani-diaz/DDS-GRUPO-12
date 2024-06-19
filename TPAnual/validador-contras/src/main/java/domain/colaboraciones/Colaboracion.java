package domain.colaboraciones;
import domain.personas_empresas.Empresa;
import domain.personas_empresas.PersonaColaboradora;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class Colaboracion {
    private PersonaColaboradora colaborador;
    private Empresa empresaColaboradora;
    private LocalDate fecha;
}
