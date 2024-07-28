package domain.registro;

import domain.heladera.Heladera;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoViandasHeladera {
  private Heladera heladera;
  private Integer ingresadas;
  private Integer retiradas;
}
