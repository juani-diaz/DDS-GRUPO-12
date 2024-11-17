package domain.rol;

import domain.heladera.AdministradorSolicitudes;
import domain.heladera.Heladera;
import domain.persona.Persona;
import domain.registro.SingletonSeguidorEstadistica;
import domain.vianda.Vianda;
import domain.vianda.ViandaRecogida;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
//@Entity
public class Vulnerable extends Rol {
  private LocalDate fechaRegistro;

  private EnumSituacionCalle situacionCalle;
  private Integer menoresACargo;
  //Tambien ver esto
  private List<ViandaRecogida> viandasTomadas;
  private Tarjeta tarjeta;
  //Ver Esto
  private List<UsoDeTarjeta> usos;
  private Integer usosRestantesPorDia;

  public boolean retirarVianda(int indiceViandas, Heladera heladera){
    if(usosRestantesPorDia > 0){
      usosRestantesPorDia = usosRestantesPorDia - 1;
      Vianda viandaRecogida = heladera.sacarVianda(indiceViandas);
      ViandaRecogida vr = new ViandaRecogida(this, heladera, viandaRecogida, new Date());

      SingletonSeguidorEstadistica se = new SingletonSeguidorEstadistica(); // TODO singleton?
      se.getRetirosViandas().add(vr);

      viandasTomadas.add(new ViandaRecogida(this, heladera, viandaRecogida, new Date()));
      return true;
    } else
      return false;
  }
  public void calcularUsos(){ // Esto se ejecuta a las 00:00 de cada dia
    this.usosRestantesPorDia = 4 + this.menoresACargo * 2;
  }
}