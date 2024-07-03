package domain.colaboraciones;
import domain.rol.Colaborador;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public abstract class Colaboracion {
    Colaborador colaborador;
    LocalDate fecha;

    public abstract void ejecutar();

    public abstract Float puntosObtenidos();
}
