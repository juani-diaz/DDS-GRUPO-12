package domain.persona;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonaFisica extends Persona {
    private String apellido;
    private Documento documento;
    private LocalDate fechaNacimiento;

    public PersonaFisica(String nombre, MedioContacto medioContacto, String direccion, String apellido, Documento documento, LocalDate fechaNacimiento){
        super(nombre, medioContacto, direccion);
        this.apellido = apellido;
        this.documento = documento;
        this.fechaNacimiento = fechaNacimiento;
    }
}
