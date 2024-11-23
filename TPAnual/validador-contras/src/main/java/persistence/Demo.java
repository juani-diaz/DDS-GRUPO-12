package persistence;

import domain.heladera.EnumEstadoHeladera;
import domain.heladera.Heladera;
import domain.heladera.Ubicacion;
import domain.persona.Persona;
import domain.rol.Colaborador;
import domain.vianda.EnumEstadoVianda;
import domain.vianda.Vianda;
import org.hibernate.type.LocalDateTimeType;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Demo {

    public static void main(String[] args) {

        Ubicacion direccion = new Ubicacion("BSAS", "1011", "200", "GUEMES" ,"4426");

        Heladera heladera = new Heladera("heladera2",direccion, 20, LocalDate.now(), 5f, 10.0f, EnumEstadoHeladera.INACTIVA_POR_FALLA);

        Vianda vianda = new Vianda("carne", LocalDate.now(), LocalDate.now(),  "100", 300f, EnumEstadoVianda.ENTREGADO);
        Vianda vianda2 = new Vianda("papa", LocalDate.now(), LocalDate.now(),  "100", 300f, EnumEstadoVianda.ENTREGADO);

        vianda.setHeladera(heladera);
        vianda2.setHeladera(heladera);
        List<Vianda> viandas = new ArrayList<Vianda>();
        viandas.add(vianda);
        viandas.add(vianda2);

        heladera.ingresarViandas(viandas);

        EntityManager em = BDUtils.getEntityManager();
        BDUtils.comenzarTransaccion(em);

        em.persist(direccion);
        em.persist(vianda);
        em.persist(vianda2);
        //em.persist(new Colaborador());
        em.persist(heladera);
        //em.persist(new Heladera("heladera4", direccion, 10, LocalDate.now(), viandas, 5f, 10.0f, EnumEstadoHeladera.INACTIVA_POR_FALLA));
        BDUtils.commit(em);
    }

}