package persistence.Repos;

import domain.heladera.Heladera;
import persistence.BDUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

//NO SE PERSISTE
public class RepoHeladera{

  //Se crea el EntityManager
  private EntityManager em = BDUtils.getEntityManager();

  public void add_Heladera(Heladera hela) {
    BDUtils.comenzarTransaccion(em);
    try {
      em.persist(hela);
      BDUtils.commit(em);
    } catch (Exception e) {
      System.out.println("Error al agregar la HELADERA: " + hela + e);
    }
  }

  public void remove_Heladera(Heladera hela) {
    BDUtils.comenzarTransaccion(em);
    try {
      em.remove(hela);
      BDUtils.commit(em);
    } catch (Exception e) {
      System.out.println("Error al remover la HELADERA: " + hela + e);
    }
  }

  public Heladera findById_Heladera(Integer heladeraID) {
    //BDUtils.comenzarTransaccion(this.em);

    Heladera heladera = null;

    try {
      heladera = this.em.getReference(Heladera.class, heladeraID);
    } catch (Exception e) {
      System.out.println("Error al agregar la heladera: " + e + " en HeladeraController.obtenerHeladera");
    }

    return heladera;
  }

  public List<Heladera> getAll_Heladera() {
    CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
    CriteriaQuery<Heladera> criteriaQuery = criteriaBuilder.createQuery(Heladera.class);
    Root<Heladera> heladeraRoot = criteriaQuery.from(Heladera.class);

    criteriaQuery.select(heladeraRoot);

    Query query = em.createQuery(criteriaQuery);
    return query.getResultList();
  }
}
