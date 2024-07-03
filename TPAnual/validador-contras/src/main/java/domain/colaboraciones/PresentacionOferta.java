package domain.colaboraciones;

import domain.rol.Colaborador;
import domain.servicios.Catalogo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PresentacionOferta extends Colaboracion{
    private String rubro;
    private String nombre;
    private String puntosNecesarios;
    private String imagen;

    public PresentacionOferta(Colaborador colaborador, LocalDate fecha,
                              String rubro, String nombre, String  puntosNecesarios, String imagen){
        this.colaborador = colaborador;
        this.fecha = fecha;
        this.rubro = rubro;
        this.nombre = nombre;
        this. puntosNecesarios = puntosNecesarios;
        this.imagen = imagen;
    }

    public void ejecutar(){
        Catalogo.agregarAlCatalogo(this);
    }

    public Float puntosObtenidos(){
        return 0F; // TODO
    }
}
