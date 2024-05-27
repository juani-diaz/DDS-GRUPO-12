package pokeApi;

public class PokemonService(){

    AdapterInterface pokeApi;

    public void setPokeApi(AdapterInterface pokeApi) {
        this.pokeApi = pokeApi;
    }

    public String obtenerPokemonsPorMovimiento(String movimiento){
       return pokeApi.obtenerPokemonsPorMovimiento(movimiento);

    }


    public String ObtenerImagenPokemon(String pokemon){
        return pokeApi.obtenerImagen(pokemon);

    }

    public String obtenerMovimientosPokemon(String pokemon){
        return pokeApi.obtenerMovimientos(pokemon);

    }

}