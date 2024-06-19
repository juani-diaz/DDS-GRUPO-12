package domain.heladera;
//import domain.servicios.AdapterUbicacionesInterface;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class RecomendadorUbicacion extends Heladera {

    private List<Heladera> listaHeladera;
    //private AdapterUbicacionesInterface proveedorUbicacion; TODO: Ni idea de como apregar un adapter


    private void darDeAltaHeladera(){ new Heladera(); } //TODO: darDeAltaHeladera

}


