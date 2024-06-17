package domain.personas_empresas;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter @Getter
public class PersonaColaboradora {
  private String direccion;
  private List<Ayuda> ayudasList;
  private MedioContacto contacto;

  public Float cantidadPuntos(){
    return 0F; //TODO SERVICIO
  }

  public void canjear(){
     //TODO SERVICIO
  }
}
