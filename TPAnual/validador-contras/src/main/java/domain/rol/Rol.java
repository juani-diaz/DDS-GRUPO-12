package domain.rol;

import domain.persona.Persona;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Rol {
    Persona persona;
}
