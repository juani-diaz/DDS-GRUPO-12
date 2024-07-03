package domain.rol;

import domain.persona.Persona;
import lombok.*;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Tecnico extends Rol {
  private List<String> areaCobertura;
}
