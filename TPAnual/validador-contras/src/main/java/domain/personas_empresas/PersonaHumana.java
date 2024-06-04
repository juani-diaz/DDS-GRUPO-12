package domain.personas_empresas;

import java.time.LocalDate;
import domain.personas_empresas.PersonaVulnerable;
import java.util.List;
public class PersonaHumana extends PersonaColaboradora {
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento; //como se pondria aca un valor nulo?
    private String descripcionDeColaboracion;
    private List<Tarjeta> tarjetasParaEntregar;

    public boolean tarjetaParaVulnerable(PersonaVulnerable destinatario){
        if(!tarjetasParaEntregar.isEmpty()) {
            destinatario.setTarjeta(tarjetasParaEntregar.remove(0));
            return true;
        }
        return false;
    }
}