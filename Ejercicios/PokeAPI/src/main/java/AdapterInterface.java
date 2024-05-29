import java.io.IOException;
import java.util.List;

public interface AdapterInterface {


    public default List<String> obtenerMovimientos(String pokemon) throws IOException, InterruptedException {

        return null;
    }

    public default String obtenerImagen(String pokemon) throws IOException, InterruptedException {

        return null;
    }

    public default List<String> obtenerPokemonPorMovimiento(String movimiento) throws IOException, InterruptedException {

        return null;
    }

}