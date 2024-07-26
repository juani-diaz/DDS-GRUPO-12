package domain.incidente;

import domain.heladera.Heladera;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
public abstract class Incidente {
  Heladera heladera;
  LocalDate fecha;
  List<VisitasTecnicas> evolucionDeIncidente;
  EnumEstadoDeIncidente estadoDeIncidente;

  public abstract void flujoDeSolucion();
  //public abstract void cerrarTiquetDeIncidente();//TODO:funciones de Incidente
}
