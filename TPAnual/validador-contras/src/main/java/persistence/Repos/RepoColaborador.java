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

        Query query2 = em.createQuery("SELECT p.id, COUNT (DISTINCT d) FROM DonacionVianda d JOIN d.colaborador c JOIN c.persona p GROUP BY p");


        System.out.println("Query creada: " + query1);
        System.out.println("Query creada: " + query2);

        List<Object[]> personasFisicas = query1.getResultList();
        List<Object[]> donaciones = query2.getResultList();


        System.out.println(personasFisicas);
        System.out.println(donaciones);

        List<Map<String, Object>> resultado = new ArrayList<>();
        for (Object[] fila1 : personasFisicas) {
            Map<String, Object> mapa = new HashMap<>();


            for (Object[] fila2:donaciones){
                if(fila1[0] == fila2[0]){ //comparo ids y si son iguales los agrego al mapa para devolverlo
            mapa.put("nombre", fila1[1]);
            mapa.put("apellido", fila1[2]);
            mapa.put("donacion",fila2[1]);
            resultado.add(mapa);}
        }
        }



        return resultado;
    }
}
