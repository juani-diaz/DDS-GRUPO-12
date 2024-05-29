package domain.vianda;
import domain.heladera.Heladera;
import domain.personas_empresas.PersonaVulnerable;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ViandaRecogida {
  private PersonaVulnerable necesitado;
  private Heladera heraderaDeVianda;
  private Vianda viandaRecogida;
  private LocalDate fechaDeRecogida;
}
