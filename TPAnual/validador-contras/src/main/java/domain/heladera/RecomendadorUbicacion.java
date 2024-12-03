package domain.heladera;
//import domain.servicios.AdapterUbicacionesInterface;

import lombok.*;
import persistence.EntidadPersistente;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
public class RecomendadorUbicacion extends EntidadPersistente {

    @OneToMany
    private List<Ubicacion> ubicacionesHeladeras;
    //private AdapterUbicacionesInterface proveedorUbicacion; TODO

}


