package views;

import domain.colaboraciones.DonacionDinero;
import domain.colaboraciones.MedioDePago;
import domain.persona.Documento;
import domain.persona.PersonaFisica;
import domain.rol.EnumSituacionCalle;
import domain.rol.Vulnerable;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import persistence.BDUtils;

import javax.persistence.EntityManager;
import java.time.LocalDate;

public class UI_Dinero extends UI_Navegable implements Handler{

  @Override
  public void handle(Context ctx) throws Exception {

    this.validarUsuario(ctx);
    if (this.sesionValida()) {
      System.out.println("estoy en UI_Dinero");
      ctx.render("dinero.hbs");
    }

  }

  public void agregarDonacion(Context ctx) {
    System.out.println("estoy en agregarDonacion");

    // Obtener parámetros del formulario (datos enviados en la solicitud)
    String monto = ctx.formParam("monto");
    System.out.println("monto" + monto);
    String tipoTarjeta = ctx.formParam("tipoTarjeta");
    System.out.println("tipoTarjeta" + tipoTarjeta);
    String numTarjeta = ctx.formParam("numTarjeta");
    System.out.println("numTarjeta" + numTarjeta);
    String nombreTitular = ctx.formParam("nombreTitular");
    System.out.println("nombreTitular" + nombreTitular);
    String mesVencimiento = ctx.formParam("mesVencimiento");
    System.out.println("mesVencimiento" + mesVencimiento);
    String codSeguridad = ctx.formParam("codSeguridad");
    System.out.println("codSeguridad" + codSeguridad);
    //String numMenoresACargo =ctx.formParam("numMenoresACargo");

    // Convertir parámetros necesarios
    Float montoFloat = Float.parseFloat(monto);

    // Crear MedioPago
    MedioDePago medioDePago = new MedioDePago();
    medioDePago.setTipoTarjeta(tipoTarjeta);
    medioDePago.setNumTarjeta(numTarjeta);
    medioDePago.setNombreTitular(nombreTitular);
    medioDePago.setMesVencimiento(mesVencimiento);
    medioDePago.setCodSeguridad(codSeguridad);

    // Crea Donacion
    DonacionDinero dona = new DonacionDinero();
    dona.setMonto(montoFloat);
    dona.setMedioDePago(medioDePago);


    //ORM
    EntityManager em = BDUtils.getEntityManager();
    BDUtils.comenzarTransaccion(em);

    em.persist(medioDePago);
    em.persist(dona);

    BDUtils.commit(em);

    ctx.render("index.hbs");
  }


}
