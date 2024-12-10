package views;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import persistence.BDUtils;
import persistence.Repos.RepoColaborador;
import persistence.Repos.RepoHeladera;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UI_Reporte implements Handler {


    @Override
    public void handle(Context ctx) throws Exception {
        RepoHeladera hela = new RepoHeladera();
        RepoColaborador cola = new RepoColaborador();

        Map<String, Object> model = new HashMap<>();
        model.put("helaFallos", hela.obtenerFallasxHeladera());
        model.put("colaboradorDatos", cola.obtenerDonacionesxColaborador());

        System.out.println(model);
        ctx.render("reportes.hbs", model);
    }





    public void obtener(Context ctx) {

        System.out.println("Estoy en obtenerFallas");

        EntityManager em = BDUtils.getEntityManager();

        try {
            // Crear y ejecutar la consulta
            Query query = em.createQuery(
                    "SELECT p.id, p.estado, p.fechaFuncionamiento, '' as direccion, p.nombre, p.tamanioEnViandas, p.temperaturaMaxima, p.temperaturaMinima " +
                            "FROM IncidenteAlarma al " +
                            "JOIN al.heladera p"
            );
            System.out.println("Query creada: " + query);


            // Obtener resultados
            List heladeras = query.getResultList();

            System.out.println(heladeras);

            // Procesar resultados
//            for (Heladera heladera : heladeras) {
//                System.out.println("Heladera: " + heladera);
//            }

            // Respuesta al cliente
            ctx.json(heladeras);
        } catch (Exception e) {
            // Manejo de excepciones
            e.printStackTrace();
            ctx.status(500).result("Error al obtener fallas de heladeras");
        } finally {
            em.close();
        }
    }



}






