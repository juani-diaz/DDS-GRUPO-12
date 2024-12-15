package persistence.Repos;

import domain.heladera.Heladera;
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

  private RepoVianda(){
    this.viandas = getAll_Viandas_BD();
  }

  public static RepoVianda getInstance(){
    if(instance == null){
      instance = new RepoVianda();
    }
    return instance;
  }

  //Se crea el EntityManager
  //private EntityManager em = BDUtils.getEntityManager();
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

  public void cambiarHeladera(Heladera heladeraNueva, Integer viandaID){

    Vianda vianda = this.findById_Vianda(viandaID);
    vianda.setHeladera(heladeraNueva);

    comenzarTransaccion(em);

    // Merging the updated entity
    em.merge(vianda);

    commit(em);
  }

  public void cambiarHeladeraPlural(Heladera heladeraNueva, List<Vianda> viandas){
    Integer i=0;

    for (i=0 ; i<viandas.size(); i++)
    {
      Vianda vianda = viandas.get(i);
      vianda.setHeladera(heladeraNueva);

      comenzarTransaccion(em);

      // Merging the updated entity
      em.merge(vianda);

      commit(em);
    }

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
