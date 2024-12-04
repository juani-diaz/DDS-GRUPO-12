package domain.incidente;

import domain.rol.Tecnico;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import persistence.EntidadPersistente;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class VisitasTecnicas extends EntidadPersistente {
  @Transient
  private Tecnico tecnico;
  @Transient
  private Incidente incidente;
  @Transient
  private LocalDate fechaVisita;
  @Transient
  private String descripcion;
  @Transient
  private String foto; //TODO:definir el tipo de la foto (esta como string)
}
