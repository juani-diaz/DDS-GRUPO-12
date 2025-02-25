package persistence.Repos;

import domain.heladera.Heladera;
import domain.incidente.Incidente;
import domain.vianda.Vianda;
import lombok.Getter;
import persistence.BDUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

//NO SE PERSISTE
public class RepoVianda extends BDUtils{
  @Getter
  private List<Vianda> viandas = new ArrayList<>();

  private static RepoVianda instance;

  public RepoVianda(){
    this.viandas = getAll_Viandas_BD();
  }

  public static RepoVianda getInstance(){
    if(instance == null){
      instance = new RepoVianda();
    }
    return instance;
  }

  private EntityManager em = getEm();

  public void add_Vianda(Vianda vian) {
    viandas.add(vian);

    comenzarTransaccion(em);
    try {
      System.out.println("add_Vianda: " + vian.getComida());
      em.persist(vian);
      commit(em);
    } catch (Exception e) {
      System.out.println("Error al agregar la Vianda: " + vian + e);
    }
  }

  public void updateVianda(Vianda v) {
    BDUtils.comenzarTransaccion(em);
    try {
      em.merge(v);
      BDUtils.commit(em);
    } catch (Exception e) {
      System.out.println("Error al actualizar vianda: " + e);
      BDUtils.rollback(em);
    }
  }

  public void remove_Vianda(Vianda vian) {
    viandas.remove(vian);

    comenzarTransaccion(em);
    try {
      em.remove(vian);
      commit(em);
    } catch (Exception e) {
      System.out.println("Error al remover la Vianda: " + vian + e);
    }
  }

  public Vianda findById_Vianda(Integer viandaID) {
    Vianda vianda = null;

    try {
      vianda = viandas.stream().filter(v -> v.getId() == viandaID).findFirst().get();
    } catch (Exception e) {
      System.out.println("Error al agregar la Vianda: " + e);
    }

    return vianda;
  }

  public List<Vianda> getAll_Viandas_BD() {
    CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
    CriteriaQuery<Vianda> criteriaQuery = criteriaBuilder.createQuery(Vianda.class);
    Root<Vianda> viandaRoot = criteriaQuery.from(Vianda.class);

    criteriaQuery.select(viandaRoot);

    Query query = em.createQuery(criteriaQuery);
    return query.getResultList();
  }

}
