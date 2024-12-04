package domain.heladera;

import domain.rol.Tarjeta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import persistence.EntidadPersistente;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PedidoApertura extends EntidadPersistente {
  @ManyToOne
  private Tarjeta tarjeta;
  @ManyToOne
  private Heladera heladera;
  @Column
  private Date fechaVencimiento;
  @Enumerated(value = EnumType.STRING)
  private EnumPedidoApertura estado;
  @Enumerated(value = EnumType.STRING)
  private EnumMotivoApertura motivo;
}
