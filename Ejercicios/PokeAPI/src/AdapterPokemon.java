package pokeApi;

import AdapterInterface.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AdapterPokemon implements AdapterInterface{

    private static String genericGet(String url, String key) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + key))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public List<String> obtenerPokemonsPorMovimiento(String movimiento){
    Sring json = genericGet('https://pokeapi.co/api/v2/move/',movimiento);
    JsonNode rootNode = objectMapper.readTree(json);
    return rootNode.path("learned_by_pokemon").path("front_default").asText();
    }

    public List<String> obtenerImagen(String pokemon){
        Sring json =genericGet('https://pokeapi.co/api/v2/pokemon/',pokemon);
        JsonNode rootNode = objectMapper.readTree(json);
        return rootNode.path("sprites").path("front_default").asText();
    }

    public List<String> obtenerMovimientos(String pokemon){
        Sring json = genericGet('https://pokeapi.co/api/v2/pokemon/',pokemon);
        JsonNode rootNode = objectMapper.readTree(json);
        return rootNode.path("moves").path("front_default").asText();
    }
}