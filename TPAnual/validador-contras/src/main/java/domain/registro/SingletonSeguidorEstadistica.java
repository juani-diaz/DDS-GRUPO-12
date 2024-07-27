package domain.registro;

import domain.colaboraciones.DistribucionVianda;
import domain.colaboraciones.DonacionVianda;
import domain.incidente.Incidente;
import domain.vianda.ViandaRecogida;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.time.DateUtils;

import java.util.ArrayList;
import java.util.Date;
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
    List<FallaHeladera> fe = new ArrayList<FallaHeladera>();

    Date limite = DateUtils.addDays(new Date(), -7);
    List<Incidente> incidentesUltimaSemana = this.incidentes.stream().filter(
            i -> i.getFecha().after(limite)
    ).toList();

    for(Incidente i : incidentesUltimaSemana){
      FallaHeladera encontrado;
      try {
        encontrado = fe.stream().filter(f -> f.getHeladera() == i.getHeladera()).findFirst().get();
      } catch (Exception e) {
        encontrado = null;
      }
      if(encontrado != null){
        encontrado.setFallas(encontrado.getFallas()+1);
      } else {
        fe.add(new FallaHeladera(i.getHeladera(), 1));
      }
    }

    return new ReporteFallas(fe);
  }

  public ReporteMovimientoViandas generarReporteMovimientos() {
    List<MovimientoViandasHeladera> mve = new ArrayList<MovimientoViandasHeladera>();

    Date limite = DateUtils.addDays(new Date(), -7);

    List<DonacionVianda> donacionesUltimaSemana = this.donacionViandas.stream().filter(
            i -> i.getFecha().isAfter(limite)
    ).toList();

    return new ReporteMovimientoViandas();
  }

  public ReporteDonacionesViandas generarReporteDonaciones() {
    // TODO
    return null;
  }
}
