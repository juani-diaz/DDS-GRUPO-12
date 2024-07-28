package domain.incidente;

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
  }

  public void flujoDeSolucion(){} //TODO:hacer funcion flujoDeSolucion
}

