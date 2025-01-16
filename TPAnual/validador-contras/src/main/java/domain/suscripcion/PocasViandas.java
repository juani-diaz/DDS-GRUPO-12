package domain.suscripcion;

import domain.heladera.Heladera;
import domain.persona.MedioDeContacto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter @Setter
@Entity
public class PocasViandas extends Suscripcion{

  @Column
  private Integer numeroMinimo;

  public PocasViandas() {
  }

  public PocasViandas(Heladera heladera, MedioDeContacto notificadores, Integer numeroMinimo){
    this.heladera=heladera;
    this.notificadores=notificadores;
    this.numeroMinimo = numeroMinimo;
    header="Notificacion por suscripcion";
    this.mensaje="Quedan únicamente "+numeroMinimo+" viandas disponibles en la heladera";
  }

  public PocasViandas(Heladera heladera) {
    this.heladera = heladera;
    this.notificadores=null;
    this.numeroMinimo=(Integer) (heladera.getTamanioEnViandas()/5);
    header="Notificacion por suscripcion";
    this.mensaje="Quedan únicamente "+numeroMinimo+" viandas disponibles en la heladera";
  }
}
