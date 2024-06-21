package domain.personas_empresas;

import lombok.*;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Persona {
    private String nombre;
    private String apellido;
    private Documento documento;
    private LocalDate fechaNacimiento;
    private String direccion;
    private MedioContacto medioDeContacto;
}
