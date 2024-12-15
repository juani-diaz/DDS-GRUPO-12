package domain.heladera;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import persistence.EntidadPersistente;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
public class Ubicacion extends EntidadPersistente {

    @Column
    private String ciudad;
    @Column
    private String localidad;
    @Column
    private String latitud;
    @Column
    private String longitud;
    @Column
    private String calle;
    @Column
    private String altura;

    //public ContructorUbicacion(String ciudad, String latitud, String longitud, String calle, String altura) {
    //    this.ciudad = ciudad;
    //    this.latitud = latitud;
    //    this.longitud = longitud;
    //    this.calle = calle;
    //    this.altura = altura;
    //}
}