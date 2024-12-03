package domain.heladera;

import persistence.BDUtils;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
//NO SE PERSISTE
public class RepoHeladera {

  //Se crea el EntityManager
  private EntityManager em = BDUtils.getEntityManager();

  private static long secuencia = 0;

  private long nextId() {
    secuencia = secuencia + 1;
    return secuencia ;
  }

  private Collection<Heladera> heladeras;

  public void add(Heladera hela) {
    try {
      em.persist(hela);
    } catch (Exception e) {
      System.out.println("Error al agregar la vianda: " + hela + e);
    }
  }

  //public void remove(Heladera prod) {
  //  this.heladeras = this.heladeras.stream().filter(
  //      x -> !x.getId().equals(prod.getId())).toList();
  //}

  //public boolean exists(Long id) {
  //  return this.heladeras.stream().anyMatch(
  //      x -> !x.getId().equals(id));
  //}

  public Heladera findById(Long heladeraID_Long) {
    EntityManager em = BDUtils.getEntityManager();
    BDUtils.comenzarTransaccion(em);

    Heladera heladera = null;
    System.out.println("ESTOY EN RERPOHELADERA");
    try {
      heladera = em.getReference(Heladera.class, heladeraID_Long);
    } catch (Exception e) {
      System.out.println("Error al agregar la heladera: " + e + " en HeladeraController.obtenerHeladera");
    }
    System.out.println("La heladera es: "+heladera);
    System.out.println("La heladera es: "+heladera.getNombre());
    return heladera;
  }

  //public Collection<Heladera> all() {
  //  return this.heladeras;
  //}
}
