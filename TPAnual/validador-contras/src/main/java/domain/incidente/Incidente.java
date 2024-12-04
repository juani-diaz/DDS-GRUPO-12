package domain.incidente;

import domain.heladera.EnumEstadoHeladera;
import domain.heladera.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import persistence.EntidadPersistente;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

@Getter @Setter
@Entity
public abstract class Incidente extends EntidadPersistente {

  @Transient
  private Heladera heladera;

  @Transient
  private Date fecha;

  @Transient
  private List<VisitasTecnicas> evolucionDeIncidente;

  @Transient
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

