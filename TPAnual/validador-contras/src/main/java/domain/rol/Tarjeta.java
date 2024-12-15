package domain.rol;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import persistence.EntidadPersistente;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Embeddable
@Entity
public class Tarjeta extends EntidadPersistente {
  @Column
  private String identificador;
}
