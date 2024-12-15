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
public class FallaHeladera {
  private Heladera heladera;
  private Integer fallas;
}
