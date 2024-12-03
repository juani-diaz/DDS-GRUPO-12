package domain.vianda;

import domain.heladera.Heladera;
import domain.rol.Colaborador;
import lombok.*;
import persistence.EntidadPersistente;

import javax.persistence.*;
import java.time.LocalDate;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Entity
public class Vianda extends EntidadPersistente {

  @Column
  private String comida;

  @Column
  private LocalDate fechaVencimiento;

  @Column
  private LocalDate fechaDonacion;

  @ManyToOne
  @JoinColumn(name="vianda_id", referencedColumnName = "id")
  private Heladera heladera;

  @Column
  private String calorias;

  @Column
  private Float peso;

  @Enumerated(value = EnumType.STRING)
  private EnumEstadoVianda estado;

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
