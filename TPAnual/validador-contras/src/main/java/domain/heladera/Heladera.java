package domain.heladera;

import com.mysql.cj.xdevapi.FetchResult;
import lombok.*;
import lombok.Getter;
import lombok.Setter;
//import sun.management.Sensor;

import domain.vianda.*;
import persistence.EntidadPersistente;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "heladera")
public class Heladera extends EntidadPersistente {

  @Column(name = "nombre")
  private String nombre;

  //public String getNombre() {
  //  return nombre;
  //}

  @ManyToOne
  private Ubicacion direccion;

  @Column
  private Integer tamanioEnViandas;

  @Column(columnDefinition = "DATE")
  private LocalDate fechaFuncionamiento;

  @OneToMany(mappedBy = "heladera", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
  private List<Vianda> viandasEnHeladera; //=new ArrayList<>()

  @Column
  private Float temperaturaMinima;

  @Column
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

