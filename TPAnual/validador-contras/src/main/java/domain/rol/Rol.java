package domain.rol;

import domain.persona.Persona;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Rol{

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int id;

    @ManyToOne
    Persona persona;

    public String getDireccion() {
        return persona.getDireccion();
    }
}
