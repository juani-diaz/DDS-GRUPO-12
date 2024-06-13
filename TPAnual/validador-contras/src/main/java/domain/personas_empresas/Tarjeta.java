package domain.personas_empresas;

import lombok.*;

@Getter @Setter @AllArgsConstructor
public class Tarjeta {
  private String identificador;
  private PersonaColaboradora donante;
}
