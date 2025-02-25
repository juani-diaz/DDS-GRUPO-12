package views;

import domain.auth.JwtUtil;
import domain.colaboraciones.DonacionDinero;
import domain.colaboraciones.MedioDePago;
import domain.rol.Colaborador;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.jsonwebtoken.Claims;
import persistence.BDUtils;
import persistence.Repos.RepoColaborador;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Objects;

public class UI_Dinero extends UI_Navegable implements Handler{


  @Override
  public void handle(Context ctx) throws Exception {
    this.validarUsuario(ctx);

    ctx.render("dinero.hbs", this.model);
  }

  public void agregarDonacion(Context ctx) {
    // Obtener parámetros del formulario (datos enviados en la solicitud)
    String monto = ctx.formParam("monto");
    String tipoTarjeta = ctx.formParam("tipoTarjeta");
    String numTarjeta = ctx.formParam("numTarjeta");
    String nombreTitular = ctx.formParam("nombreTitular");
    String mesVencimiento = ctx.formParam("mesVencimiento");
    String codSeguridad = ctx.formParam("codSeguridad");

    String tipo = ctx.formParam("tipo");
    String recurrencia = ctx.formParam("recurrencia");
    if(Objects.equals(tipo, "unica"))
      recurrencia = "unica";

    System.out.println(ctx.formParam("tipo"));
    System.out.println(ctx.formParam("recurrencia"));
    System.out.println(recurrencia);

    // Convertir parámetros necesarios
    Float montoFloat = Float.parseFloat(monto);

    // Crear MedioPago
    MedioDePago medioDePago = new MedioDePago();
    medioDePago.setTipoTarjeta(tipoTarjeta);
    medioDePago.setNumTarjeta(numTarjeta);
    medioDePago.setNombreTitular(nombreTitular);
    medioDePago.setMesVencimiento(mesVencimiento);
    medioDePago.setCodSeguridad(codSeguridad);

    Colaborador cola = (Colaborador) getUsuario().getRol();

    // Crea Donacion
    DonacionDinero dona = new DonacionDinero(cola, LocalDate.now(), montoFloat, recurrencia, medioDePago);
    cola.realizarColaboracion(dona);

    ctx.redirect("/index");
  }


}
