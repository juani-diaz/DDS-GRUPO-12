package domain.incidente;

import domain.heladera.Heladera;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter @Setter @AllArgsConstructor
public abstract class Incidente {
  Heladera heladera;
  Date fecha;
  List<VisitasTecnicas> evolucionDeIncidente;
  EnumEstadoDeIncidente estadoDeIncidente;

  public abstract void flujoDeSolucion();
  //public abstract void cerrarTiquetDeIncidente();//TODO:funciones de Incidente
}
