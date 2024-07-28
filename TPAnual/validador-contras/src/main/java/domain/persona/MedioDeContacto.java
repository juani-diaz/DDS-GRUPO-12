package domain.persona;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class MedioDeContacto {
  private List<String> emails;
  private List<String> telefonos;
  private List<String> whatsapps;
}
