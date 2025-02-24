package domain.registro;

import domain.api.ListadoLocalidades;
import domain.api.LocalidadCantidad;
import domain.colaboraciones.DistribucionVianda;
import domain.colaboraciones.DonacionVianda;
import domain.heladera.Heladera;
import domain.incidente.EnumEstadoDeIncidente;
import domain.incidente.Incidente;
import domain.persona.PersonaFisica;
import domain.rol.Colaborador;
import domain.rol.EnumSituacionCalle;
import domain.rol.Tecnico;
import domain.rol.Vulnerable;
import domain.vianda.ViandaRecogida;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.time.DateUtils;
import persistence.BDUtils;
import persistence.EntidadPersistente;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.OneToMany;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Getter
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

    Date limiteDate = DateUtils.addDays(new Date(), -7);

    LocalDate limite = limiteDate.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate();

    List<Incidente> incidentesUltimaSemana = this.incidentes.stream().filter(
            i -> i.getFecha().isAfter(limite)
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

      if(v.getFechaDeRecogida() == null || v.getNecesitado() == null || v.getNecesitado().getSituacionCalle() == null)
        continue;

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
  private EntityManager em = BDUtils.getEntityManager();

  private SingletonSeguidorEstadistica() {
    this.donacionViandas = getDonaciones_BD();
    this.incidentes = getIncidentes_BD();
    this.distribucionViandas = getDistribuciones_BD();
    this.retirosViandas = getRetiros_BD();
  }

  public List<DonacionVianda> getDonaciones_BD() {
    CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
    CriteriaQuery<DonacionVianda> criteriaQuery = criteriaBuilder.createQuery(DonacionVianda.class);
    Root<DonacionVianda> donacionRoot = criteriaQuery.from(DonacionVianda.class);

    criteriaQuery.select(donacionRoot);

    Query query = em.createQuery(criteriaQuery);
    return query.getResultList();
  }

  public List<Incidente> getIncidentes_BD() {
    CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
    CriteriaQuery<Incidente> criteriaQuery = criteriaBuilder.createQuery(Incidente.class);
    Root<Incidente> incidenteRoot = criteriaQuery.from(Incidente.class);

    criteriaQuery.select(incidenteRoot);

    Query query = em.createQuery(criteriaQuery);
    return query.getResultList();
  }

  public List<DistribucionVianda> getDistribuciones_BD() {
    CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
    CriteriaQuery<DistribucionVianda> criteriaQuery = criteriaBuilder.createQuery(DistribucionVianda.class);
    Root<DistribucionVianda> distrRoot = criteriaQuery.from(DistribucionVianda.class);

    criteriaQuery.select(distrRoot);

    Query query = em.createQuery(criteriaQuery);
    return query.getResultList();
  }

  public List<ViandaRecogida> getRetiros_BD() {
    CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
    CriteriaQuery<ViandaRecogida> criteriaQuery = criteriaBuilder.createQuery(ViandaRecogida.class);
    Root<ViandaRecogida> retiroRoot = criteriaQuery.from(ViandaRecogida.class);

    criteriaQuery.select(retiroRoot);

    Query query = em.createQuery(criteriaQuery);
    return query.getResultList();
  }

  public static SingletonSeguidorEstadistica getInstance(){
    if(instance == null){
      instance = new SingletonSeguidorEstadistica();
    }
    return instance;
  }

  // Métodos para Incidentes
  public void addIncidente(Incidente incidente) {
    incidentes.add(incidente);
    persistirEntidad(incidente);
  }

  public void updateIncidente(Incidente incidente) {
    BDUtils.comenzarTransaccion(em);
    try {
      em.merge(incidente);
      BDUtils.commit(em);
    } catch (Exception e) {
      System.out.println("Error al actualizar el incidente: " + e);
      BDUtils.rollback(em);
    }
  }

  public void removeIncidente(Incidente incidente) {
    incidentes.remove(incidente);
    eliminarEntidad(incidente);
  }

  public Incidente findIncidenteById(Integer id) {
    return incidentes.stream()
            .filter(i -> i.getId() == id)
            .findFirst()
            .orElse(null);
  }

  // Métodos para DonacionVianda
  public void addDonacionVianda(DonacionVianda donacionVianda) {
    donacionViandas.add(donacionVianda);
    //persistirEntidad(donacionVianda);
  }

  public void updateDonacionVianda(DonacionVianda donacionVianda) {
    BDUtils.comenzarTransaccion(em);
    try {
      em.merge(donacionVianda);
      BDUtils.commit(em);
    } catch (Exception e) {
      System.out.println("Error al actualizar la donación de vianda: " + e);
      BDUtils.rollback(em);
    }
  }

  public void removeDonacionVianda(DonacionVianda donacionVianda) {
    donacionViandas.remove(donacionVianda);
    eliminarEntidad(donacionVianda);
  }

  public DonacionVianda findDonacionViandaById(Integer id) {
    return donacionViandas.stream()
            .filter(dv -> dv.getId() == id)
            .findFirst()
            .orElse(null);
  }

  // Métodos para DistribucionVianda
  public void addDistribucionVianda(DistribucionVianda distribucionVianda) {
    distribucionViandas.add(distribucionVianda);
    //persistirEntidad(distribucionVianda);
  }

  public void updateDistribucionVianda(DistribucionVianda distribucionVianda) {
    BDUtils.comenzarTransaccion(em);
    try {
      em.merge(distribucionVianda);
      BDUtils.commit(em);
    } catch (Exception e) {
      System.out.println("Error al actualizar la distribución de vianda: " + e);
      BDUtils.rollback(em);
    }
  }

  public void removeDistribucionVianda(DistribucionVianda distribucionVianda) {
    distribucionViandas.remove(distribucionVianda);
    eliminarEntidad(distribucionVianda);
  }

  public DistribucionVianda findDistribucionViandaById(Integer id) {
    return distribucionViandas.stream()
            .filter(dv -> dv.getId() == id)
            .findFirst()
            .orElse(null);
  }

  // Métodos para ViandaRecogida
  public void addViandaRecogida(ViandaRecogida viandaRecogida) {
    retirosViandas.add(viandaRecogida);
    persistirEntidad(viandaRecogida);
  }

  public void updateViandaRecogida(ViandaRecogida viandaRecogida) {
    BDUtils.comenzarTransaccion(em);
    try {
      em.merge(viandaRecogida);
      BDUtils.commit(em);
    } catch (Exception e) {
      System.out.println("Error al actualizar la vianda recogida: " + e);
      BDUtils.rollback(em);
    }
  }

  public void removeViandaRecogida(ViandaRecogida viandaRecogida) {
    retirosViandas.remove(viandaRecogida);
    eliminarEntidad(viandaRecogida);
  }

  public ViandaRecogida findViandaRecogidaById(Integer id) {
    return retirosViandas.stream()
            .filter(vr -> vr.getId() == id)
            .findFirst()
            .orElse(null);
  }

  // Métodos auxiliares para persistencia
  private void persistirEntidad(Object entidad) {
    BDUtils.comenzarTransaccion(em);
    try {
      em.persist(entidad);
      BDUtils.commit(em);
    } catch (Exception e) {
      System.out.println("Error al persistir la entidad: " + e);
      BDUtils.rollback(em);
    }
  }

  private void eliminarEntidad(Object entidad) {
    BDUtils.comenzarTransaccion(em);
    try {
      em.remove(entidad);
      BDUtils.commit(em);
    } catch (Exception e) {
      System.out.println("Error al eliminar la entidad: " + e);
      BDUtils.rollback(em);
    }
  }

  public List<Incidente> getIncidentesEnArea(Tecnico tecnico) {
    return incidentes.stream().filter(i ->
            (i.getEstadoDeIncidente() == EnumEstadoDeIncidente.PENDIENTE_A_SOLUCIONAR
            || (i.getEstadoDeIncidente() == EnumEstadoDeIncidente.TECNICO_ASIGNADO && i.getTecnico().getId() == tecnico.getId()))
            && tecnico.cubreLocalidad(i.getHeladera().getDireccion().getLocalidad())
    ).toList();
  }

  public List<Incidente> getIncidentesFueraArea(Tecnico tecnico) {
    return incidentes.stream().filter(i ->
            (i.getEstadoDeIncidente() == EnumEstadoDeIncidente.PENDIENTE_A_SOLUCIONAR
            || (i.getEstadoDeIncidente() == EnumEstadoDeIncidente.TECNICO_ASIGNADO && i.getTecnico().getId() == tecnico.getId()))
            && !tecnico.cubreLocalidad(i.getHeladera().getDireccion().getLocalidad())
    ).toList();
  }
}
