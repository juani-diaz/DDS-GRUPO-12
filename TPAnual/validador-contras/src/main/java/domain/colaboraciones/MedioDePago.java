package domain.colaboraciones;

import lombok.Getter;
import lombok.Setter;
import persistence.EntidadPersistente;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter @Setter
@Entity
public class MedioDePago extends EntidadPersistente {
  @Column
  private String tipoTarjeta;
  @Column
  private String numTarjeta;
  @Column
  private String nombreTitular;
  @Column
  private String mesVencimiento;
  @Column
  private String codSeguridad;
}
