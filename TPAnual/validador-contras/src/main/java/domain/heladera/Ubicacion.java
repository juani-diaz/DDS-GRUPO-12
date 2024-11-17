package domain.heladera;

import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
public class Ubicacion {
    @Id
    @GeneratedValue
    private Long id;
    private String ciudad;
    private String latitud;
    private String longitud;
    private String calle;
    private String altura;

    public Ubicacion(String ciudad, String latitud, String longitud, String calle, String altura) {
        this.ciudad = ciudad;
        this.latitud = latitud;
        this.longitud = longitud;
        this.calle = calle;
        this.altura = altura;
    }
}