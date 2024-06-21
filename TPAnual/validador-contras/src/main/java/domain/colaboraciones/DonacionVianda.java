package domain.colaboraciones;

import domain.vianda.Vianda;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class DonacionVianda extends Colaboracion {
    private Vianda vianda;

    public void ejecutar(){
        //TODO
    }
}
