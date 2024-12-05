package domain.registro;

import domain.rol.Tarjeta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RegistroApertura extends RegistroSensor {
  @OneToOne
  private Tarjeta tarjetaLeida;
  @Column
  private boolean exitoso;
}
