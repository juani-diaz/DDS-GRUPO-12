package domain.suscripcion;

import domain.heladera.Heladera;
import domain.persona.MedioDeContacto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.IOException;

@Getter @Setter
@Entity
public class MuchasViandas extends Suscripcion{

  @Column
  private Integer numeroMaximo;

  public MuchasViandas() {
  }

  @Override
  void notificar() throws IOException {
    String subject =
            "Suscripcion a heladeraID "+ this.getHeladera();
    String mensaje =
        "<h1>Hola <strong>" + this.getColaborador().getPersona().getNombre() + "</strong>,</h1>" +
            "<p>La heladera <strong>"+ this.getHeladera().getNombre() +"</strong>"+
                    " tiene " + this.getHeladera().cantidadViandas()+ " viandas</p>";

    this.notificadores.notificar(subject,mensaje);

  }

  @Override
  boolean condicion(Integer cantidad) {
    return cantidad>=numeroMaximo;
  }

  public MuchasViandas(Heladera heladera, MedioDeContacto notificadores, Integer numeroMaximo){
    this.heladera=heladera;
    this.notificadores=notificadores;
    this.numeroMaximo = numeroMaximo;
    header="Notificacion por suscripcion";
    this.mensaje="Faltan "+numeroMaximo+" viandas para que la heladera esté llena y no se puedan ingresar más viandas";
  }

  public MuchasViandas(Heladera heladera){
    this.heladera=heladera;
    this.notificadores=null;
    this.numeroMaximo = (Integer) (heladera.getTamanioEnViandas()*8/10);
    header="Notificacion por suscripcion";
    this.mensaje="Faltan "+numeroMaximo+" viandas para que la heladera esté llena y no se puedan ingresar más viandas";
  }

  public MuchasViandas(Heladera heladera, int numeroMaximo){
    this.heladera=heladera;
    this.notificadores=null;
    this.numeroMaximo = numeroMaximo;
    header="Notificacion por suscripcion";
    this.mensaje="Faltan "+numeroMaximo+" viandas para que la heladera esté llena y no se puedan ingresar más viandas";
  }
}
