import domain.colaboraciones.*;
import domain.heladera.Heladera;
import domain.heladera.Ubicacion;
import domain.rol.Colaborador;
import domain.servicios.Catalogo;
import domain.vianda.Vianda;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static domain.heladera.EnumEstadoHeladera.DISPONIBLE;

public class test_colaboraciones {

    Colaborador colaborador1 = new Colaborador();
    Vianda viandaDonada= new Vianda();
    Heladera heladeraADonar= new Heladera();
    List<Vianda> viandaEsperada= Arrays.asList(viandaDonada);
    Heladera heladera2 = new Heladera("ss", new Ubicacion(), 2, LocalDate.now(), 0F, 0F, DISPONIBLE);

    //DistribucionVianda donacionVianda1 = new DistribucionVianda(colaborador1, LocalDate.now(), heladera2, heladeraADonar, List.of(0), DESPERFECTO_HELADERA);

    DonacionVianda aDonarVianda = new DonacionVianda(colaborador1,LocalDate.now(),viandaDonada,heladeraADonar);
    ResponsableHeladera sereResponsable = new ResponsableHeladera(colaborador1,LocalDate.now(),heladera2);
    PresentacionOferta ofertaPresentada = new PresentacionOferta(colaborador1,LocalDate.now(),"empresa","Playmovil","una oferta",150F,"IMAGEN");
    Catalogo catalogo1 = Catalogo.getInstance();
    @Test
    public void crearColaboracionDonacion() {
        DonacionDinero donacion1 = new DonacionDinero(colaborador1, LocalDate.now(), 100F,null);
        List<Colaboracion> colaboraciones= new ArrayList<>();
        colaboraciones.add(donacion1);
        colaborador1.setColaboraciones(colaboraciones);
        colaborador1.realizarColaboracion(donacion1);
        List<Colaboracion> listaColaboracionEsperada = Arrays.asList(donacion1);
        Assertions.assertEquals(listaColaboracionEsperada.get(0), colaborador1.getColaboraciones().get(0));
    }

    @Test
    public void donarUnaViandurria(){
        aDonarVianda.ejecutar();
        Assertions.assertEquals(viandaEsperada,heladeraADonar.getViandasEnHeladera());
    }

    @Test
    public void distribucionVianda(){
        DonacionDinero donacion1 = new DonacionDinero(colaborador1, LocalDate.now(), 100F,null);
        List<Colaboracion> colaboraciones= new ArrayList<>();
        colaboraciones.add(donacion1);
        colaborador1.setColaboraciones(colaboraciones);
        colaborador1.realizarColaboracion(donacion1);
        //donacionVianda1.ejecutar();
        Assertions.assertEquals(viandaEsperada, heladeraADonar.getViandasEnHeladera());
    }

    @Test
    public void testPuntosDonacionDinero(){
        DonacionDinero donacion2 = new DonacionDinero(colaborador1, LocalDate.now(), 200F,null);
        Float multiplicador = 0.5F;
        Assertions.assertEquals(100, donacion2.puntosObtenidos());


    }
    /*@Test
    public void responsableHeladera(){
        sereResponsable.ejecutar();
        Assertions.assertEquals(listaDeViandas, heladera2.get());
    }*/
    /*public void presentarOferta(){ CADA EMPRESA TIENE Q TENER SU CATALOGO

    }*/
}