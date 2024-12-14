package domain.colaboraciones;

import domain.registro.SingletonSeguidorEstadistica;
import domain.rol.Colaborador;
import domain.servicios.Catalogo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
public class PresentacionOferta extends Colaboracion{
    @Column
    private String rubro;
    @Column
    private String nombre;
    @Column
    private String descripcion;
    @Column
    private Float puntosNecesarios;
    @Column
    private String imagen;

    public PresentacionOferta(Colaborador colaborador, LocalDate fecha,
                              String rubro, String nombre, String descripcion, Float puntosNecesarios, String imagen){
        this.colaborador = colaborador;
        this.fecha = fecha;
        this.rubro = rubro;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.puntosNecesarios = puntosNecesarios;
        this.imagen = imagen;
    }

    public void ejecutar(){
        Catalogo.agregarAlCatalogo(this);
    }

    public Float puntosObtenidos(){
        return 0F; // TODO
    }
}
