package views;


import domain.auth.JwtUtil;
import domain.colaboraciones.DonacionVianda;
import domain.heladera.Heladera;
import domain.rol.Colaborador;
import domain.vianda.EnumEstadoVianda;
import domain.vianda.Vianda;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.jsonwebtoken.Claims;
import persistence.BDUtils;
import persistence.Repos.RepoColaborador;
import persistence.Repos.RepoHeladera;
import persistence.Repos.RepoVianda;

import javax.persistence.EntityManager;
import java.time.LocalDate;

public class UI_Vianda extends UI_Navegable implements Handler{

  @Override
  public void handle(Context ctx) throws Exception {
    this.validarUsuario(ctx);
    if (this.sesionValida(ctx)) {
      RepoHeladera hela = RepoHeladera.getInstance();
      this.model.put("hela", hela.getHeladeras());
      ctx.render("vianda.hbs", this.model);
    }
  }

  // Metodo para agregar una vianda a la heladera
  public void agregarVianda(Context ctx) {
    System.out.println("estoy en agregarVianda");

    // Obtener parámetros del formulario (datos enviados en la solicitud)
    String comida = ctx.formParam("comida");
    String fechaVencimientoStr = ctx.formParam("fechaVencimiento");
    String fechaDonacionStr = ctx.formParam("fechaDonacion");
    String calorias = ctx.formParam("calorias");
    String pesoStr = ctx.formParam("peso");
    String heladeraId = ctx.formParam("heladeraId");

    // Convertir parámetros necesarios
    LocalDate fechaVencimiento = LocalDate.parse(fechaVencimientoStr);
    LocalDate fechaDonacion = LocalDate.parse(fechaDonacionStr);
    float peso = Float.parseFloat(pesoStr);
    Integer heladeraID = Integer.parseInt(heladeraId);
    EnumEstadoVianda estado = EnumEstadoVianda.NO_ENTREGADO;

    // Busca la heladera en la BD
    RepoHeladera hela = RepoHeladera.getInstance();
    Heladera heladera = hela.findById_Heladera(heladeraID);
    System.out.println("HelaName= "+heladera.getNombre());

    // Crea vianda

    Vianda vianda = new Vianda(comida, fechaVencimiento, fechaDonacion, calorias, peso, estado);
    vianda.setHeladera(heladera);


    RepoVianda vian = RepoVianda.getInstance();
    vian.add_Vianda(vianda);


    String token = ctx.cookie("Auth");
    Claims claims= JwtUtil.getClaimsFromToken(token);
    RepoColaborador repoColaborador = RepoColaborador.getInstance();

    DonacionVianda dona = new DonacionVianda();
    dona.setVianda(vianda);
    dona.setDestino(heladera);
    dona.setFecha(LocalDate.now());

    Colaborador cola = repoColaborador.findById_Colaborador((Integer) claims.get("roleId"));
    dona.setColaborador(cola);

    cola.realizarColaboracion(dona);

    EntityManager em = BDUtils.getEntityManager();
    BDUtils.comenzarTransaccion(em);

    em.persist(dona);

    BDUtils.commit(em);
    ctx.render("index.hbs");
  }

}
