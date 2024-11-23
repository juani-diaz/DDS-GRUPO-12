package domain.vianda;

import domain.heladera.Heladera;
import domain.rol.Colaborador;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Entity
public class Vianda {
  @Id @GeneratedValue
  private Long id;
  private String comida;
  private LocalDate fechaVencimiento;
  private LocalDate fechaDonacion;

  @ManyToOne
  private Heladera heladera;
  private String calorias;
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
