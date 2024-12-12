package domain.persona;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import persistence.EntidadPersistente;

import javax.persistence.*;
import java.util.List;
@Setter@Getter
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class MedioDeContacto {

  @Column
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
