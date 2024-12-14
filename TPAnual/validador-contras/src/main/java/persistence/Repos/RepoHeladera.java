package persistence.Repos;

import domain.heladera.Heladera;
import io.javalin.http.Context;
import lombok.Getter;
import persistence.BDUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepoHeladera extends BDUtils{
  @Getter
  private List<Heladera> heladeras = new ArrayList<>();

  private static RepoHeladera instance;

  private RepoHeladera(){
    this.heladeras = getHeladerass_BD();
  }

  public static RepoHeladera getInstance(){
    if(instance == null){
      instance = new RepoHeladera();
    }
    return instance;
  }

  //Se crea el EntityManager
  //private EntityManager em = BDUtils.getEntityManager();
  EntityManager em = getEm();

    public void add_Heladera(Heladera hela) {
    heladeras.add(hela);

    BDUtils.comenzarTransaccion(em);
    try {
      em.persist(hela);
      BDUtils.commit(em);
    } catch (Exception e) {
      System.out.println("Error al agregar la HELADERA: " + hela + e);
    }
  }

  public void remove_Heladera(Heladera hela) {
    heladeras.remove(hela);

    BDUtils.comenzarTransaccion(em);
    try {
      em.remove(hela);
      BDUtils.commit(em);
    } catch (Exception e) {
      System.out.println("Error al remover la HELADERA: " + hela + e);
    }
  }

  public Heladera findById_Heladera(Integer heladeraID) {
    Heladera heladera = null;

    try {
      heladera = heladeras.stream().filter(h -> h.getId() == heladeraID).findFirst().get();
    } catch (Exception e) {
      System.out.println("Error al agregar la heladera: " + e);
    }

    return heladera;
  }

  public List<Heladera> getHeladerass_BD() {
    CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
    CriteriaQuery<Heladera> criteriaQuery = criteriaBuilder.createQuery(Heladera.class);
    Root<Heladera> heladeraRoot = criteriaQuery.from(Heladera.class);

    criteriaQuery.select(heladeraRoot);

    Query query = em.createQuery(criteriaQuery);
    return query.getResultList();
  }

  public List<Map<String, Object>> obtenerFallasxHeladera() {
    System.out.println("Estoy en obtenerFallas");

    EntityManager em = BDUtils.getEntityManager();

    Query query = em.createQuery(
            "SELECT p.id, p.nombre, " +
                    "       COUNT(DISTINCT al) as totalErrores, " +
                    "       COUNT(DISTINCT rv) as totalRecogidas, " +
                    "       COUNT(DISTINCT rv) + COUNT(DISTINCT v) as totalViandas " +
                    "FROM Heladera p " +
                    "LEFT JOIN p.incidentesAlarma al " +
                    "LEFT JOIN p.viandasEnHeladera v " +
                    "LEFT JOIN v.viandaRecogida rv " +
                    "GROUP BY p.id, p.nombre"
    );


    System.out.println("Query creada: " + query);

    List<Object[]> heladeras = query.getResultList();

    List<Map<String, Object>> resultado = new ArrayList<>();
    for (Object[] fila : heladeras) {
      Map<String, Object> mapa = new HashMap<>();
      mapa.put("id", fila[0]);
      mapa.put("nombre", fila[1]);
      mapa.put("totalErrores", fila[2]);
      mapa.put("totalRecogidas", fila[3]);
      mapa.put("totalViandas", fila[4]);
      resultado.add(mapa);
    }

    return resultado;
  }

}
