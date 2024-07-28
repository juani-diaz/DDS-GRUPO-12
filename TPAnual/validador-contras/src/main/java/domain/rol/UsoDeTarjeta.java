package domain.rol;

import domain.heladera.Heladera;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UsoDeTarjeta {
  private LocalDate dia;
  private Time hora;
  private Heladera heladera;
  private Tarjeta tarjeta;
}
