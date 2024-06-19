package domain.vianda;
import domain.heladera.Heladera;
import domain.personas_empresas.PersonaVulnerable;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class ViandaRecogida extends Vianda {
  private PersonaVulnerable necesitado;
  private Heladera heraderaDeVianda;
  private Vianda viandaRecogida;
  private LocalDate fechaDeRecogida;
}
