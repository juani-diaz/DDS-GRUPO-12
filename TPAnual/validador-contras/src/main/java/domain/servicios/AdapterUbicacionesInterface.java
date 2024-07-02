
package domain.servicios;

import domain.heladera.Ubicacion;

import java.util.List;

public interface AdapterUbicacionesInterface {

    String adaptadoURL = "";


    public default List<Ubicacion> obtenerUbicacionesRecomendadas(Ubicacion ubicacion) {

        return null;
    }


}
