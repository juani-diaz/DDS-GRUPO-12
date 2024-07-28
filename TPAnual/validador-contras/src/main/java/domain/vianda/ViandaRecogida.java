package domain.vianda;
import domain.heladera.Heladera;
import domain.rol.Vulnerable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ViandaRecogida  {
  private Vulnerable necesitado;
  private Heladera heraderaDeVianda;
  private Vianda viandaRecogida;
  private Date fechaDeRecogida;
}
