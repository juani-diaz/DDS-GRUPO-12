package domain.personas_empresas;

import lombok.*;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Tecnico extends Persona {
  private List<String> areaCobertura;
}
