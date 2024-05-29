import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class PokemonService {

    AdapterInterface pokeApi;

    public PokemonService(AdapterInterface pokeApi){
        this.pokeApi = pokeApi;
    }


    public List<String> obtenerPokemonsPorMovimiento(String movimiento) throws IOException, InterruptedException {
       return pokeApi.obtenerPokemonPorMovimiento(movimiento);

    }


    public String ObtenerImagenPokemon(String pokemon) throws IOException, InterruptedException {
        return pokeApi.obtenerImagen(pokemon);

    }

    public List<String> obtenerMovimientosPokemon(String pokemon) throws IOException, InterruptedException {
        return pokeApi.obtenerMovimientos(pokemon);

    }

}