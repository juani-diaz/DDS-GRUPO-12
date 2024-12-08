package domain.incidente;

import domain.heladera.EnumEstadoHeladera;
import domain.heladera.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import persistence.EntidadPersistente;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;

@Getter @Setter
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Incidente {

  @Id
  private int id;

  @ManyToOne
  private Heladera heladera;

  @Column
  private Date fecha;

  @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
  private List<VisitasTecnicas> evolucionDeIncidente;

  @Enumerated
  private EnumEstadoDeIncidente estadoDeIncidente;


  public Incidente(Heladera heladera, Date fecha, List<VisitasTecnicas> evolucionDeIncidente, EnumEstadoDeIncidente estadoDeIncidente) {
    this.heladera = heladera;
    this.fecha = fecha;
    this.evolucionDeIncidente = evolucionDeIncidente;
    this.estadoDeIncidente = estadoDeIncidente;
  }

  // Si no esta este metodo tira error el JPA/Hibernate
  public Incidente() {

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

