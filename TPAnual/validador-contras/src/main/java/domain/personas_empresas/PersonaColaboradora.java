package domain.personas_empresas;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter @Getter
public class PersonaColaboradora {
  private String dericcion;
  private List<Ayuda> ayudasList;
  private MedioContacto contacto;
  private Integer cantidadPuntos;
}
