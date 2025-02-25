package views;


import domain.auth.JwtUtil;
import domain.colaboraciones.DistribucionVianda;
import domain.colaboraciones.EnumMotivosMovimientoVianda;
import domain.heladera.Heladera;
import domain.persona.EnumTipoPersonaJuridica;
import domain.rol.Colaborador;
import domain.suscripcion.Publicador;
import domain.vianda.Vianda;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.jsonwebtoken.Claims;
import persistence.BDUtils;
import persistence.EntidadPersistente;
import persistence.Repos.RepoColaborador;
import persistence.Repos.RepoHeladera;
import persistence.Repos.RepoVianda;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class UI_Traslado extends UI_Navegable implements Handler{

  @Override
  public void handle(Context ctx) throws Exception {
    this.validarUsuario(ctx);

    RepoHeladera hela = RepoHeladera.getInstance();

    List<Heladera> heladeras = hela.getHeladeras();

    this.model.put("hela", heladeras);
    this.model.put("helaDonables", heladeras.stream().filter(Heladera::puedoDonarle).toList());

    ctx.render("traslado.hbs", this.model);
  }

  public void trasladarCantViandas(Context ctx) throws IOException {
    // Obtener parámetros del formulario (datos enviados en la solicitud)
    String cantidad = ctx.formParam("cantidad");
    Integer cantidadViandas= Integer.parseInt(cantidad);
    String heladeraIdProviene = ctx.formParam("heladeraID");
    String heladeraIdHacia = ctx.formParam("heladeraIDHacia");
    String motivoMovimiento = ctx.formParam("motivoTraslado");

    EnumMotivosMovimientoVianda motivo=null;
    if(Objects.equals(motivoMovimiento, "FALTA_DE_VIANDAS")){
      motivo = EnumMotivosMovimientoVianda.FALTA_DE_VIANDAS;
    } else if(Objects.equals(motivoMovimiento, "DESPERFECTO_HELADERA")){
      motivo = EnumMotivosMovimientoVianda.DESPERFECTO_HELADERA;
    }

    // Convertir parámetros necesarios
    Integer heladeraID = Integer.parseInt(heladeraIdProviene);
    Integer heladeraIDVa = Integer.parseInt(heladeraIdHacia);

    // Busca la heladera en la BD
    RepoHeladera hela = RepoHeladera.getInstance();
    Heladera heladera = hela.findById_Heladera(heladeraID);
    Heladera heladeraHacia = hela.findById_Heladera(heladeraIDVa);

    if (heladera == heladeraHacia) {
        throw new IllegalArgumentException("No se puede mover a la misma heladera");
    }

    if(cantidadViandas<=heladera.cantidadViandas()) {
        Colaborador colapinto = (Colaborador) getUsuario().getRol();

        DistribucionVianda distribucion = new DistribucionVianda(colapinto, LocalDate.now(), heladera, heladeraHacia, cantidadViandas, motivo);

        colapinto.realizarColaboracion(distribucion);
    } else System.out.println("La heladera no puede mover mas viandas de las que tiene");
    Publicador pub=new Publicador();
    pub.notifyObservers();

    ctx.redirect("/index");
  }

}
