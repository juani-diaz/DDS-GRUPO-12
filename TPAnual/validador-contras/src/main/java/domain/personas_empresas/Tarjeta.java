package domain.personas_empresas;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Tarjeta {
  private String identificador;
  private PersonaColaboradora donante;
}
