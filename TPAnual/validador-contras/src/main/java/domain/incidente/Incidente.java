package domain.incidente;

import domain.heladera.EnumEstadoHeladera;
import domain.heladera.*;
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

  public Incidente(Heladera heladera, Date fecha, List<VisitasTecnicas> evolucionDeIncidente, EnumEstadoDeIncidente estadoDeIncidente) {
    this.heladera = heladera;
    this.fecha = fecha;
    this.evolucionDeIncidente = evolucionDeIncidente;
    this.estadoDeIncidente = estadoDeIncidente;
  }

  public void flujoDeSolucion(){
    cerrarTiquetIncidente(heladera);
  }

  public void modificarEstado(EnumEstadoDeIncidente nuevoEstado){
    this.estadoDeIncidente = nuevoEstado;
  }
  public void cerrarTiquetIncidente(Heladera heladera){
    this.estadoDeIncidente = EnumEstadoDeIncidente.SOLUCIONADO;
    heladera.setEstado(EnumEstadoHeladera.DISPONIBLE);
  }
}

