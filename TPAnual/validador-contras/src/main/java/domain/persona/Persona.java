package domain.persona;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public abstract class Persona {
    private String nombre;
    private MedioDeContacto medioDeContacto;
    private String direccion;
    private Documento documento; //TODO: Evaluar si corresponde que documento este en persona. Yo creo que tanto PF como PJ tienen documento para identificarlo
}
