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
public class ReporteMovimientoViandas extends Reporte {
  private List<MovimientoViandasHeladera> detalle;

  public int cantidadFallasPorHeladera(String nombreHeladera){
    return detalle.stream().filter(movimientoViandasHeladera -> Objects.equals(movimientoViandasHeladera.getHeladera().getNombre(), nombreHeladera)).toList().size();
  }
}
