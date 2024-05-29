import domain.persona_contra.Requisitos;
import domain.persona_contra.TAMANIO;
import domain.persona_contra.TOP10000;
import domain.persona_contra.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


public class test {

  TAMANIO tam = new TAMANIO();
  TOP10000 top = new TOP10000();
  List<Requisitos> chequeos = new ArrayList<>();

  @Test
  public void noSeCreaPorTamañoContraseña() {
    Usuario usuario = new Usuario();

    chequeos.add(tam);
    usuario.crearUsuario("123", "G19", chequeos);
    Assertions.assertNotEquals("123", usuario.getContraseña());
  }

  @Test
  public void seCreaContraseña() {
    Usuario usuario = new Usuario();
    chequeos.add(tam);
    chequeos.add(top);
    usuario.crearUsuario("djkasbjkdbsakkdkasb", "G19", chequeos);
    Assertions.assertEquals("djkasbjkdbsakkdkasb", usuario.getContraseña());
  }

  @Test
  public void noSeCreaPortop10000Contraseña() {
    Usuario usuario = new Usuario();
    chequeos.add(top);
    usuario.crearUsuario("123456789", "G19", chequeos);
    Assertions.assertNotEquals("123456789", usuario.getContraseña());
  }
}
