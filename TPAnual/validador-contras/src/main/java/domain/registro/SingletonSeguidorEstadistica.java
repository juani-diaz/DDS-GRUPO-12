package domain.registro;

import domain.api.ListadoLocalidades;
import domain.api.LocalidadCantidad;
import domain.colaboraciones.DistribucionVianda;
import domain.colaboraciones.DonacionVianda;
import domain.heladera.PedidoApertura;
import domain.incidente.Incidente;
import domain.persona.PersonaFisica;
import domain.rol.EnumSituacionCalle;
import domain.rol.Vulnerable;
import domain.vianda.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.time.DateUtils;
import persistence.EntidadPersistente;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Getter
@Setter
@Entity
public class SingletonSeguidorEstadistica extends EntidadPersistente {

  @OneToMany
  private List<Incidente> incidentes;
  @OneToMany
  private List<DonacionVianda> donacionViandas;
  @OneToMany
  private List<DistribucionVianda> distribucionViandas;
  @OneToMany
  private List<ViandaRecogida> retirosViandas;

  public ReporteFallas generarReporteFallas() {
    List<FallaHeladera> fe = new ArrayList<FallaHeladera>();

    Date limite = DateUtils.addDays(new Date(), -7);
    List<Incidente> incidentesUltimaSemana = this.incidentes.stream().filter(
            i -> i.getFecha().after(limite)
    ).toList();

    for(Incidente i : incidentesUltimaSemana){
      FallaHeladera encontrado;
      try {
        encontrado = fe.stream().filter(f -> f.getHeladera() == i.getHeladera()).findFirst().get();
      } catch (Exception e) {
        encontrado = null;
      }
      if(encontrado != null){
        encontrado.setFallas(encontrado.getFallas()+1);
      } else {
        fe.add(new FallaHeladera(i.getHeladera(), 1));
      }
    }

    return new ReporteFallas(fe);
  }

  public ReporteMovimientoViandas generarReporteMovimientos() {
    List<MovimientoViandasHeladera> mve = new ArrayList<MovimientoViandasHeladera>();

    // Utilizar LocalDate en lugar de Date
    LocalDate limite = LocalDate.now().minus(7, ChronoUnit.DAYS);

    List<DonacionVianda> donacionesUltimaSemana = this.donacionViandas.stream().filter(
            i -> i.getFecha().isAfter(limite)
    ).toList();

    return new ReporteMovimientoViandas();
  }

  public ReporteDonacionesViandas generarReporteDonaciones() {
    // TODO
    return null;
  }

  public ListadoLocalidades encontrarLocalidades(boolean soloSinHogar, LocalDate desde, LocalDate hasta){
    List<LocalidadCantidad> localidades = new ArrayList<LocalidadCantidad>();
    for(ViandaRecogida v : retirosViandas){
      LocalDate targetLocalDate = v.getFechaDeRecogida().toInstant()
              .atZone(ZoneId.systemDefault())
              .toLocalDate();
      if((!soloSinHogar || v.getNecesitado().getSituacionCalle() == EnumSituacionCalle.NO_POSEE_HOGAR) && !targetLocalDate.isBefore(desde) && !targetLocalDate.isAfter(hasta)) {
        Vulnerable vul = v.getNecesitado();
        String nombreCompleto = ((PersonaFisica) vul.getPersona()).getNombreCompleto();

        if (localidades.stream().anyMatch(l -> Objects.equals(l.getLocalidad(), v.getHeraderaDeVianda().getDireccion().getLocalidad()))) {
          LocalidadCantidad t = localidades.stream().filter(l -> Objects.equals(l.getLocalidad(), v.getHeraderaDeVianda().getDireccion().getLocalidad())).findFirst().get();
          t.setCantidadPersonas(t.getCantidadPersonas() + 1);

          if(t.getNombresPersonas().stream().noneMatch(n -> Objects.equals(n, nombreCompleto))){
            t.getNombresPersonas().add(nombreCompleto);
          }
        } else {
          localidades.add(new LocalidadCantidad(v.getHeraderaDeVianda().getDireccion().getLocalidad(), 1, new ArrayList<>(Arrays.asList(nombreCompleto))));
        }
      }
    }
    return new ListadoLocalidades(localidades);
  }

  private static SingletonSeguidorEstadistica instance;

  private SingletonSeguidorEstadistica() {
    this.donacionViandas = new ArrayList<DonacionVianda>();
    this.incidentes = new ArrayList<Incidente>();
    this.distribucionViandas = new ArrayList<DistribucionVianda>();
    this.retirosViandas = new ArrayList<ViandaRecogida>();
  }

  public static SingletonSeguidorEstadistica getInstance(){
    if(instance == null){
      instance = new SingletonSeguidorEstadistica();
    }
    return instance;
  }
}
