package domain.heladera;

import domain.rol.Tarjeta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.time.DateUtils;
import persistence.EntidadPersistente;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AdministradorSolicitudes extends EntidadPersistente {

  @Transient
  private SingletonListadoHeladeras listadoHeladeras;

  @OneToMany
  private List<PedidoApertura> pedidos;

  @Transient
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

  public boolean puedeAbrir(Tarjeta tarjeta, Heladera heladera) {
    try {
      // Crea una copia de la lista de pedidos y la invierte
      List<PedidoApertura> pedidosInvertidos = new ArrayList<>(this.pedidos);
      Collections.reverse(pedidosInvertidos);

      PedidoApertura pedido = pedidosInvertidos.stream()
              .filter(pe -> pe.getHeladera() == heladera && pe.getTarjeta() == tarjeta)
              .findFirst()
              .get();

      return pedido.getEstado() == EnumPedidoApertura.VALIDO;
    } catch (Exception e) {
      return false;
    }
  }

}
