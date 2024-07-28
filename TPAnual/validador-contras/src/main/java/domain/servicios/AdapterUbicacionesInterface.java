
package domain.servicios;

import domain.heladera.Ubicacion;

import java.io.IOException;
import java.util.List;

public interface AdapterUbicacionesInterface {

    String adaptadoURL = "";


    public default List<Ubicacion> obtenerUbicacionesRecomendadas(Ubicacion ubicacion) throws IOException, InterruptedException{

        return null;
    }


}
