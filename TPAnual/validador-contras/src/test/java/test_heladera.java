import domain.heladera.*;
import org.junit.jupiter.api.Assertions;
import domain.vianda.Vianda;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;

public class test_heladera {
    
    Vianda vianda1 = new Vianda();
    Vianda vianda2 = new Vianda();
    Vianda vianda3 = new Vianda();
    Vianda vianda4 = new Vianda();
    List<Vianda> listaVianda2 = Arrays.asList(vianda4);
    Ubicacion ubicacion = new Ubicacion();
    LocalDate fechaFuncionamiento = LocalDate.now();
    List<Vianda> listaVianda = Arrays.asList(vianda1,vianda2,vianda3);
    List<Vianda> listaViandaEsperada1 = Arrays.asList(vianda1,vianda2,vianda3,vianda4);
    List<Vianda> listaViandaEsperada2 = Arrays.asList(vianda1,vianda3);
    Heladera heladera1 = new Heladera("heladera1",ubicacion, 100, fechaFuncionamiento,listaVianda,12F,20F, EnumEstadoHeladera.DISPONIBLE);

    Sensor sensor = new SensorDeTemperatura(heladera1);

    @Test
    public void agregarVianda() {
        heladera1.ingresarViandas(listaVianda2);
        Assertions.assertEquals(listaViandaEsperada1, heladera1.getViandasEnHeladera());
    }
    @Test
    public void sacarVianda() {
        heladera1.sacarVianda(1);

        Assertions.assertEquals(listaViandaEsperada2, heladera1.getViandasEnHeladera());
    }
    @Test
    public void elSensorConoceLaHeladera(){
        Assertions.assertEquals(heladera1,sensor.getHeladeraAsignada());
    }
}
