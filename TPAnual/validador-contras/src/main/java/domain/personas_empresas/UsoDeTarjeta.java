package domain.personas_empresas;

import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDate;

@Getter @Setter
public class UsoDeTarjeta {
  private LocalDate dia;
  private Time hora;
  private Heladera heladera;
  private Tarjeta tarjeta;
}
