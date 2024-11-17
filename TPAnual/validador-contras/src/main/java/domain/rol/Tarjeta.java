package domain.rol;

import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Embeddable
public class Tarjeta {
  private String identificador;
}
