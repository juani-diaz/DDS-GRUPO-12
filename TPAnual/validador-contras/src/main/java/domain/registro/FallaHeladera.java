package domain.registro;

import domain.heladera.Heladera;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FallaHeladera {
  private Heladera heladera;
  private Integer fallas;
}
