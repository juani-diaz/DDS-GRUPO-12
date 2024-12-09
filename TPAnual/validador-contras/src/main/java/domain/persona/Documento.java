package domain.persona;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.XSlf4j;

import javax.persistence.*;
import java.io.Serializable;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
public class Documento implements Serializable {;

  @Id
  @GeneratedValue
  private int id;

  @Column
  private String tipo;

  @Column
  private String numero;

  public Documento(String tipo, String numero) {
    this.tipo = tipo;
    this.numero = numero;
  }
}