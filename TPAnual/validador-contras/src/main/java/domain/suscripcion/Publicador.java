package domain.suscripcion;

import lombok.Getter;
import lombok.Setter;
import persistence.EntidadPersistente;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;


@Getter @Setter
@Entity
public class Publicador extends EntidadPersistente {

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<Suscripcion> observers = new ArrayList<>();

    // Método que notifica a todos los suscriptores
    public void notifyObservers() {
        for (Suscripcion observer : observers) {
            observer.notificar();
        }
    }

    public void checkConditionAndNotify(boolean condicion) {
        if (condicion) {
            notifyObservers();
        }
    }
}
