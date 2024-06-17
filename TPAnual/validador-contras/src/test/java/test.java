import domain.colaboraciones.DistribucionVianda;
import domain.colaboraciones.EnumMotivosMovimientoVianda;
import domain.heladera.Heladera;
import domain.heladera.Sensor;
import domain.heladera.Ubicacion;
import domain.persona_contra.Requisitos;
import domain.persona_contra.TAMANIO;
import domain.persona_contra.TOP10000;
import domain.persona_contra.Usuario;
import org.junit.jupiter.api.Assertions;
import domain.vianda.Vianda;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static domain.colaboraciones.EnumMotivosMovimientoVianda.DESPERFECTO_HELADERA;

public class test {

  //ELEMENTOS PARA TEST DE CONTRASEÑA
  TAMANIO tam = new TAMANIO();
  TOP10000 top = new TOP10000();
  List<Requisitos> chequeos = new ArrayList<>();



  Ubicacion ubicacion = new Ubicacion();
  LocalDate fechaFuncionamiento = LocalDate.now();

  Vianda vianda1 = new Vianda();
  Vianda vianda2 = new Vianda();
  Vianda vianda3 = new Vianda();
  Vianda vianda4 = new Vianda();
  List<Vianda> listaVianda = Arrays.asList(vianda1,vianda2,vianda3,vianda4);

  Vianda vianda5 = new Vianda();
  Vianda vianda6 = new Vianda();
  List<Vianda> listaVianda2 = Arrays.asList(vianda5,vianda6);

  Sensor sensor1 = new Sensor();
  Sensor sensor2 = new Sensor();
  Sensor sensor3 = new Sensor();
  List<Sensor> listaSensor = Arrays.asList(sensor1,sensor2,sensor3);
  List<Sensor> listaSensor2 = Arrays.asList(sensor1,sensor2);

  Heladera heladera_origen = new Heladera("healdera_origen", ubicacion, 100, fechaFuncionamiento, listaVianda, 12F, 20F, listaSensor);
  Heladera heladera_destino = new Heladera("heladera_destino", ubicacion, 50, fechaFuncionamiento, listaVianda, 4F, 21F, listaSensor2);

  DistribucionVianda distribucionVianda = new DistribucionVianda(heladera_origen, heladera_destino, listaVianda, DESPERFECTO_HELADERA, 3F);
  DistribucionVianda distribucionViandaVacia = new DistribucionVianda(heladera_origen, heladera_destino, new ArrayList<>(), DESPERFECTO_HELADERA, 3F);

  @Test
  public void noSeCreaPorTamañoChicoContraseña() { // No se crea por ser demasiado corta (8 caracteres)
    Usuario usuario = new Usuario();

    chequeos.add(tam);
    usuario.crearUsuario("123", "G19", chequeos);
    Assertions.assertNotEquals("123", usuario.getContraseña());
  }

  @Test
  public void noSeCreaPorTamañoGrandeContraseña() { // No se crea al tener más de 63 caracteres
    Usuario usuario = new Usuario();
    chequeos.add(tam);

    usuario.crearUsuario("senfosler6.s0r,elr3@teh*teOy()65senfosler6.s0r,elr3@teh*teOy()65", "G19", chequeos);
    Assertions.assertNotEquals("senfosler6.s0r,elr3@teh*teOy()65senfosler6.s0r,elr3@teh*teOy()65", usuario.getContraseña());
  }

  @Test
  public void noSeCreaPortop10000Contraseña() { // No se crea por ser muy insegura
    Usuario usuario = new Usuario();
    chequeos.add(top);
    usuario.crearUsuario("123456789", "G19", chequeos);
    Assertions.assertNotEquals("123456789", usuario.getContraseña());
  }

  @Test
  public void seCreaConCaracteresUnicode() {
    Usuario usuario = new Usuario();
    usuario.crearUsuario("℘⇴϶∀2024", "G19", chequeos);
    Assertions.assertEquals("℘⇴϶∀2024", usuario.getContraseña());
  }

  @Test
  public void seCreaContraseña() { // Se crea al estar entre 8 y 64 y ser segura
    Usuario usuario = new Usuario();
    chequeos.add(tam);
    chequeos.add(top);
    usuario.crearUsuario("djkasbjkdbsakkdkasb", "G19", chequeos);
    Assertions.assertEquals("djkasbjkdbsakkdkasb", usuario.getContraseña());
  }

  @Test
  public void cantidadViandas_4() {
    Assertions.assertEquals(4, distribucionVianda.cantidadViandas());
  }
  @Test
  public void cantidadViandas_0() {
    Assertions.assertEquals(0, distribucionViandaVacia.cantidadViandas());
  }

  //----------------HELADERA--------------------------------------------------------------------------------------------
  @Test
  public void ingresarVianda() {
    int canViandas = heladera_destino.getViandasEnHeladeraList().size();
    heladera_destino.ingresarViandas(listaVianda2);
    System.out.println(listaVianda2.size());
    Assertions.assertEquals(canViandas + listaVianda2.size(), heladera_destino.getViandasEnHeladeraList().size());
  }

  @Test
  public void sacarViandas() {
    Assertions.assertEquals(0, distribucionViandaVacia.cantidadViandas());
  }







}
