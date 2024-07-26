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
    private String sexo;
    private String genero;
    private LocalDate fechaNacimiento;


    //TODO: No se que hacd esto :)
    public PersonaFisica(
        String nombre,
        MedioDeContacto medioContacto,
        String direccion,
        Documento documento,
        String apellido,
        String sexo,
        String genero,
        LocalDate fechaNacimiento
        ){
        super(nombre, medioContacto, direccion, documento);
        this.apellido = apellido;
        this.setDocumento(documento);
        this.fechaNacimiento = fechaNacimiento;
    }



}
