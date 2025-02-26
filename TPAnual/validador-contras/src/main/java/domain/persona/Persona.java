package domain.persona;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import persistence.EntidadPersistente;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "persona")
public abstract class Persona extends EntidadPersistente {

    @Column
    private String nombre;

    @Column
    private String direccion;

    @OneToOne
    private Documento documento;

    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<MedioDeContacto> mediosDeContacto=new ArrayList<>();

    public void agregarMedioDeContacto(MedioDeContacto medioDeContacto) {
        medioDeContacto.setPersona(this); // Vincula al medio con esta persona
        this.mediosDeContacto.add(medioDeContacto);
    }

    public void eliminarMedioDeContacto(MedioDeContacto medioDeContacto) {
        medioDeContacto.setPersona(null); // Desvincula el medio de esta persona
        this.mediosDeContacto.remove(medioDeContacto);
    }

}
