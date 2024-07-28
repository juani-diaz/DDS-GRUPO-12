package domain.suscripcion;

import domain.heladera.Heladera;
import domain.persona.MedioDeContacto;

public class HeladeraNoFuncional extends Suscripcion {
    public HeladeraNoFuncional(Heladera heladera, MedioDeContacto notificadores){
        super(heladera, notificadores);
    }
}
