package domain.rol;

import domain.persona.Persona;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@AllArgsConstructor
@Entity
public class Administrador extends Rol{
    public Administrador(Persona p){
        this.persona = p;
    }
}
