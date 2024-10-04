package domain.persona;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
@Setter@Getter
public abstract class MedioDeContacto {

  protected String direccion;

  public void notificar(String header,String mensaje){};


}
