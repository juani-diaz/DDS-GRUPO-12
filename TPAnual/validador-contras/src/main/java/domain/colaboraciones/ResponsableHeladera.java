package domain.colaboraciones;

import domain.heladera.Heladera;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResponsableHeladera extends Colaboracion{
    private Heladera heladeraNueva;
}
