package domain.suscripcion;

import domain.heladera.Heladera;
import domain.persona.MedioDeContacto;
import domain.rol.Colaborador;
import lombok.Getter;
import lombok.Setter;
import persistence.EntidadPersistente;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Getter @Setter
@Entity
public abstract class Suscripcion extends EntidadPersistente {

  @ManyToOne
  private Colaborador colaborador;

  @ManyToOne
  Heladera heladera;

  @OneToOne
  MedioDeContacto notificadores;

  @Column
  String header;

  @Column
  String mensaje;

  void notificar(){
    notificadores.notificar(header,mensaje);
  }
}
