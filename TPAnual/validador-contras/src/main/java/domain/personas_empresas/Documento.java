package domain.personas_empresas;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Documento {
  private String tipo;
  private String numero;
  private String sexo;
  private String genero;
  private String cuil;
}