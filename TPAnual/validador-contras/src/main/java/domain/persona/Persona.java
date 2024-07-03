package domain.persona;

import lombok.*;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public abstract class Persona {
    private String nombre;
    private MedioContacto medioDeContacto;
    private String direccion;
    private Documento documento; //TODO: Evaluar si corresponde que documento este en persona. Yo creo que tanto PF como PJ tienen documento para identificarlo
}
