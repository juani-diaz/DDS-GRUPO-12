package domain.registro;

import domain.rol.Colaborador;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DonacionesColaborador {
  private Colaborador colaborador;
  private Integer cantidad;
}
