package domain.heladera;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Ubicacion {
    private String ciudad;
    private String latitud;
    private String longitud;
    private String calle;
    private String altura;
}