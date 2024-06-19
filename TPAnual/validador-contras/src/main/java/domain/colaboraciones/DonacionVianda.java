package domain.colaboraciones;

import domain.vianda.Vianda;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class DonacionVianda extends Colaboracion {
    private Vianda vianda;
    private Float multiplicador;
}
