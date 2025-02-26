package domain.servicios;

import domain.colaboraciones.PresentacionOferta;
import domain.rol.Colaborador;
import lombok.Getter;
import persistence.EntidadPersistente;
import persistence.Repos.RepoColaborador;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

import static persistence.BDUtils.*;

public class Catalogo extends EntidadPersistente {

    @Getter
    private List<PresentacionOferta> ofertas = new ArrayList<>();

    EntityManager em = getEm();

    private static Catalogo instance;

    private Catalogo(){
        ofertas = getAllOfertas_BD();
    }

    public static Catalogo getInstance(){
        if(instance == null){
            instance = new Catalogo();
        }
        return instance;
    }

    private List<PresentacionOferta> getAllOfertas_BD(){
        CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
        CriteriaQuery<PresentacionOferta> criteriaQuery = criteriaBuilder.createQuery(PresentacionOferta.class);
        Root<PresentacionOferta> ofertaRoot = criteriaQuery.from(PresentacionOferta.class);
        criteriaQuery.select(ofertaRoot);
        Query query = this.em.createQuery(criteriaQuery);
        return query.getResultList();
    }

    public void agregarAlCatalogo(PresentacionOferta p){
        ofertas.add(p);

        comenzarTransaccion(em);

        try {
            em.persist(p);
            commit(em);
        } catch (Exception e) {
            System.out.println("Error al agregar la OFERTA: " + p);
        }
    }

    public PresentacionOferta getOfertaById(int id){
        return ofertas.stream().filter(o -> o.getId() == id).findFirst().get();
    }

    public void retirarDelCatalogoPorId(int id){
        PresentacionOferta p = ofertas.stream().filter(o -> o.getId() == id).findFirst().get();
        retirarDelCatalogo(p);
    }

    public void retirarDelCatalogo(PresentacionOferta p){
        comenzarTransaccion(em);
        em.remove(p);
        commit(em);

        ofertas.remove(p);
    }

    public boolean otorgar(PresentacionOferta o, Colaborador colaborador){
        if(o.getPuntosNecesarios() <= colaborador.getCantidadPuntos()){
            //TODO hacer lo que corresponda (mail, envio)
            colaborador.setPuntosGastados(colaborador.getPuntosGastados() + o.getPuntosNecesarios());
            return true;
        }
        return false;
    }
}
