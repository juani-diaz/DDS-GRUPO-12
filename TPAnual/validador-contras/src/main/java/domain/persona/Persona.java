package domain.persona;

import domain.heladera.Ubicacion;
import lombok.*;
import persistence.EntidadPersistente;

import javax.persistence.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
public abstract class Persona extends EntidadPersistente {

    //@JoinColumn(name = "documento_tipo", referencedColumnName = "tipo")
    //@JoinColumn(name = "documento_numero", referencedColumnName = "numero")

    @Column
    private String nombre;

    @Column
    private String direccion;

    @OneToOne
    private Documento documento;

    @OneToOne
    private MedioDeContacto medioDeContacto;




}
