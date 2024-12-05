package domain.registro;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RegistroMovimiento extends RegistroSensor {
  @Column
  private Float movimiento;
}
