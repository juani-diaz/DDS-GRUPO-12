package domain.heladera;

import domain.incidente.Incidente;
import domain.persona.PersonaJuridica;
import domain.rol.Colaborador;
import domain.vianda.Vianda;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import persistence.EntidadPersistente;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "heladera")
public class Heladera extends EntidadPersistente {

  @Column(name = "nombre")
  private String nombre;

  //public String getNombre() {
  //  return nombre;
  //}

  @ManyToOne
  private Ubicacion direccion;

  @Column
  private Integer tamanioEnViandas;

  @Column(columnDefinition = "DATE")
  private LocalDate fechaFuncionamiento;

  @OneToMany(mappedBy = "heladera", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
  private List<Vianda> viandasEnHeladera; //=new ArrayList<>()

  @OneToMany(mappedBy = "heladera", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
  private List<Incidente> incidentes;



  @Column
  private Float temperaturaMinima;

  @Column
  private Float temperaturaMaxima;

  @Enumerated(value = EnumType.STRING)
  private EnumEstadoHeladera estado;

  @OneToOne
  private Colaborador responsable = null;//Tendria que se una persona

  public void ingresarViandas(List<Vianda> viandas) {
    this.viandasEnHeladera.addAll(viandas);
  }

  public Vianda sacarViandaPorIndice(int indice) {
    viandasEnHeladera=new LinkedList<>(viandasEnHeladera);
    return viandasEnHeladera.remove(indice);
  }

  public void sacarViandas(List<Vianda> viandas){
    viandasEnHeladera.removeAll(viandas);
  }

  public void setResponsable(Colaborador responsable) {
    if (!(responsable.getPersona() instanceof PersonaJuridica)) {
    throw new IllegalArgumentException("El responsable debe ser una persona juridica");
    }
      this.responsable = responsable;
  }

  public Integer cantidadViandas(){
    return viandasEnHeladera.size();
  }

  public Heladera (String nombre,
                   Ubicacion direccion,
                   Integer tamanioEnViandas,
                   LocalDate fechaFuncionamiento,
                   //List<Vianda> viandas,
                   Float temperaturaMinima,
                   Float temperaturaMaxima,
                   EnumEstadoHeladera estado){
    this.nombre = nombre;
    this.direccion = direccion;
    this.tamanioEnViandas = tamanioEnViandas;
    this.fechaFuncionamiento = fechaFuncionamiento;
    this.viandasEnHeladera = new ArrayList<Vianda>();
    this.temperaturaMinima = temperaturaMinima;
    this.temperaturaMaxima = temperaturaMaxima;
    this.estado = estado;
  }
  public LocalDate getFechaFuncionamiento(){
        return fechaFuncionamiento;
  }

}

