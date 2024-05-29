package domain.heladera;

import domain.vianda.Vianda;
import lombok.Getter;
import lombok.Setter;
import sun.management.Sensor;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
public class Heladera {
  private String nombre;
  private Ubicacion direccion;
  private Integer TamanioEnViandas;
  private LocalDate fechaFuncionamiento;
  private List<Vianda> viandasEnHeladeraList;
  private Float temperaturaMinima;
  private Float temperaturaMaxima;
  private List<Sensore> sensores
}
