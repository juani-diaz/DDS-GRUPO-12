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
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.Transient;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
@DiscriminatorValue("vulnerable")
public class Vulnerable extends Rol {

  @Column(columnDefinition = "DATE")
  private LocalDate fechaRegistro;
  @Enumerated(value = EnumType.STRING)
  private EnumSituacionCalle situacionCalle;
  @Column
  private Integer menoresACargo;
  //Tambien ver esto
  @OneToMany
  private List<ViandaRecogida> viandasTomadas;
  @OneToOne
  private Tarjeta tarjeta;
  //Ver Esto
  @OneToMany
  private List<UsoDeTarjeta> usos;
  @Column
  private Integer usosRestantesPorDia;


  public Vulnerable(Persona p, LocalDate fr,EnumSituacionCalle tipo ,Integer mAC, List<ViandaRecogida> vr, Tarjeta t, List<UsoDeTarjeta> u , Integer uRpD){
    this.persona = p;
    this.menoresACargo = mAC;
    this.fechaRegistro = fr;
    this.situacionCalle = tipo;
    this.viandasTomadas = vr;
    this.tarjeta = t;
    this.usos = u;
    this.usosRestantesPorDia = uRpD;
  }

  public boolean retirarVianda(int indiceViandas, Heladera heladera){
    if(usosRestantesPorDia > 0){
      usosRestantesPorDia = usosRestantesPorDia - 1;
      Vianda viandaRecogida = heladera.sacarVianda(indiceViandas);
      ViandaRecogida vr = new ViandaRecogida(this, heladera, viandaRecogida, new Date());

      SingletonSeguidorEstadistica se = SingletonSeguidorEstadistica.getInstance();
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
