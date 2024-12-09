package views;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import domain.heladera.EnumEstadoHeladera;
import domain.heladera.Heladera;
import domain.heladera.Ubicacion;

public class HelperSource {
    public String lat_lon(Ubicacion u) {
        return u.getLatitud()+","+u.getLongitud();
    }

    public String viandas_del_total(Heladera h){
        return h.getViandasEnHeladera().size()+"/"+h.getTamanioEnViandas();
    }

}

