package domain.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocalidadCantidad {
    private String localidad;
    private Integer cantidadPersonas;
    private List<String> nombresPersonas;
}
