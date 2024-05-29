package domain.personas_empresas;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class Ayuda {
  private PersonaColaboradora colaborador;
  private Empresa empresaColaboradora;
  private Colaboracion ayuda;
  private LocalDate fecha;
}
