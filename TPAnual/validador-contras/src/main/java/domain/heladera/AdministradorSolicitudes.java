package domain.heladera;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdministradorSolicitudes {
  private SingletonListadoHeladeras listadoHeladeras;
  private List<PedidoApertura> pedidos;

  public PedidoApertura solicitarApertura(){
    // TODO
    return null;
  }
}
