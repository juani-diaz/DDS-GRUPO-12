package domain.incidente;

import domain.heladera.EnumEstadoHeladera;
import domain.heladera.Heladera;
import domain.registro.SingletonSeguidorEstadistica;
import domain.rol.Colaborador;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter @Setter
@Entity
public class IncidenteFallaTecnica extends Incidente {
  @OneToOne
  private Colaborador colaborador;
  @Column
  private String descripcion;
  @Column
  private String foto; //TODO: definir el tipo de la foto (esta como string)

  public IncidenteFallaTecnica(Heladera heladera, Date fecha, Colaborador colaborador, String descripcion, String foto){
    super(heladera, fecha, new ArrayList<VisitasTecnicas>(), EnumEstadoDeIncidente.PENDIENTE_A_SOLUCIONAR);
    this.colaborador = colaborador;
    this.descripcion = descripcion;
    this.foto = foto;
    heladera.setEstado(EnumEstadoHeladera.INACTIVA_POR_FALLA);
    SingletonSeguidorEstadistica se = SingletonSeguidorEstadistica.getInstance();
    se.getIncidentes().add(this);
  }
  // Si no esta este metodo tira error el JPA/Hibernate
  public IncidenteFallaTecnica() {

  }

  @Override
  public void setHeladera(Heladera heladera) {
    super.setHeladera(heladera);
    heladera.setEstado(EnumEstadoHeladera.INACTIVA_POR_FALLA);
  }
}
