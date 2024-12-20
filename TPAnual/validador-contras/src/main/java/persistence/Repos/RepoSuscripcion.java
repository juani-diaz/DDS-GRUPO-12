package persistence.Repos;

import domain.suscripcion.PocasViandas;
import domain.suscripcion.Suscripcion;
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
public class RepoSuscripcion extends BDUtils{
  @Getter
  private List<Suscripcion> suscripcion = new ArrayList<>();

  private static RepoSuscripcion instance;

  private RepoSuscripcion(){
    this.suscripcion = getAll_Suscripcions_BD();
  }

  public static RepoSuscripcion getInstance(){
    if(instance == null){
      instance = new RepoSuscripcion();
    }
    return instance;
  }

  //Se crea el EntityManager
  //private EntityManager em = BDUtils.getEntityManager();
  private EntityManager em = getEm();

  public void add_Suscripcion(Suscripcion suscrip) {
    suscripcion.add(suscrip);

    comenzarTransaccion(em);
    try {
      System.out.println("add_Suscripcion: " + suscrip.getId());
      em.persist(suscrip);
      commit(em);
    } catch (Exception e) {
      System.out.println("Error al agregar la Suscripcion: " + suscrip + e);
    }
  }

  public void remove_Suscripcion(Suscripcion suscrip) {
    suscripcion.remove(suscrip);

    comenzarTransaccion(em);
    try {
      em.remove(suscrip);
      commit(em);
    } catch (Exception e) {
      System.out.println("Error al remover la Suscripcion: " + suscrip + e);
    }
  }

  public Suscripcion findById_Suscripcion(Integer suscripboracionID) {
    Suscripcion suscripboracion = null;

    try {
      suscripboracion = suscripcion.stream().filter(c -> c.getId() == suscripboracionID).findFirst().get();
    } catch (Exception e) {
      System.out.println("Error al agregar la Suscripcion: " + e);
    }

    return suscripboracion;
  }

  public List<Suscripcion> getAll_Suscripcions_BD() {
    CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
    CriteriaQuery<Suscripcion> criteriaQuery = criteriaBuilder.createQuery(Suscripcion.class);
    Root<Suscripcion> suscripboracionRoot = criteriaQuery.from(Suscripcion.class);

    criteriaQuery.select(suscripboracionRoot);

    Query query = em.createQuery(criteriaQuery);
    return query.getResultList();
  }


}
