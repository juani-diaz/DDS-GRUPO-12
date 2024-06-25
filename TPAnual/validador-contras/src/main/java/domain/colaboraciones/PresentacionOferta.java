package domain.colaboraciones;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PresentacionOferta extends Colaboracion{
    private String rubro;
    private String nombre;
    private String puntosNecesarios;
    private String imagen;

    public void ejecutar(){
        //TODO
    }

    public Float puntosObtenidos(){
        return 0F; // TODO
    }
}
