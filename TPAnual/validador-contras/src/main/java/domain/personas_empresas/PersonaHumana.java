package domain.personas_empresas;

import java.util.list;
import java.time.LocalDate;
import domain.personas_empresas.PersonaVulnerable;

public class PersonaHumana {
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento; //como se pondria aca un valor nulo?
    private String descripcionDeColaboracion;
    private List<Tarjeta> tarjetasParaEntregar;

    public static tarjetaParaVulnerable(Tarjeta,PersonaVulnerable){
        // nose muy bien como implementarlo yo pensaba algo asi como un setter de la tarjeta
        // a persona vulnerable algo asi, fijense si esta bien
        PersonaVulnerable.setTarjeta(Tarjeta);
    }
    public static tarjetasParaEntregar(List<Tarjeta> tarjetasNuevas){
        List<String> union = new ArrayList<Tarjeta>();
        union.addAll(tarjetasParaEntregar);
        union.addAll(tarjetasNuevas);
        setTarjetasParaEntregar(union);
    }

    public void setTarjetasParaEntregar(List<Tarjeta> tarjetasParaEntregar) {
        this.tarjetasParaEntregar = tarjetasParaEntregar;
    }
}