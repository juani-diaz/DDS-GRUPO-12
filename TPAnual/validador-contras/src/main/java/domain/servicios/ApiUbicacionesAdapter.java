package domain.servicios;

import com.google.gson.Gson;
import domain.heladera.Ubicacion;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class ApiUbicacionesAdapter implements AdapterUbicacionesInterface{

    private String adaptadoURL;

    private static String genericGet(String url, String key) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + key))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    private List<Ubicacion> ApiUbicacionAdapter(Ubicacion ubicacion) throws IOException, InterruptedException {
        // Convierte la ubicación a formato JSON
        Gson gson = new Gson();
        String jsonUbicacion = gson.toJson(ubicacion);


        String ubicacionesJson = genericGet(adaptadoURL, jsonUbicacion);

        List<Ubicacion> ubicaciones= List.of(gson.fromJson(ubicacionesJson, Ubicacion[].class));

        return ubicaciones;
    }
}
