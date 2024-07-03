package domain.persona;

import lombok.*;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public abstract class Persona {
    private String nombre;
    private MedioContacto medioDeContacto;
    private String direccion;
}
