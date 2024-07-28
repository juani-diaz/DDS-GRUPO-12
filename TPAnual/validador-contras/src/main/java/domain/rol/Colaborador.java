package domain.rol;

import domain.colaboraciones.*;
import domain.heladera.Heladera;
import domain.incidente.Incidente;
import domain.incidente.IncidenteFallaTecnica;
import domain.persona.MedioDeContacto;
import domain.persona.Persona;
import domain.registro.FallaHeladera;
import domain.registro.SingletonSeguidorEstadistica;
import domain.servicios.Catalogo;
import domain.suscripcion.ExcesoViandas;
import domain.suscripcion.HeladeraNoFuncional;
import domain.suscripcion.PocasViandas;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Colaborador extends Rol {
  private List<Colaboracion> colaboraciones;
  private Float cantidadPuntos;
  private List<Tarjeta> tarjetasParaEntregar;
  private Tarjeta tarjetaColaborador;

  public Colaborador(Persona p, List<Colaboracion> lc, Float cp, List<Tarjeta> te, Tarjeta t){
    this.persona = p;
    this.colaboraciones = lc;
    this.cantidadPuntos = cp;
    this.tarjetasParaEntregar = te;
    this.tarjetaColaborador = t;
  }

  public void realizarColaboracion(Colaboracion colaboracion){
    colaboracion.ejecutar();
    this.colaboraciones.add(colaboracion);
    this.actualizarPuntos();
  }

  public void actualizarPuntos(){
    Float puntosNuevos = 0F;
    for(Colaboracion col : this.colaboraciones){
      puntosNuevos += col.puntosObtenidos();
    }
    this.cantidadPuntos = puntosNuevos;
  }

  public boolean entregarTarjeta(Vulnerable destinatario){
    if(!tarjetasParaEntregar.isEmpty()) {
      destinatario.setTarjeta(tarjetasParaEntregar.remove(0));
      return true;
    }
    return false;
  }

  public void recibirTarjetas(List<Tarjeta> tarjetas) { tarjetasParaEntregar.addAll(tarjetas); }

  public boolean realizarCanje(Integer indiceOferta){
    return Catalogo.otorgar(indiceOferta, this);
  }

  public void reportarIncidente(Heladera heladera, String descripcion, String foto) {
    IncidenteFallaTecnica falla = new IncidenteFallaTecnica(heladera, new Date(), this, descripcion, foto);
    SingletonSeguidorEstadistica se = new SingletonSeguidorEstadistica(); // TODO singleton?
    se.getIncidentes().add(falla);
  }

  public void suscribirsePocasViandas(Heladera heladera, MedioDeContacto noti, Integer num) {
    PocasViandas pv = new PocasViandas(heladera, noti, num);
  }

  public void suscribirseDemasiadasViandas(Heladera heladera, MedioDeContacto noti, Integer num) {
    ExcesoViandas ev = new ExcesoViandas(heladera, noti, num);
  }

  public void suscribirseNoFuncional(Heladera heladera, MedioDeContacto noti) {
    HeladeraNoFuncional hnf = new HeladeraNoFuncional(heladera, noti);
  }
}
