package domain.rol;

import domain.persona.Persona;
import lombok.Getter;
import lombok.Setter;
import persistence.EntidadPersistente;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Rol{

    @Id
    private int id;

    @ManyToOne
    Persona persona;
}
