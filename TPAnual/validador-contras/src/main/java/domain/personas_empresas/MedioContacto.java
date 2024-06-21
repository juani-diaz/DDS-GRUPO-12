package domain.personas_empresas;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class MedioContacto {
  private List<String> emails;
  private List<String> telefonos;
  private List<String> whatsapps;
}
