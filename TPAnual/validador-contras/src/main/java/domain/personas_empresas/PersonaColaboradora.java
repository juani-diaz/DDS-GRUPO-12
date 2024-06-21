package domain.personas_empresas;

import domain.colaboraciones.Colaboracion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PersonaColaboradora extends Persona {
  private List<Colaboracion> colaboraciones;
  private Float puntos;

  public void realizarColaboracion(){
    //TODO
  }

  public void actualizarPuntos(){
    //TODO
  }

  public void realizarCanje(){
    //TODO
  }
}
