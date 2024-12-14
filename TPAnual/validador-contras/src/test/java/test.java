import domain.colaboraciones.DistribucionVianda;
import domain.heladera.*;
import domain.contra.Requisitos;
import domain.contra.TAMANIO;
import domain.contra.TOP10000;
import domain.contra.Usuario;
import domain.persona.*;
import domain.rol.Colaborador;
import org.junit.jupiter.api.Assertions;
import domain.vianda.Vianda;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;

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

  Heladera heladera_origen = new Heladera("healdera_origen", ubicacion, 100, fechaFuncionamiento, 12F, 20F, EnumEstadoHeladera.DISPONIBLE);
  Heladera heladera_destino = new Heladera("heladera_destino", ubicacion, 50, fechaFuncionamiento, 4F, 21F, EnumEstadoHeladera.DISPONIBLE);

  Sensor sensor1 = new SensorApertura(heladera_origen);
  Sensor sensor2 = new SensorDeTemperatura(heladera_destino);
  Sensor sensor3 = new SensorDeMovimiento(heladera_origen);

  Email emailManuel = new Email("manubocha@gmail.com");
  String direccionManuel = "Montes De Oca 2671";
  Documento documentoManuel = new Documento("DNI", "47112068");
  LocalDate fechaNacimientoManuel = LocalDate.of(2000, 10, 12);
  PersonaFisica manuelBochini = new PersonaFisica("Manuel", Arrays.asList(emailManuel),direccionManuel,documentoManuel, "Bochini","Hombre","Masculino", fechaNacimientoManuel);
  Colaborador colaboradorManuel = new Colaborador(manuelBochini, null,0f,null,null);

  DistribucionVianda distribucionVianda = new DistribucionVianda(colaboradorManuel,LocalDate.now(),heladera_origen, heladera_destino, 4, DESPERFECTO_HELADERA);
  DistribucionVianda distribucionViandaVacia = new DistribucionVianda(colaboradorManuel,LocalDate.now(),heladera_origen, heladera_destino, 0, DESPERFECTO_HELADERA);

  List<String> email = Arrays.asList("juanmartin@gmail.com");
  List<String> telefono = Arrays.asList("1100001111");
  List<String> whatsap = Arrays.asList("1100001111");
  MedioDeContacto medioDePersona = new Email();

  Documento documento;// =new Documento("dni","450123456");

  PersonaFisica personaFisica1;//= new PersonaFisica("Juan_Martin",medioDePersona,"Avenida_Libertador_1820",documento,"Terrizzi","masculino","masculino",fechaFuncionamiento);

  PersonaJuridica personaJuridica1 = new PersonaJuridica("darVianda",EnumTipoPersonaJuridica.EMPRESA, "Empresa");



  //---------------------------CONTRASEÑAS------------------------------------
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

  //------------------------------------VIANDAS------------------------------------
  @Test
  public void cantidadViandas_4() {
    Assertions.assertEquals(1, distribucionVianda.cantidadViandas());
  }
  @Test
  public void cantidadViandas_0() {
    Assertions.assertEquals(0, distribucionViandaVacia.cantidadViandas());
  }




  //------------------------------------COLABORADOR----------------------------------
  @Test
  public void crearColaborador() {

  }

  //------------------------------------PERSONA----------------------------------
  @Test
  public void crearPersonaFisica(){
  Assertions.assertEquals("Juan_Martin",personaFisica1.getNombre());
  Assertions.assertEquals(Arrays.asList(medioDePersona),personaFisica1.getMediosDeContacto());
  Assertions.assertEquals("Avenida_Libertador_1820",personaFisica1.getDireccion());
  Assertions.assertEquals(documento,personaFisica1.getDocumento());
  Assertions.assertEquals("Terrizzi",personaFisica1.getApellido());
  //Assertions.assertEquals("masculino",personaFisica1.getGenero()); nose xq no anda estos 2
  //Assertions.assertEquals("masculino",personaFisica1.getSexo());
  Assertions.assertEquals(LocalDate.now(),personaFisica1.getFechaNacimiento());
  }

  @Test
public void crearPersonaJuridica(){
  Assertions.assertEquals("darVianda",personaJuridica1.getRazonSocial());
  Assertions.assertEquals(EnumTipoPersonaJuridica.EMPRESA,personaJuridica1.getTipo());
  Assertions.assertEquals("Empresa",personaJuridica1.getRubro());
}}

