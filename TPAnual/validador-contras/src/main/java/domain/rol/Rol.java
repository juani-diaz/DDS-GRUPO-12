package domain.rol;

import domain.persona.Persona;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
//@Entity
//@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Rol {

    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //@OneToOne(cascade = CascadeType.ALL)
    //@JoinColumn(name = "persona_documento", referencedColumnName = "documento")
    Persona persona;
}
