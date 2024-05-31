package domain.vianda;

import domain.heladera.Heladera;
import domain.personas_empresas.PersonaColaboradora;

import java.time.LocalDate;

public class Vianda {
  private String comida;
  private LocalDate fechaVencimiento;
  private LocalDate fechaDonacion;
  private PersonaColaboradora colaborador;
  private Heladera heladera;
  private String calorias;
  private String peso;
  private EnumEstadoVianda estado;
}
