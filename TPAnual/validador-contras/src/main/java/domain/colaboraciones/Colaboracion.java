package domain.colaboraciones;
import domain.rol.Colaborador;

import lombok.Getter;
import lombok.Setter;
import persistence.EntidadPersistente;

import javax.persistence.*;
import java.time.LocalDate;

@Getter @Setter
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Colaboracion{

    @Id
    private int id;

    @ManyToOne
    Colaborador colaborador;

    @Column(columnDefinition = "DATE")
    LocalDate fecha;

    public abstract void ejecutar();

    public abstract Float puntosObtenidos();

    public Colaborador getColaborador() {
        return colaborador;
    }

}
