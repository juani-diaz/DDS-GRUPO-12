package domain.colaboraciones;
import domain.personas_empresas.PersonaColaboradora;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public abstract class Colaboracion {
    private PersonaColaboradora colaborador;
    private LocalDate fecha;

    public abstract void ejecutar();
}
