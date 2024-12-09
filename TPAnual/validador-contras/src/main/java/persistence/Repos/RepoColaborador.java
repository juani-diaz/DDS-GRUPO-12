package persistence.Repos;

import persistence.BDUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepoColaborador {



    public List<Map<String, Object>> obtenerDonacionesxColaborador() {
        System.out.println("Estoy en obtenerDonaciones");

        EntityManager em = BDUtils.getEntityManager();


        Query query1 = em.createQuery(
                "SELECT p.id, p.nombre, p.apellido FROM PersonaFisica p  WHERE p.id IN(SELECT p.id "  +
                                                "FROM DonacionVianda d "  +
                                                "JOIN d.colaborador c "+
                                                "JOIN c.persona p  )"
        );

        Query query2 = em.createQuery("SELECT p.id, COUNT (DISTINCT d) FROM DonacionVianda d JOIN d.colaborador c JOIN ersona pGROUP BY p")


        System.out.println("Query creada: " + query2);

        List<Object[]> heladeras = query2.getResultList();

        List<Map<String, Object>> resultado = new ArrayList<>();
        for (Object[] fila : heladeras) {
            Map<String, Object> mapa = new HashMap<>();
            mapa.put("id", fila[0]);
            mapa.put("nombre", fila[1]);
            mapa.put("apellido", fila[2]);
            resultado.add(mapa);
        }

        return resultado;
    }
}
