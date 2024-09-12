package domain.rol;

import domain.heladera.Heladera;
import domain.incidente.EnumEstadoDeIncidente;
import domain.incidente.Incidente;
import domain.incidente.VisitasTecnicas;
import domain.persona.Persona;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Tecnico extends Rol {
  private List<String> areaCobertura;

  private List<Incidente> incidentesARevisar;

  private List<VisitasTecnicas> visitasRealizadas;

  private void realizarVisitaTecnica(Incidente incidente, LocalDate fecha, String trabajoRealizado, String foto, Boolean solucionado){

    VisitasTecnicas visitaTecnica = new VisitasTecnicas(this,incidente,fecha,trabajoRealizado,foto);

    visitasRealizadas.add(visitaTecnica);
    incidentesARevisar.remove(incidente);

    if (solucionado){
      incidente.flujoDeSolucion();
    }

  }

  private void serNotificadoDeIncidente(Incidente incidente){
    incidentesARevisar.add(incidente);
  }
}
