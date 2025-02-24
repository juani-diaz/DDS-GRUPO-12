package domain.incidente;

import domain.heladera.EnumEstadoHeladera;
import domain.heladera.Heladera;
import domain.registro.SingletonSeguidorEstadistica;
import domain.rol.Tecnico;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import persistence.EntidadPersistente;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter @Setter
@Entity @NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Incidente extends EntidadPersistente {

  @ManyToOne
  private Heladera heladera;

  @ManyToOne
  private Tecnico tecnico;

  @Column
  private LocalDate fecha;

  @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
  private List<VisitasTecnicas> evolucionDeIncidente;

  @Enumerated
  private EnumEstadoDeIncidente estadoDeIncidente;


  public Incidente(Heladera heladera, LocalDate fecha, List<VisitasTecnicas> evolucionDeIncidente, EnumEstadoDeIncidente estadoDeIncidente) {
    this.heladera = heladera;
    this.fecha = fecha;
    this.evolucionDeIncidente = evolucionDeIncidente;
    this.estadoDeIncidente = estadoDeIncidente;
  }

  public void asignarTecnico(Tecnico t){
    this.tecnico = t;
    this.estadoDeIncidente = EnumEstadoDeIncidente.TECNICO_ASIGNADO;

    SingletonSeguidorEstadistica.getInstance().updateIncidente(this);
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

