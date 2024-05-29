package domain.personas_empresas;

import domain.vianda.ViandaRecogida;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
public class PersonaVulnerable {
  private String nombre;
  private LocalDate fechaNacimiento;
  private LocalDate fechaRegistro;
  private EnumSituacionCalle situacionCalle;
  private String domicilio;
  private Documento identificador;
  private String menoresACargo;
  private List<ViandaRecogida> viandasTomadasList;
  private Tarjeta tarjeta;
  private List<UsoDeTarjeta> usosList;
}
