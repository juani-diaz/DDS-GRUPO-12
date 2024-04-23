package ReservaDeVuelos;

//@Getter
//@Setter

import ReservaDeVuelos.EstadoVuelo;
import ReservaDeVuelos.Avion;
import java.sql.Date;
import java.sql.Time;

public class vuelo {

    String origen;
    String destino;
    private String aerolinea;
    private String codigo;
    private Avion avion;
    private Date fecha;
    private Time horario_salida;
    private Time horario_llegada;
    private EstadoVuelo estado;
}

