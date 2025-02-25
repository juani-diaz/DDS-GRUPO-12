package domain.suscripcion;

import domain.vianda.Vianda;
import lombok.Getter;
import lombok.Setter;
import persistence.Repos.RepoVianda;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static persistence.BDUtils.getEm;


@Getter @Setter
public class Publicador  {

    private static MuchasViandas via;
    private static NoFunciona nf;
    private static PocasViandas pv;
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<Suscripcion> observerSuscripcion = new ArrayList<>();

    public Publicador(){
        this.observerSuscripcion = getAll_Suscripciones_BD();
    }

    public void addObservable(Suscripcion observable){

        observerSuscripcion.add(observable);
    }

    // Método que notifica a todos los suscriptores
    public void notifyObservers() throws IOException {
        for (Suscripcion observer : observerSuscripcion) {

            System.out.println(observer.getClass().getName());

            RepoVianda rv=RepoVianda.getInstance();
            List<Vianda> viandas=rv.getAll_Viandas_BD();
            List<Vianda> viandasFiltradas= (List<Vianda>) viandas.stream().filter(vianda -> vianda.getHeladera().getId()==observer.getHeladera().getId()).toList();

            switch(observer.getClass().getName()) {
                case "domain.suscripcion.NoFunciona": observer.notificar();
                case "domain.suscripcion.PocasViandas": if( observer.condicion(viandasFiltradas.size()))observer.notificar();
                case "domain.suscripcion.MuchasViandas":   if(observer.condicion(viandasFiltradas.size())) observer.notificar() ;
            }
        }
    }

//    public void checkConditionAndNotify(boolean condicion) {
//        if (condicion) {
//            notifyObservers();
//        }
//    }

    public List<Suscripcion> getAll_Suscripciones_BD() {
        EntityManager em=getEm();
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Suscripcion> criteriaQuery = criteriaBuilder.createQuery(Suscripcion.class);
        Root<Suscripcion> viandaSuscripcion = criteriaQuery.from(Suscripcion.class);

        criteriaQuery.select(viandaSuscripcion);

        Query query = em.createQuery(criteriaQuery);
        return query.getResultList();
    }
}
