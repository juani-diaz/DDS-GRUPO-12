package domain.rol;

import domain.incidente.VisitasTecnicas;
import domain.persona.Persona;
import lombok.*;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Tecnico extends Rol {
  private List<String> areaCobertura;

  private List<VisitasTecnicas> visitasRealizadas;

  private void realizarVisitaTecnica(){} //TODO:hacer realizarVisitaTecnica
}
