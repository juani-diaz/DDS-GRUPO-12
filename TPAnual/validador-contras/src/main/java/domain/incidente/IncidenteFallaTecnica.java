package domain.incidente;

import domain.heladera.EnumEstadoHeladera;
import domain.heladera.Heladera;
import domain.rol.Colaborador;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter @Setter
public class IncidenteFallaTecnica extends Incidente {
  private Colaborador colaborador;
  private String descripcion;
  private String foto; //TODO: definir el tipo de la foto (esta como string)

  public IncidenteFallaTecnica(Heladera heladera, Date fecha, Colaborador colaborador, String descripcion, String foto){
    super(heladera, fecha, new ArrayList<VisitasTecnicas>(), EnumEstadoDeIncidente.PENDIENTE_A_SOLUCIONAR);
    this.colaborador = colaborador;
    this.descripcion = descripcion;
    this.foto = foto;
  }

  public void flujoDeSolucion() { //TODO:hacer funcion flujoDeSolucion
    getHeladera().setEstado(EnumEstadoHeladera.INACTIVA_POR_FALLA);

  }
}
