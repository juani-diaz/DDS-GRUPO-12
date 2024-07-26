package domain.heladera;

import domain.rol.Tarjeta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoApertura {
  private Tarjeta tarjeta;
  private Heladera heladera;
  private Date fechaVencimiento;
  private EnumPedidoApertura estado;
  private EnumMotivoApertura motivo;
}
