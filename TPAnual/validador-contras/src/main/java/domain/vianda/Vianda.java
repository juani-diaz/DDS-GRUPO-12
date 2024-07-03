package domain.vianda;

import domain.heladera.Heladera;
import domain.rol.Colaborador;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Vianda {
  private String comida;
  private LocalDate fechaVencimiento;
  private LocalDate fechaDonacion;
  private Colaborador colaborador;
  private Heladera heladera;
  private String calorias;
  private Float peso;
  private EnumEstadoVianda estado;
}
