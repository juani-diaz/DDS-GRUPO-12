package domain.suscripcion;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter @Setter
public class Publicador {

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
