package domain.registro;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReporteFallas extends Reporte {
  private List<FallaHeladera> detalle;

  public int cantidadFallasPorHeladera(String nombreHeladera){
    return detalle.stream().filter(fallaHeladera -> Objects.equals(fallaHeladera.getHeladera().getNombre(), nombreHeladera)).toList().size();
  }
}
