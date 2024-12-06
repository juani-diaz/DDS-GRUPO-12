package persistence.Repos;

import domain.heladera.Heladera;
import domain.vianda.Vianda;
import persistence.BDUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

//NO SE PERSISTE
public class RepoVianda {

  //Se crea el EntityManager
  private EntityManager em = BDUtils.getEntityManager();

  public void add_Vianda(Vianda vian) {
    BDUtils.comenzarTransaccion(em);
    try {
      System.out.println("add_Vianda: " + vian.getComida());
      em.persist(vian);
      BDUtils.commit(em);
    } catch (Exception e) {
      System.out.println("Error al agregar la Vianda: " + vian + e);
    }
  }

  public void remove_Vianda(Vianda vian) {
    BDUtils.comenzarTransaccion(em);
    try {
      em.remove(vian);
      BDUtils.commit(em);
    } catch (Exception e) {
      System.out.println("Error al remover la Vianda: " + vian + e);
    }
  }

  public Vianda findById_Vianda(Integer viandaID) {

    Vianda vianda = null;

    try {
      vianda = this.em.getReference(Vianda.class, viandaID);
    } catch (Exception e) {
      System.out.println("Error al agregar la Vianda: " + e + " en ViandaController.obtenerVianda");
    }

    return vianda;
  }

  public List<Vianda> getAll_Vianda() {
    CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
    CriteriaQuery<Vianda> criteriaQuery = criteriaBuilder.createQuery(Vianda.class);
    Root<Vianda> viandaRoot = criteriaQuery.from(Vianda.class);

    criteriaQuery.select(viandaRoot);

    Query query = em.createQuery(criteriaQuery);
    return query.getResultList();
  }
}
