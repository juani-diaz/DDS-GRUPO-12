package domain.persona;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PersonaFisica extends Persona {

    @Column
    private String apellido;

    @Column
    private String sexo;
    @Column
    private String genero;
    @Column
    private LocalDate fechaNacimiento;


    //TODO: No se que hace esto :)
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
        //super(documento, nombre, medioContacto, direccion);
        this.apellido = apellido;
        this.setDocumento(documento);
        this.fechaNacimiento = fechaNacimiento;
    }



}
