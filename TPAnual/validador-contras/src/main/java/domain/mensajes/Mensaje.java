package domain.mensajes;

import lombok.Getter;
import lombok.Setter;
import persistence.EntidadPersistente;

import javax.persistence.Column;
import javax.persistence.Entity;
@Getter@Setter
@Entity
public abstract class Mensaje extends EntidadPersistente {

  @Column
  String titulo;
  @Column
  String cuerpo;
}
