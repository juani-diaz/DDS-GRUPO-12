package domain.vianda;

import domain.colaboraciones.DistribucionVianda;
import domain.heladera.Heladera;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import persistence.EntidadPersistente;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Entity
public class Vianda extends EntidadPersistente {

  @Column
  private String comida;

  @Column(columnDefinition = "DATE")
  private LocalDate fechaVencimiento;

  @Column(columnDefinition = "DATE")
  private LocalDate fechaDonacion;

  @ManyToOne
  @JoinColumn(name="heladera_id", referencedColumnName = "id")
  private Heladera heladera;

  @Column
  private String calorias;

  @ManyToMany (mappedBy = "viandasMovidas")
  private List<DistribucionVianda> distribucionVianda;

  @Column
  private Float peso;

  @Enumerated(value = EnumType.STRING)
  private EnumEstadoVianda estado;

  @OneToOne
  @JoinColumn(name = "id")
  private ViandaRecogida viandaRecogida;

  public void setHeladera(Heladera heladera) {
    this.heladera = heladera;
  }

  public Vianda(String comida, LocalDate fechaVencimiento, LocalDate fechaDonacion/*,Heladera heladera*/, String calorias, Float peso, EnumEstadoVianda estado) {
    this.comida = comida;
    this.fechaVencimiento = fechaVencimiento;
    this.fechaDonacion = fechaDonacion;
    this.heladera = heladera;
    this.calorias = calorias;
    this.peso = peso;
    this.estado = estado;
  }



}
