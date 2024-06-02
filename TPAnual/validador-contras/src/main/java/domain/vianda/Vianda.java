package domain.vianda;

import java.time.LocalDate;

public class Vianda {
  private String comida;
  private LocalDate fechaVencimiento;
  private LocalDate fechaDonacion;
  private Persona colaborador;
  private Heladera heladera;
  private String calorias;
  private String peso;
  private EnumEstadoVianda estado;
}
