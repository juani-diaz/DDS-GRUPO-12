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
  private Float cantidadPuntos;

  public void realizarColaboracion(Colaboracion colaboracion){
    //TODO
    colaboracion.realizarColaboracion();
  }

  public void actualizarPuntos(Integer puntosPorColaborar){
    //TODO
    set
  }

  public void realizarCanje(){
    //TODO
  }
}
