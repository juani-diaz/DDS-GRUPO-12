package domain.incidente;

import domain.rol.Tecnico;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import persistence.EntidadPersistente;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class VisitasTecnicas extends EntidadPersistente {
  @OneToOne
  private Tecnico tecnico;
  @OneToOne
  private Incidente incidente;
  @Column(columnDefinition = "DATE")
  private LocalDate fechaVisita;
  @Column
  private String descripcion;
  @Column
  private String foto; //TODO:definir el tipo de la foto (esta como string)
}
