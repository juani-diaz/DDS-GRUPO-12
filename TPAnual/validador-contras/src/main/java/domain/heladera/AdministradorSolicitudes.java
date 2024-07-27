package domain.heladera;

import domain.rol.Colaborador;
import domain.rol.Tarjeta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdministradorSolicitudes {
  private SingletonListadoHeladeras listadoHeladeras;
  private List<PedidoApertura> pedidos;
  private final Integer CANTIDAD_HORAS = 3;

  public PedidoApertura solicitarApertura(Tarjeta tarjeta, Integer indiceHeladera, EnumMotivoApertura motivo){
    PedidoApertura pedido = new PedidoApertura(
            tarjeta,
            listadoHeladeras.getHeladeras().get(indiceHeladera),
            DateUtils.addHours(new Date(), CANTIDAD_HORAS),
            EnumPedidoApertura.VALIDO,
            motivo
    );
    this.pedidos.add(pedido);
    return pedido;
  }

  public boolean puedeAbrir(Tarjeta tarjeta, Heladera heladera){
    try {
      PedidoApertura pedido = this.pedidos.reversed().stream().filter(pe -> pe.getHeladera() == heladera && pe.getTarjeta() == tarjeta).findFirst().get();
      return pedido.getEstado() == EnumPedidoApertura.VALIDO;
    } catch (Exception e) {
      return false;
    }
  }
}
