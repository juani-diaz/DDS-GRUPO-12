package domain.rol;

import domain.colaboraciones.*;
import domain.persona.Persona;
import domain.servicios.Catalogo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor
public class Colaborador extends Rol {
  private List<Colaboracion> colaboraciones;
  private Float cantidadPuntos;
  private List<Tarjeta> tarjetasParaEntregar;

  public Colaborador(Persona p, List<Colaboracion> lc, Float cp, List<Tarjeta> te){
    this.persona = p;
    this.colaboraciones = lc;
    this.cantidadPuntos = cp;
    this.tarjetasParaEntregar = te;
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
}
