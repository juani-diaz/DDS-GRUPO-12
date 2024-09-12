package domain.incidente;

import domain.heladera.EnumEstadoHeladera;
import domain.heladera.Heladera;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

@Getter @Setter
public class IncidenteAlarma extends Incidente{
  private EnumTipoDeFalla enumTipoDeFalla;

  public IncidenteAlarma(Heladera heladera, Date fecha, EnumTipoDeFalla falla){
    super(heladera, fecha, new ArrayList<VisitasTecnicas>(), EnumEstadoDeIncidente.PENDIENTE_A_SOLUCIONAR);
    this.enumTipoDeFalla = falla;
    heladera.setEstado(EnumEstadoHeladera.INACTIVA_POR_ALERTA);
  }

  @Override
  public void setHeladera(Heladera heladera) {
    super.setHeladera(heladera);
    heladera.setEstado(EnumEstadoHeladera.INACTIVA_POR_FALLA);
  }
}



