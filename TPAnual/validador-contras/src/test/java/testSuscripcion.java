import domain.heladera.EnumEstadoHeladera;
import domain.heladera.Heladera;
import domain.heladera.Ubicacion;
import domain.persona.Email;
import domain.persona.MedioDeContacto;
import domain.persona.Persona;
import domain.rol.Colaborador;
import domain.servicios.LectorCsv;
import domain.servicios.Mailer;
import domain.suscripcion.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class testSuscripcion {

    @Test
    public void seNotificaBien() {
        Publicador publicador=new Publicador();

        Ubicacion ubicacion = new Ubicacion();
        LocalDate fechaFuncionamiento = LocalDate.now();

        Heladera heladera1 = new Heladera("heladera1",ubicacion, 100, fechaFuncionamiento,null,12F,20F, EnumEstadoHeladera.DISPONIBLE);
        MedioDeContacto medioDeContacto1= new Email();
        medioDeContacto1.setDireccion("juanidiaz8260@gmail.com");

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