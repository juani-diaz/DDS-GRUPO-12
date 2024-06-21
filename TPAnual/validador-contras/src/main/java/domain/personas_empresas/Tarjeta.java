package domain.personas_empresas;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Tarjeta {
  private String identificador;
  private PersonaColaboradora donante;
}
