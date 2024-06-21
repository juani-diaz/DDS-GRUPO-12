package domain.heladera;

import lombok.*;
//import sun.management.Sensor;

import domain.vianda.Vianda;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Heladera {
  private String nombre;
  private Ubicacion direccion;
  private Integer tamanioEnViandas;
  private LocalDate fechaFuncionamiento;
  private List<Vianda> viandasEnHeladera;
  private Float temperaturaMinima;
  private Float temperaturaMaxima;
  private EnumEstadoHeladera estado;

  public void ingresarViandas(List<Vianda> Viandas) {
    this.viandasEnHeladera.addAll(Viandas);
  }

  public Vianda sacarVianda(int indice) {
    return viandasEnHeladera.remove(indice);
  }

}
