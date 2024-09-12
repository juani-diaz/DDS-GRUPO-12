package domain.heladera;

import lombok.*;
//import sun.management.Sensor;

import domain.vianda.*;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Heladera {
  private String nombre;
  private Ubicacion direccion;
  private Integer tamanioEnViandas;
  private LocalDate fechaFuncionamiento;
  private List<Vianda> viandasEnHeladera=new ArrayList<>();
  private Float temperaturaMinima;
  private Float temperaturaMaxima;
  private EnumEstadoHeladera estado;

  public void ingresarViandas(List<Vianda> Viandas) {
    this.viandasEnHeladera.addAll(Viandas);
  }

  public Vianda sacarVianda(int indice) {
    viandasEnHeladera=new LinkedList<>(viandasEnHeladera);
    return viandasEnHeladera.remove(indice);
  }

}
