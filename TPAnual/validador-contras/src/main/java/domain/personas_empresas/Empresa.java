package domain.personas_empresas;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class Empresa {
  private String nombre;
  private List<Ayuda> catalogo;
}
