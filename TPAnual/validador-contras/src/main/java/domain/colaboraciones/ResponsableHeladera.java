package domain.colaboraciones;

import domain.heladera.Heladera;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResponsableHeladera extends Colaboracion{//TODO: Chequear q extend este este bien
    private Heladera heladeraNueva;
}
