package domain.rol;

import domain.colaboraciones.Colaboracion;
import domain.colaboraciones.PresentacionOferta;
import domain.heladera.Heladera;
import domain.incidente.IncidenteFallaTecnica;
import domain.persona.MedioDeContacto;
import domain.persona.Persona;
import domain.registro.SingletonSeguidorEstadistica;
import domain.servicios.Catalogo;
import domain.suscripcion.MuchasViandas;
import domain.suscripcion.NoFunciona;
import domain.suscripcion.PocasViandas;
import domain.suscripcion.Suscripcion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import persistence.Repos.RepoColaboracion;
import persistence.Repos.RepoColaborador;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
//@DiscriminatorValue("colaborador")
public class Colaborador extends Rol {

  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinTable(name = "colaborador_X_colaboraciones")
  private List<Colaboracion> colaboraciones;

  @Column
  private Float cantidadPuntos;

  @Column
  private Float puntosGastados;
  //@OneToMany(cascade = CascadeType.ALL)
  //@ElementCollection
  @OneToMany
  private List<Tarjeta> tarjetasParaEntregar;

  @OneToOne
  private Tarjeta tarjetaColaborador;

  @OneToMany(
      cascade = CascadeType.REMOVE, // Aplica el comportamiento en cascada
      orphanRemoval = true // Opcional: elimina registros huérfanos
  )
  private List<Suscripcion> suscripciones=new ArrayList<>();

  public Colaborador(Persona p, List<Colaboracion> lc, Float cp, List<Tarjeta> te, Tarjeta t){
    this.persona = p;
    this.colaboraciones = lc;
    this.cantidadPuntos = cp;
    this.tarjetasParaEntregar = te;
    this.tarjetaColaborador = t;
    this.puntosGastados = 0F;
  }
  public  Colaborador(Persona p, List<Tarjeta> te){
    this.persona = p;
    this.colaboraciones = new ArrayList<Colaboracion>();
    this.cantidadPuntos = 0F;
    this.tarjetasParaEntregar = te;
    this.tarjetaColaborador = null;
    this.puntosGastados = 0F;
  }

  public Colaborador(Persona p){
    this.persona = p;
    this.colaboraciones = new ArrayList<Colaboracion>();
    this.cantidadPuntos = 0F;
    this.tarjetasParaEntregar = new ArrayList<Tarjeta>();
    this.tarjetaColaborador = null;
    this.puntosGastados = 0F;
  }

  public void realizarColaboracion(Colaboracion colaboracion){
    colaboracion.ejecutar();
    this.colaboraciones.add(colaboracion);

    RepoColaboracion.getInstance().add_Colaboracion(colaboracion);

    actualizarPuntos();
    RepoColaborador.getInstance().actualizarColaborador(this);
  }

  public void actualizarPuntos(){
    Float puntosNuevos = 0F;
    for(Colaboracion col : this.colaboraciones){
      puntosNuevos += col.puntosObtenidos();
    }
    if(puntosGastados == null)
      puntosGastados = 0F;
    this.cantidadPuntos = puntosNuevos - puntosGastados;
  }

  public boolean realizarCanje(PresentacionOferta o){
    return Catalogo.getInstance().otorgar(o, this);
  }

  public boolean entregarTarjeta(Vulnerable vulnerable){
    if(!tarjetasParaEntregar.isEmpty()) {
      Tarjeta tarjetaVulnerable = tarjetasParaEntregar.get(0);
      this.tarjetasParaEntregar.remove(tarjetaVulnerable);
      vulnerable.setTarjeta(tarjetaVulnerable);

      return true;
    }
    return false;
  }

  public void recibirTarjetas(List<Tarjeta> tarjetas) {
    if(this.persona.getDireccion() == null){
      System.out.println(this.persona.getNombre()+ " no tiene una direccion para enviarle las tarjetas");
      return;
    }
    tarjetasParaEntregar.addAll(tarjetas);
  }
  public void recibirUnaTarjeta(Tarjeta tarjeta) {
    if(this.persona.getDireccion() == null){
      System.out.println(this.persona.getNombre()+ " no tiene una direccion para enviarle las tarjetas");
      return;
    }
    tarjetasParaEntregar.add(tarjeta);
  }

  public void suscribirsePocasViandas(Heladera heladera, MedioDeContacto noti, Integer num) {
    PocasViandas pv = new PocasViandas(heladera, noti, num);
  }

  public void suscribirseDemasiadasViandas(Heladera heladera, MedioDeContacto noti, Integer num) {
    MuchasViandas ev = new MuchasViandas(heladera, noti, num);
  }

  public void suscribirseNoFuncional(Heladera heladera, MedioDeContacto noti) {
    NoFunciona hnf = new NoFunciona(heladera, noti);
  }
}
