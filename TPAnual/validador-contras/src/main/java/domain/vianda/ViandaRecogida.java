package domain.vianda;
import domain.heladera.Heladera;
import domain.personas_empresas.PersonaVulnerable;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ViandaRecogida  {
  private PersonaVulnerable necesitado;
  private Heladera heraderaDeVianda;
  private Vianda viandaRecogida;
  private LocalDate fechaDeRecogida;
}
