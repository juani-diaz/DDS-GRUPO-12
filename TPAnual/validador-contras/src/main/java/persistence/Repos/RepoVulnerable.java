package persistence.Repos;

import domain.rol.Vulnerable;
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
public class RepoVulnerable {
  @Getter
  private List<Vulnerable> vulnerables = new ArrayList<>();

  private static RepoVulnerable instance;

  private RepoVulnerable(){
    this.vulnerables = getAll_Vulnerables_BD();
  }

  public static RepoVulnerable getInstance(){
    if(instance == null){
      instance = new RepoVulnerable();
    }
    return instance;
  }

  //Se crea el EntityManager
  private EntityManager em = BDUtils.getEntityManager();

  public void add_Vulnerable(Vulnerable vulne) {
    BDUtils.comenzarTransaccion(em);
    try {
      em.persist(vulne);
      BDUtils.commit(em);
    } catch (Exception e) {
      System.out.println("Error al agregar la Vulnerable: " + vulne + e);
    }
  }

  public void remove_Vulnerable(Vulnerable vulne) {
    BDUtils.comenzarTransaccion(em);
    try {
      em.remove(vulne);
      BDUtils.commit(em);
    } catch (Exception e) {
      System.out.println("Error al remover la Vulnerable: " + vulne + e);
    }
  }

  public Vulnerable findById_Vulnerable(Integer vulnerableID) {

    Vulnerable vulnerable = null;

    try {
      vulnerable = this.em.getReference(Vulnerable.class, vulnerableID);
    } catch (Exception e) {
      System.out.println("Error al agregar la Vulnerable: " + e + " en VulnerableController.obtenerVulnerable");
    }

    return vulnerable;
  }

  public List<Vulnerable> getAll_Vulnerables_BD() {
    CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
    CriteriaQuery<Vulnerable> criteriaQuery = criteriaBuilder.createQuery(Vulnerable.class);
    Root<Vulnerable> vulnerableRoot = criteriaQuery.from(Vulnerable.class);

    criteriaQuery.select(vulnerableRoot);

    Query query = em.createQuery(criteriaQuery);
    return query.getResultList();
  }
}
