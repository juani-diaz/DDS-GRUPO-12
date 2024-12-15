package domain.registro;

import domain.heladera.Heladera;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoViandasHeladera {
  private Heladera heladera;
  private Integer ingresadas;
  private Integer retiradas;
}
