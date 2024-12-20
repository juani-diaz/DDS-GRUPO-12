package domain.persona;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Setter@Getter
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class MedioDeContacto {

  @Enumerated
  private Medio medio;

  @Column
  private String contacto; // Ejemplo: "email@example.com"

  @ManyToOne
  private Persona persona;

  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  private int id;

  public void notificar(String header,String mensaje){};


}
