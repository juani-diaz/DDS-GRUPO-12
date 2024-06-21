package domain.vianda;

import domain.heladera.Heladera;
import domain.heladera.RegistroMovimiento;
import domain.heladera.RegistroTemperatura;
import domain.heladera.SensorDeTemperatura;
import domain.personas_empresas.PersonaColaboradora;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Vianda {
  private String comida;
  private LocalDate fechaVencimiento;
  private LocalDate fechaDonacion;
  private PersonaColaboradora colaborador;
  private Heladera heladera;
  private String calorias;
  private Float peso;
  private EnumEstadoVianda estado;
}
