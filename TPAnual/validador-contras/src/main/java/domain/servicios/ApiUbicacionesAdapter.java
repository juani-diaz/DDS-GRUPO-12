package domain.servicios;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import domain.heladera.Ubicacion;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class ApiUbicacionesAdapter implements AdapterUbicacionesInterface {
    private String adaptadoURL="https://c4795cd9-26ed-4435-a2c2-12e13324a3aa.mock.pstmn.io/api/estudiante/1";

    private static String genericGet(String url, String key) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))//habria que ponerle + key para que lo identifique, talvez con lat y long pero como es mock ahora moriria
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public List<Ubicacion> obtenerUbicacionesRecomendadas(Ubicacion ubicacion) throws IOException, InterruptedException {
        Gson gson = new Gson();
        String jsonUbicacion = gson.toJson(ubicacion);

        String ubicacionesJson = genericGet(adaptadoURL, jsonUbicacion);

        //esto lo hago porque sino el mala persona me toma los \r y \n del mockasi que implosiona al leerlo
        ubicacionesJson = ubicacionesJson.replace("\\n", "")
                .replace("\\r", "")
                .replace("\\", "");

        JsonObject jsonObject = gson.fromJson(ubicacionesJson, JsonObject.class);
        JsonArray ubicationsArray = jsonObject.getAsJsonArray("ubications");

        List<Ubicacion> ubicaciones = new ArrayList<>();
        for (JsonElement element : ubicationsArray) {
            JsonObject obj = element.getAsJsonObject();
            double latitude = obj.get("latitude").getAsDouble();
            double longitude = obj.get("longitude").getAsDouble();

            Ubicacion nuevaUbicacion = new Ubicacion();
            nuevaUbicacion.setLatitud(String.valueOf(latitude));
            nuevaUbicacion.setLongitud(String.valueOf(longitude));

            ubicaciones.add(nuevaUbicacion);
        }

        return ubicaciones;
    }
}
