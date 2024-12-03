package domain.rol;

import lombok.*;
import persistence.EntidadPersistente;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Embeddable
@Entity
public class Tarjeta extends EntidadPersistente {

  private String identificador;
}
