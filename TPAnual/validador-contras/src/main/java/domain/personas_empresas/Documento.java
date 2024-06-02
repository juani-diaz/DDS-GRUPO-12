package domain.personas_empresas;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Documento {
  private String tipo;
  private String numero;
  private String sexo;
  private String genero;
  private String CUIL;
}
