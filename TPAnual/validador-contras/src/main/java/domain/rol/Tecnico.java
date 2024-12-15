package domain.rol;

import domain.incidente.Incidente;
import domain.incidente.VisitasTecnicas;
import domain.persona.Persona;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
@DiscriminatorValue("tecnico")
public class Tecnico extends Rol {

  @ElementCollection
  @CollectionTable(name = "areaCobertura_lista", joinColumns = @JoinColumn(name = "tecnico_id"))
  @Column(name = "areaCobertura")
  private List<String> areaCobertura;
//Empezar a ver incidentes
  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType. LAZY)
  private List<Incidente> incidentesARevisar;
//Ver las visitas tecnicas
  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType. LAZY)
  private List<VisitasTecnicas> visitasRealizadas;

  public Tecnico(Persona p, List<String> ac, List<Incidente> iAr , List<VisitasTecnicas> vT){
    this.persona = p;
    this.areaCobertura = ac;
    this.incidentesARevisar = iAr;
    this.visitasRealizadas = vT;
  }

  public Tecnico(Persona p, List<String> ac){
    this.persona = p;
    this.areaCobertura = ac;
    this.incidentesARevisar = new ArrayList<>();
    this.visitasRealizadas = new ArrayList<>();
  }

  public Tecnico(Persona p){
    this.persona = p;
    this.areaCobertura = new ArrayList<String>();
    this.incidentesARevisar = new ArrayList<Incidente>();
    this.visitasRealizadas = new ArrayList<VisitasTecnicas>();
  }


  private void realizarVisitaTecnica(Incidente incidente, LocalDate fecha, String trabajoRealizado, String foto, Boolean solucionado){

    VisitasTecnicas visitaTecnica = new VisitasTecnicas(this,incidente,fecha,trabajoRealizado,foto);

    visitasRealizadas.add(visitaTecnica);
    incidentesARevisar.remove(incidente);

    if (solucionado){
      incidente.flujoDeSolucion();
    }

  }

  private void serNotificadoDeIncidente(Incidente incidente){
    incidentesARevisar.add(incidente);
  }
}
