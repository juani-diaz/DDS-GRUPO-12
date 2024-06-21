package domain.heladera;
//import domain.servicios.AdapterUbicacionesInterface;

import lombok.*;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class RecomendadorUbicacion {

    private List<Ubicacion> ubicacionesHeladeras;
    private AdapterUbicacionInterface proveedorUbicaciones;
    //private AdapterUbicacionesInterface proveedorUbicacion; TODO: Ni idea de como apregar un adapter

}


