package domain.heladera;

import lombok.*;
//import sun.management.Sensor;

import domain.vianda.Vianda;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Heladera {
  private String nombre;
  private Ubicacion direccion;
  private Integer tamanioEnViandas;
  private LocalDate fechaFuncionamiento;
  private List<Vianda> viandasEnHeladera;
  private Float temperaturaMinima;
  private Float temperaturaMaxima;
  private List<Sensor> sensores;
  private EnumEstadoHeladera estado;
  private List<RegistroTemperatura> temperaturas;

  public void ingresarViandas(List<Vianda> Viandas) {
    System.out.println(Viandas.size());
    this.viandasEnHeladera.addAll(Viandas);
  }
  public void sacarViandas(List<Vianda> Viandas) {
    viandasEnHeladera.removeAll(Viandas);
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
