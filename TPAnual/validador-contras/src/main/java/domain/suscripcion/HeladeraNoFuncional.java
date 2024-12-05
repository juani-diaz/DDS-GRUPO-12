package domain.suscripcion;

import domain.heladera.Heladera;
import domain.persona.MedioDeContacto;

public class HeladeraNoFuncional extends Suscripcion {

    public HeladeraNoFuncional(Heladera heladera, MedioDeContacto notificadores){
        this.heladera=heladera;
        this.notificadores=notificadores;
        header="Notificacion por suscripcion";
        mensaje="La heladera sufrió un desperfecto y las viandas deben ser llevadas a otras heladeras a la brevedad para que las mismas no se echen a perder";
    }
}