package domain.registro;

import domain.colaboraciones.DistribucionVianda;
import domain.colaboraciones.DonacionVianda;
import domain.incidente.Incidente;
import domain.vianda.ViandaRecogida;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SingletonSeguidorEstadistica {
  private List<Incidente> incidentes;
  private List<DonacionVianda> donacionViandas;
  private List<DistribucionVianda> distribucionViandas;
  private List<ViandaRecogida> retirosViandas;

  public ReporteFallas generarReporteFallas() {
    // TODO
    return null;
  }

  public ReporteMovimientoViandas generarReporteMovimientos() {
    // TODO
    return null;
  }

  public ReporteDonacionesViandas generarReporteDonaciones() {
    // TODO
    return null;
  }
}
