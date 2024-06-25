package domain.personas_empresas;

import domain.colaboraciones.*;
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
    colaboracion.ejecutar();
    this.colaboraciones.add(colaboracion);
    this.actualizarPuntos();
  }

  public void actualizarPuntos(){
    Float puntosNuevos = 0F;
    for(Colaboracion col : this.colaboraciones){
      puntosNuevos += col.puntosObtenidos();
    }
    this.cantidadPuntos = puntosNuevos;
  }

  public void realizarCanje(){
    //TODO
  }
}
