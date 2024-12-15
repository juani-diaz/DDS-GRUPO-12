import domain.heladera.EnumEstadoHeladera;
import domain.heladera.Heladera;
import domain.heladera.Ubicacion;
import domain.persona.Email;
import domain.persona.MedioDeContacto;
import domain.suscripcion.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

public class testSuscripcion {

    @Test
    public void seNotificaBien() {
        Publicador publicador=new Publicador();

        Ubicacion ubicacion = new Ubicacion();
        LocalDate fechaFuncionamiento = LocalDate.now();

        Heladera heladera1 = new Heladera("heladera1",ubicacion, 100, fechaFuncionamiento,12F,20F, EnumEstadoHeladera.DISPONIBLE);
        MedioDeContacto medioDeContacto1= new Email();
        medioDeContacto1.setContacto("juanidiaz8260@gmail.com");

        Suscripcion suscripcion1=new ExcesoViandas(heladera1,medioDeContacto1,1);
        Suscripcion suscripcion2=new PocasViandas(heladera1,medioDeContacto1,1);
        Suscripcion suscripcion3=new HeladeraNoFuncional(heladera1,medioDeContacto1);


         ArrayList lista=new ArrayList<>();
         lista.add(suscripcion1);
        lista.add(suscripcion2);
        lista.add(suscripcion3);
        publicador.setObservers(lista);

        publicador.notifyObservers();

    }
}