package domain.suscripcion;

import domain.heladera.Heladera;
import domain.persona.MedioDeContacto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter @Setter @Entity
public class NoFunciona extends Suscripcion {

    public NoFunciona() {
    }

    public NoFunciona(Heladera heladera, MedioDeContacto notificadores){
        this.heladera=heladera;
        this.notificadores=notificadores;
        header="Notificacion por suscripcion";
        mensaje="La heladera sufrió un desperfecto y las viandas deben ser llevadas a otras heladeras a la brevedad para que las mismas no se echen a perder";
    }
    public NoFunciona(Heladera heladera){
        this.heladera=heladera;
        this.notificadores=null;
        header="Notificacion por suscripcion";
        mensaje="La heladera sufrió un desperfecto y las viandas deben ser llevadas a otras heladeras a la brevedad para que las mismas no se echen a perder";
    }
}