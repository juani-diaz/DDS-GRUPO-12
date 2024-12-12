package domain.rol;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
public class LocalidadesTecnico {
    private List<LocalidadTecnico> localidades;

    public LocalidadesTecnico(){
        this.localidades = new ArrayList<>(Arrays.asList(
            new LocalidadTecnico("c_1","Comuna 1"),
            new LocalidadTecnico("c_2","Comuna 2"),
            new LocalidadTecnico("c_3","Comuna 3"),
            new LocalidadTecnico("c_4","Comuna 4"),
            new LocalidadTecnico("c_5","Comuna 5"),
            new LocalidadTecnico("c_6","Comuna 6"),
            new LocalidadTecnico("c_7","Comuna 7"),
            new LocalidadTecnico("c_8","Comuna 8"),
            new LocalidadTecnico("c_9","Comuna 9"),
            new LocalidadTecnico("c_10","Comuna 10"),
            new LocalidadTecnico("c_11","Comuna 11"),
            new LocalidadTecnico("c_12","Comuna 12"),
            new LocalidadTecnico("c_13","Comuna 13"),
            new LocalidadTecnico("c_14","Comuna 14"),
            new LocalidadTecnico("c_15","Comuna 15"),
            new LocalidadTecnico("zs","Zona Sur"),
            new LocalidadTecnico("zo","Zona Oeste"),
            new LocalidadTecnico("zn","Zona Norte")
        ));
    }

    public List<String> nombres(){
        return localidades.stream().map(LocalidadTecnico::getNombre).collect(Collectors.toList());
    }

}
