package domain.rol;

import domain.persona.Persona;
import lombok.Getter;
import lombok.Setter;
import persistence.EntidadPersistente;

import javax.persistence.*;

@Getter
@Setter
@Entity
//@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Rol extends EntidadPersistente {

    //@OneToOne(cascade = CascadeType.ALL)
    //@JoinColumn(name = "persona_documento", referencedColumnName = "documento")
    @ManyToOne
    Persona persona;
}
