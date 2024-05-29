import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AdapterPokemon implements AdapterInterface{

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static String genericGet(String url, String key) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + key))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }


    public List<String> obtenerPokemonPorMovimiento(String movimiento) throws IOException, InterruptedException {
        String json = genericGet("https://pokeapi.co/api/v2/move/", movimiento);
        JsonNode rootNode = objectMapper.readTree(json);
        JsonNode pokemonNode = rootNode.path("learned_by_pokemon");

        List<String> pokemones = new ArrayList<>();
        for (JsonNode pokemon : pokemonNode) {
            String nombrePokemon = pokemon.path("name").asText();
            pokemones.add(nombrePokemon);
        }

        return pokemones;
    }

    public String obtenerImagen(String pokemon) throws IOException, InterruptedException {
        String json =genericGet("https://pokeapi.co/api/v2/pokemon/",pokemon);
        JsonNode rootNode = objectMapper.readTree(json);
        return rootNode.path("sprites").path("front_default").asText();
    }

    public List<String> obtenerMovimientos(String pokemon) throws IOException, InterruptedException {
        String json = genericGet("https://pokeapi.co/api/v2/pokemon/", pokemon);
        JsonNode rootNode = objectMapper.readTree(json);
        JsonNode movesNode = rootNode.path("moves");

        List<String> movimientos = new ArrayList<>();
        for (JsonNode move : movesNode) {
            String movimiento = move.path("move").path("name").asText();
            movimientos.add(movimiento);
        }

        return movimientos;
    }
}