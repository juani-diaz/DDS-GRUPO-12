package domain.heladera;

import lombok.*;
//import sun.management.Sensor;

import domain.vianda.Vianda;
import domain.heladera.Sensor;
import domain.heladera.AdministradorHeladera;
import domain.heladera.Ubicacion;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Heladera {
  private String nombre;
  private Ubicacion direccion;
  private Integer TamanioEnViandas;
  private LocalDate fechaFuncionamiento;
  private List<Vianda> viandasEnHeladeraList;
  private Float temperaturaMinima;
  private Float temperaturaMaxima;
  private List<Sensor> sensores;

  public void ingresarViandas(List<Vianda> listViandas) {
    System.out.println(listViandas.size());
    this.viandasEnHeladeraList.addAll(listViandas);
  }
  public void sacarViandas(List<Vianda> listViandas) {
    viandasEnHeladeraList.removeAll(listViandas);
  }

  public List<EnumEstadoHeladera> getEstado() {
    List<EnumEstadoHeladera> alertas = new ArrayList<>();
    for(Sensor s: sensores) {
      if(s.getClass() == SensorDeTemperatura.class){ // reviso temperatura
        Float temp = ((SensorDeTemperatura) s).getUltimaTemperatura();
        if(temp > temperaturaMaxima || temp < temperaturaMinima){
          alertas.add(EnumEstadoHeladera.TEMPERATURA_FUERA_DE_RANGO);
        }
      } else { // movimiento

      }
    }
    if(alertas.isEmpty()){
      return Collections.singletonList(EnumEstadoHeladera.DISPONIBLE);
    }
    return alertas;
  }

}
