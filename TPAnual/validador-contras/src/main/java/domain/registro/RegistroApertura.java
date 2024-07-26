package domain.registro;

import domain.rol.Tarjeta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistroApertura extends RegistroSensor {
  private Tarjeta tarjetaLeida;
  private boolean exitoso;
}
