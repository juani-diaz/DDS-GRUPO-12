import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class PokeApiTest
{

    @Test
    public void getPokemonByMove() throws IOException, InterruptedException {
        AdapterPokemon pokeApi= new AdapterPokemon();
        PokemonService pokeService= new PokemonService(pokeApi);
        String expectedString = "clefairy";

        //me fijo solo el primer pokemon porque son una banda. Es mas practico

        assertEquals(expectedString,pokeService.obtenerPokemonsPorMovimiento("pound").get(0));
    }

    @Test
    public void getPhotoPokemon() throws IOException, InterruptedException {
        AdapterPokemon pokeApi= new AdapterPokemon();
        PokemonService pokeService= new PokemonService(pokeApi);
        String expectedPhotoUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/6.png";

        assertEquals(expectedPhotoUrl,pokeService.ObtenerImagenPokemon("charizard"));
    }

    @Test
    public void getMovesPokemon() throws IOException, InterruptedException {
        AdapterPokemon pokeApi= new AdapterPokemon();
        PokemonService pokeService= new PokemonService(pokeApi);
        String expectedString = "mega-punch";

        //me fijo solo el primer movimiento porque son una banda. Es mas practico
        assertEquals(expectedString,pokeService.obtenerMovimientosPokemon("charizard").get(0));
    }
}
