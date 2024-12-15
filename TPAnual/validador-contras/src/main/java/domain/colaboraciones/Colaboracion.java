package domain.colaboraciones;

import domain.rol.Colaborador;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter @Setter
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Colaboracion{

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
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
