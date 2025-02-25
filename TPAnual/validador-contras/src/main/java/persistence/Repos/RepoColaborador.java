package persistence.Repos;

import domain.auth.Usuario;
import domain.heladera.Heladera;
import domain.rol.Colaborador;
import domain.vianda.Vianda;
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

public class RepoColaborador extends BDUtils{
    @Getter
    private List<Colaborador> colaboradores = new ArrayList<>();

    private static RepoColaborador instance;

    private RepoColaborador(){
        this.colaboradores = getAll_Colaboradores_BD();
    }

    public static RepoColaborador getInstance(){
        if(instance == null){
            instance = new RepoColaborador();
        }
        return instance;
    }

    public void add_Colaborador(Colaborador colaborador) {
        this.colaboradores.add(colaborador);

        comenzarTransaccion(em);

        try {
            if(colaborador.getPersona().getDocumento()!=null) {
                em.persist(colaborador.getPersona().getDocumento());
            }
            em.persist(colaborador.getPersona());
            em.persist(colaborador);

            commit(em);
        } catch (Exception e) {
            System.out.println("Error al agregar el USUARIO: " + colaborador + e);
        }

    }

    //Se crea el EntityManager
    EntityManager em = getEm();

    public List<Map<String, Object>> obtenerDonacionesxColaborador() {
        System.out.println("Estoy en obtenerDonaciones");



        Query query1 = this.em.createQuery(
                "SELECT p.id, p.nombre, p.apellido FROM PersonaFisica p  WHERE p.id IN(SELECT p.id "  +
                                                "FROM DonacionVianda d "  +
                                                "JOIN d.colaborador c "+
                                                "JOIN c.persona p  )"
        );

        Query query2 = this.em.createQuery("SELECT p.id, COUNT (DISTINCT d) FROM DonacionVianda d JOIN d.colaborador c JOIN c.persona p GROUP BY p");


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

    public List<Map<String, Object>> obtenerDonacionesxColaboradorxSemana(String semana) {
        System.out.println("Estoy en obtenerDonacionesxSemana");



        Query query1 = this.em.createQuery(
                "SELECT p.id, p.nombre, p.apellido FROM PersonaFisica p  WHERE p.id IN(SELECT p.id "  +
                        "FROM DonacionVianda d "  +
                        "JOIN d.colaborador c "+
                        "JOIN c.persona p  " +
                        "WHERE d.fecha BETWEEN " +
                        semana +
                        " )"

        );

        Query query2 = this.em.createQuery("SELECT p.id, COUNT (DISTINCT d) FROM DonacionVianda d JOIN d.colaborador c JOIN c.persona p WHERE d.fecha BETWEEN "+
                semana+
                " GROUP BY p");


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

    public Colaborador findById_Colaborador(Integer colaboradorId){
        Colaborador colaborador=null;

        try {
            colaborador = colaboradores.stream().filter(c -> c.getId() == colaboradorId).findFirst().get();
        } catch (Exception e) {
            System.out.println("Error al obtener el colaborador: " + e);
        }

        return colaborador;
    }

    public Float obtenerPuntosxColaborador(Integer colaboradorId){

        System.out.println("Estoy en obtenerPuntos");

        Colaborador colaborador = this.findById_Colaborador(colaboradorId);

        System.out.println("puntos");

        System.out.println(colaborador.getColaboraciones());

        return colaborador.getCantidadPuntos();

    }

    public List<Colaborador> getAll_Colaboradores_BD() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Colaborador> criteriaQuery = criteriaBuilder.createQuery(Colaborador.class);
        Root<Colaborador> colaboradorRoot = criteriaQuery.from(Colaborador.class);
        criteriaQuery.select(colaboradorRoot);
        Query query = em.createQuery(criteriaQuery);
        return query.getResultList();
    }

    public Colaborador findByUsuario(String usuario){
        RepoUsuarios ru = RepoUsuarios.getInstance();
        Usuario u = ru.findByUsuario(usuario);
        if(u != null && u.getRol().getClass() == Colaborador.class){
            return (Colaborador) u.getRol();
        }
        return null;
    }

    public void actualizarColaborador(Colaborador colaborador){
        comenzarTransaccion(em);

        em.merge(colaborador);

        commit(em);
    }
    public void byUsuarioSubscribeHela(String usuario){

    }
}
