package domain.persona;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import persistence.EntidadPersistente;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.List;
@Setter@Getter
@Entity
public abstract class MedioDeContacto extends EntidadPersistente {

  @Column
  private String email;

  @Column
  private String telefono;

  @Column
  private String celular;

  protected String direccion;

  public void notificar(String header,String mensaje){};


}
