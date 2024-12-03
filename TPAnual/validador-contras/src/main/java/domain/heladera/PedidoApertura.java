package domain.heladera;

import domain.rol.Tarjeta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import persistence.EntidadPersistente;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoApertura {
  private Tarjeta tarjeta;
  private Heladera heladera;
  @Column
  private Date fechaVencimiento;
  @Enumerated(value = EnumType.STRING)
  private EnumPedidoApertura estado;
  @Enumerated(value = EnumType.STRING)
  private EnumMotivoApertura motivo;
}
