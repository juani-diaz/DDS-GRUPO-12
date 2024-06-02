package domain.personas_empresas;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter @Getter
public class MedioContacto {
  private List<String> emails;
  private List<String> telefonos;
  private List<String> whatsapps;
}
