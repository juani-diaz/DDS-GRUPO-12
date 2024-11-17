package domain.persona;

import lombok.*;

import javax.persistence.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
//@Entity
public abstract class Persona {
    //@Id
    //@OneToOne
    //@JoinColumn(name = "documento_tipo", referencedColumnName = "tipo")
    //@JoinColumn(name = "documento_numero", referencedColumnName = "numero")

    //@EmbeddedId
    private Documento documento;

    private String nombre;
    //@Embedded
    private MedioDeContacto medioDeContacto;
    private String direccion;



}
