package domain.colaboraciones;

import domain.heladera.Heladera;
import domain.rol.Colaborador;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ResponsableHeladera extends Colaboracion{
    private Heladera heladera;

    public ResponsableHeladera(Colaborador colaborador, LocalDate fecha, Heladera heladera){
        this.colaborador = colaborador;
        this.fecha = fecha;
        this.heladera = heladera;
    }

    public void ejecutar(){
        //TODO
    }

    public Float puntosObtenidos(){
        return 0F; // TODO
    }
}
