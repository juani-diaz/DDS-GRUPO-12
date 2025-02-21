package domain.incidente;

import domain.heladera.EnumEstadoHeladera;
import domain.heladera.Heladera;
import domain.registro.SingletonSeguidorEstadistica;
import domain.rol.Colaborador;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import persistence.Repos.RepoHeladera;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

@Getter @Setter
@Entity @NoArgsConstructor
public class IncidenteFallaTecnica extends Incidente {
  @OneToOne
  private Colaborador colaborador;
  @Column
  private String descripcion;
  @Column
  private String foto;

  public IncidenteFallaTecnica(Heladera heladera, LocalDate fecha, Colaborador colaborador, String descripcion, String foto){
    super(heladera, fecha, new ArrayList<VisitasTecnicas>(), EnumEstadoDeIncidente.PENDIENTE_A_SOLUCIONAR);
    this.colaborador = colaborador;
    this.descripcion = descripcion;
    this.foto = foto;

    heladera.setEstado(EnumEstadoHeladera.INACTIVA_POR_FALLA);
    RepoHeladera.getInstance().updateHeladera(heladera);

    SingletonSeguidorEstadistica se = SingletonSeguidorEstadistica.getInstance();
    se.addIncidente(this);
  }

  @Override
  public void setHeladera(Heladera heladera) {
    super.setHeladera(heladera);
    heladera.setEstado(EnumEstadoHeladera.INACTIVA_POR_FALLA);
  }
}
