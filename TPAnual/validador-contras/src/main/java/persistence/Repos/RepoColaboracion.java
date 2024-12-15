package persistence.Repos;

import domain.colaboraciones.Colaboracion;
import domain.colaboraciones.DonacionVianda;
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
public class RepoColaboracion extends BDUtils{
  @Getter
  private List<Colaboracion> colaboraciones = new ArrayList<>();

  private static RepoColaboracion instance;

  private RepoColaboracion(){
    this.colaboraciones = getAll_Colaboracions_BD();
  }

  public static RepoColaboracion getInstance(){
    if(instance == null){
      instance = new RepoColaboracion();
    }
    return instance;
  }

  //Se crea el EntityManager
  //private EntityManager em = BDUtils.getEntityManager();
  private EntityManager em = getEm();

  public void add_Colaboracion(Colaboracion cola) {
    colaboraciones.add(cola);

    comenzarTransaccion(em);
    try {
      System.out.println("add_Colaboracion: " + cola.getId());
      em.persist(cola);
      commit(em);
    } catch (Exception e) {
      System.out.println("Error al agregar la Colaboracion: " + cola + e);
    }
  }
  public void add_DonaVianda(DonacionVianda donacionVianda) {
    colaboraciones.add(donacionVianda);

    comenzarTransaccion(em);
    try {
      System.out.println("add_Colaboracion: " + donacionVianda.getId());
      em.persist(donacionVianda);
      commit(em);
    } catch (Exception e) {
      System.out.println("Error al agregar la Colaboracion: " + donacionVianda + e);
    }
  }

  public void remove_Colaboracion(Colaboracion cola) {
    colaboraciones.remove(cola);

    comenzarTransaccion(em);
    try {
      em.remove(cola);
      commit(em);
    } catch (Exception e) {
      System.out.println("Error al remover la Colaboracion: " + cola + e);
    }
  }

  public Colaboracion findById_Colaboracion(Integer colaboracionID) {
    Colaboracion colaboracion = null;

    try {
      colaboracion = colaboraciones.stream().filter(c -> c.getId() == colaboracionID).findFirst().get();
    } catch (Exception e) {
      System.out.println("Error al agregar la Colaboracion: " + e);
    }

    return colaboracion;
  }

  public List<Colaboracion> getAll_Colaboracions_BD() {
    CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
    CriteriaQuery<Colaboracion> criteriaQuery = criteriaBuilder.createQuery(Colaboracion.class);
    Root<Colaboracion> colaboracionRoot = criteriaQuery.from(Colaboracion.class);

    criteriaQuery.select(colaboracionRoot);

    Query query = em.createQuery(criteriaQuery);
    return query.getResultList();
  }


}
