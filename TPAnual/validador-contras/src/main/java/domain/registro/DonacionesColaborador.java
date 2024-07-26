package domain.registro;

import domain.rol.Colaborador;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DonacionesColaborador {
  private Colaborador colaborador;
  private Integer cantidad;
}
