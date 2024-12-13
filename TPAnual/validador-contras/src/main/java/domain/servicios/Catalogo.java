package domain.servicios;

import domain.colaboraciones.PresentacionOferta;
import domain.rol.Colaborador;
import lombok.Getter;
import persistence.EntidadPersistente;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Catalogo extends EntidadPersistente {

    @OneToMany
    private static final List<PresentacionOferta> ofertas = new ArrayList<>();

    public static void agregarAlCatalogo(PresentacionOferta p){
        ofertas.add(p);
    }

    public static void retirarDelCatalogo(Integer indice){
        ofertas.remove(indice);
    }

    public static boolean otorgar(Integer indiceCatalogo, Colaborador colaborador){
        PresentacionOferta o = ofertas.get(indiceCatalogo);
        if(o.getPuntosNecesarios() <= colaborador.getCantidadPuntos()){
            //TODO hacer lo que corresponda (mail, envio)
            colaborador.setCantidadPuntos(colaborador.getCantidadPuntos() - o.getPuntosNecesarios());
            // hay q remover la oferta de la lista del catalogo?
            return true;
        }
        return false;
    }
}
