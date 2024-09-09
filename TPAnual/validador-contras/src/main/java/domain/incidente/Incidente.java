package domain.incidente;

import domain.heladera.EnumEstadoHeladera;
import domain.heladera.Heladera;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter @Setter
public abstract class Incidente {
  private Heladera heladera;
  private Date fecha;
  private List<VisitasTecnicas> evolucionDeIncidente;
  private EnumEstadoDeIncidente estadoDeIncidente;

  public void flujoDeSolucion(){}

  public void modificarEstado(EnumEstadoDeIncidente nuevoEstado){
    this.estadoDeIncidente = nuevoEstado;
  }
  public void cerrarTiquetIncidente(Heladera heladera){
    this.estadoDeIncidente = EnumEstadoDeIncidente.SOLUCIONADO;
    heladera.setEstado(EnumEstadoHeladera.DISPONIBLE);
  }
}

