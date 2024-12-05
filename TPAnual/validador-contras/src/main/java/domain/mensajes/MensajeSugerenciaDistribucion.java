package domain.mensajes;

import domain.heladera.Heladera;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;
@Getter@Setter
@Entity
public class MensajeSugerenciaDistribucion extends Mensaje{
  @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
  private List<Heladera> heladeras;
}
