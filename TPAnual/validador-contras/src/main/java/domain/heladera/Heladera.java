package domain.heladera;

import lombok.*;
//import sun.management.Sensor;

import domain.vianda.*;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
public class Heladera {
  @Id
  @GeneratedValue
  public long id;

  private String nombre;

  @OneToOne
  private Ubicacion direccion;

  private Integer tamanioEnViandas;
  private LocalDate fechaFuncionamiento;

  @OneToMany//(cascade = CascadeType.ALL)
  //@JoinColumn(name = "heladera")
  private List<Vianda> viandasEnHeladera=new ArrayList<>();

  private Float temperaturaMinima;
  private Float temperaturaMaxima;



  @Enumerated(value = EnumType.STRING)
  private EnumEstadoHeladera estado;

  public void ingresarViandas(List<Vianda> Viandas) {
    this.viandasEnHeladera.addAll(Viandas);
  }

  public Vianda sacarVianda(int indice) {
    viandasEnHeladera=new LinkedList<>(viandasEnHeladera);
    return viandasEnHeladera.remove(indice);
  }

  public Heladera (String nombre,
                   Ubicacion direccion,
                   Integer tamanioEnViandas,
                   LocalDate fechaFuncionamiento,
                   //List<Vianda> viandas,
                   Float temperaturaMinima,
                   Float temperaturaMaxima,
                   EnumEstadoHeladera estado){
    this.nombre = nombre;
    this.direccion = direccion;
    this.tamanioEnViandas = tamanioEnViandas;
    this.fechaFuncionamiento = fechaFuncionamiento;
    //this.viandasEnHeladera = viandas;
    this.temperaturaMinima = temperaturaMinima;
    this.temperaturaMaxima = temperaturaMaxima;
    this.estado = estado;
  }

}

