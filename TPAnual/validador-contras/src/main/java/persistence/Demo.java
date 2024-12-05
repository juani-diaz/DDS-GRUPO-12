package persistence;

import domain.api.ListadoLocalidades;
import domain.api.LocalidadCantidad;
import domain.colaboraciones.DonacionVianda;
import domain.heladera.EnumEstadoHeladera;
import domain.heladera.Heladera;
import domain.heladera.Ubicacion;
import domain.persona.Documento;
import domain.persona.MedioDeContacto;
import domain.persona.Persona;
import domain.persona.PersonaFisica;
import domain.registro.SingletonSeguidorEstadistica;
import domain.rol.*;
import domain.vianda.EnumEstadoVianda;
import domain.vianda.Vianda;
import domain.vianda.ViandaRecogida;
//import org.hibernate.type.LocalDateTimeType;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Demo {

    public static void main(String[] args) {

        Ubicacion direccion = new Ubicacion("BSAS","Villa Real", "1011", "200", "GUEMES" ,"4426");
        Heladera heladera = new Heladera("heladera2",direccion, 20, LocalDate.now(), 5f, 10.0f, EnumEstadoHeladera.INACTIVA_POR_FALLA);

        Vianda vianda = new Vianda("carne", LocalDate.now(), LocalDate.now(),  "100", 300f, EnumEstadoVianda.ENTREGADO);
        Vianda vianda2 = new Vianda("papa", LocalDate.now(), LocalDate.now(),  "100", 300f, EnumEstadoVianda.ENTREGADO);

        vianda.setHeladera(heladera);
        vianda2.setHeladera(heladera);
        List<Vianda> viandas = new ArrayList<Vianda>();
        viandas.add(vianda);
        viandas.add(vianda2);

        heladera.ingresarViandas(viandas);

        Ubicacion direccion2 = new Ubicacion("BSAS","Caballito", "2011", "100", "LORIA" ,"23");
        Heladera heladera2 = new Heladera("heladera22",direccion2, 20, LocalDate.now(), 5f, 10.0f, EnumEstadoHeladera.INACTIVA_POR_FALLA);

        Vianda vianda3 = new Vianda("carne", LocalDate.now(), LocalDate.now(),  "100", 300f, EnumEstadoVianda.ENTREGADO);
        Vianda vianda4 = new Vianda("papa", LocalDate.now(), LocalDate.now(),  "100", 300f, EnumEstadoVianda.ENTREGADO);

        vianda3.setHeladera(heladera);
        vianda4.setHeladera(heladera);
        List<Vianda> viandas2 = new ArrayList<Vianda>();
        viandas2.add(vianda3);
        viandas2.add(vianda4);

        heladera2.ingresarViandas(viandas2);

        Tarjeta t1 = new Tarjeta("abc");
        Vulnerable v1 = new Vulnerable(LocalDate.now(), EnumSituacionCalle.NO_POSEE_HOGAR, 0, new ArrayList<ViandaRecogida>(), t1, new ArrayList<UsoDeTarjeta>(), 4);
        Documento d1 = new Documento(0, "dni", "43");
        v1.setPersona(new PersonaFisica("tomas", null, null, d1, "cerezo", "m", "m", LocalDate.now()));
        v1.retirarVianda(0, heladera);

        Tarjeta t2 = new Tarjeta("abcd");
        Vulnerable v2 = new Vulnerable(LocalDate.now(), EnumSituacionCalle.NO_POSEE_HOGAR, 2, new ArrayList<ViandaRecogida>(), t2, new ArrayList<UsoDeTarjeta>(), 8);
        Documento d2 = new Documento(1, "dni", "44");
        v2.setPersona(new PersonaFisica("gabriela", null, null, d2, "varela", "f", "f", LocalDate.now()));
        v2.retirarVianda(0, heladera2);

        Tarjeta t3 = new Tarjeta("abcde");
        Vulnerable v3 = new Vulnerable(LocalDate.now(), EnumSituacionCalle.POSEE_HOGAR, 0, new ArrayList<ViandaRecogida>(), t3, new ArrayList<UsoDeTarjeta>(), 4);
        Documento d3 = new Documento(2, "dni", "45");
        v3.setPersona(new PersonaFisica("agustin", null, "MASA 38", d3, "bevilacua", "m", "t", LocalDate.now()));
        v3.retirarVianda(0, heladera);

        Tarjeta t4 = new Tarjeta("abcdef");
        Vulnerable v4 = new Vulnerable(LocalDate.now(), EnumSituacionCalle.NO_POSEE_HOGAR, 2, new ArrayList<ViandaRecogida>(), t2, new ArrayList<UsoDeTarjeta>(), 8);
        Documento d4 = new Documento(3, "dni", "46");
        v4.setPersona(new PersonaFisica("ana", null, null, d2, "dinardi", "f", "f", LocalDate.now()));
        v4.retirarVianda(0, heladera2);

        EntityManager em = BDUtils.getEntityManager();
        BDUtils.comenzarTransaccion(em);

        em.persist(direccion);
        em.persist(vianda);
        em.persist(vianda2);
        em.persist(heladera);

        BDUtils.commit(em);
    }

}