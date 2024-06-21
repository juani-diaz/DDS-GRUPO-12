package domain.personas_empresas;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ColaboradorHumano extends PersonaColaboradora {
    private String descripcionDeColaboracion;
    private List<Tarjeta> tarjetasParaEntregar;

    public boolean entregarTarjeta(PersonaVulnerable destinatario){
        if(!tarjetasParaEntregar.isEmpty()) {
            destinatario.setTarjeta(tarjetasParaEntregar.remove(0));
            return true;
        }
        return false;
    }

    public void recibirTarjetas(List<Tarjeta> tarjetas) { tarjetasParaEntregar.addAll(tarjetas); }
}