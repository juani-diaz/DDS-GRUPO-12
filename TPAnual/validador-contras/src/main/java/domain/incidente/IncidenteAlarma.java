package domain.incidente;

import domain.heladera.EnumEstadoHeladera;
import domain.heladera.Heladera;
import domain.registro.SingletonSeguidorEstadistica;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import java.util.ArrayList;
import java.util.Date;

@Getter @Setter
@Entity
public class IncidenteAlarma extends Incidente{

  @Enumerated
  private EnumTipoDeFalla enumTipoDeFalla;

  public IncidenteAlarma(Heladera heladera, Date fecha, EnumTipoDeFalla falla){
    super(heladera, fecha, new ArrayList<VisitasTecnicas>(), EnumEstadoDeIncidente.PENDIENTE_A_SOLUCIONAR);
    this.enumTipoDeFalla = falla;
    heladera.setEstado(EnumEstadoHeladera.INACTIVA_POR_ALERTA);
    SingletonSeguidorEstadistica se = SingletonSeguidorEstadistica.getInstance();
    se.getIncidentes().add(this);
  }

  // Si no esta este metodo tira error el JPA/Hibernate
  public IncidenteAlarma() {

  }

  @Override
  public void setHeladera(Heladera heladera) {
    super.setHeladera(heladera);
    heladera.setEstado(EnumEstadoHeladera.INACTIVA_POR_FALLA);
  }
}



