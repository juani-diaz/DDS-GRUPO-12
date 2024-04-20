package ReservaDeVuelos;

import java.util.List;

public class Viaje {
    private List<vuelo> listVuelos;
    private Number tarifa;
    


    public void origen(){
        String aeropuertoDeOrigen = listVuelos.get(0).origen;
        System.out.println(aeropuertoDeOrigen);
    }
    public void destino(){
        String aeropuertoDeDestino = listVuelos.get(-1).destino;
        System.out.println(aeropuertoDeDestino);
    }
}
