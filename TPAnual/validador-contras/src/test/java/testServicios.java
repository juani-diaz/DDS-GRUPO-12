import domain.contra.Usuario;
import domain.heladera.Ubicacion;
import domain.persona.Persona;
import domain.rol.Colaborador;
import domain.servicios.AdapterUbicacionesInterface;
import domain.servicios.ApiUbicacionesAdapter;
import domain.servicios.LectorCsv;
import domain.servicios.Mailer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class testServicios {

    @Test
    public void cargaPersonasConCSV() {
        Mailer mailer=new Mailer();

        InputStream csvStream = getClass().getClassLoader().getResourceAsStream("csvTest.csv");

        LectorCsv lector =new LectorCsv(csvStream,mailer);

        lector.cargarArchivo();

        List<String> list = new ArrayList<>();

        list.add("Juan");
        list.add("María");
        list.add("Carlos");
        list.add("Ana");
        list.add("José");

        List<Persona> personas= lector.getColaboradoresExistentes().stream().map(Colaborador::getPersona).toList();

        Assertions.assertEquals(list, personas.stream().map(Persona::getNombre).collect(Collectors.toList()));


    }

    @Test
    public void getUbicaciones() throws IOException, InterruptedException {
        AdapterUbicacionesInterface ubicacionesApi= new ApiUbicacionesAdapter();
        List<String> list = new ArrayList<>();

        list.add("10.0");
        list.add("120.0");
        list.add("100.0");

        Ubicacion ubicacion=new Ubicacion("CABA","Villa Real","1","10","calle","123");

        List<String> ubicacionesRecomendadas= ubicacionesApi.obtenerUbicacionesRecomendadas(ubicacion).stream().map(Ubicacion::getLatitud).toList();


        assertEquals(list,ubicacionesRecomendadas);
    }

}