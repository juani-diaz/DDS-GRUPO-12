package domain.colaboraciones;
import domain.rol.Colaborador;

import lombok.Getter;
import lombok.Setter;
import persistence.EntidadPersistente;

import javax.persistence.*;
import java.time.LocalDate;

@Getter @Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Colaboracion extends EntidadPersistente{

//    @Id @GeneratedValue
//    private Long id;
//    @ManyToOne
//    @JoinColumn(name = "colaborador_id")
    @ManyToOne
    Colaborador colaborador;
    @Column
    LocalDate fecha;

    public abstract void ejecutar();

    public abstract Float puntosObtenidos();
}
