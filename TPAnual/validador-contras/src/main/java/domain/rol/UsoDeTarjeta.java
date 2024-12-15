package domain.rol;

import domain.heladera.Heladera;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import persistence.EntidadPersistente;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
public class UsoDeTarjeta extends EntidadPersistente {

  @Column(columnDefinition = "TIMESTAMP")
  private LocalDateTime dia_y_hora;

  @OneToOne
  private Heladera heladera;

  @OneToOne
  private Tarjeta tarjeta;
}
