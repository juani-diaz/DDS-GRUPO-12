package domain.heladera;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Ubicacion extends Heladera {
    private String ciudad;
    private String latitud;
    private String longitud;
    private String calle;
    private String altura;
}