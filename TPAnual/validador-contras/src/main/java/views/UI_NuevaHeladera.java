package views;

import domain.colaboraciones.ResponsableHeladera;
import domain.heladera.EnumEstadoHeladera;
import domain.heladera.Heladera;
import domain.heladera.PredeterminadosYEstadisticasHeladera;
import domain.heladera.Ubicacion;
import domain.rol.Colaborador;
import domain.rol.LocalidadesTecnico;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import lombok.NoArgsConstructor;
import persistence.Repos.RepoHeladera;

import java.time.LocalDate;

@NoArgsConstructor
public class UI_NuevaHeladera extends UI_Navegable implements Handler {

    @Override
    public void handle(Context ctx) throws Exception {
        this.validarUsuario(ctx);

        PredeterminadosYEstadisticasHeladera estadisticasHeladera = new PredeterminadosYEstadisticasHeladera();
        model.put("estadisticas", estadisticasHeladera);
        LocalidadesTecnico lt = new LocalidadesTecnico();
        model.put("listadoLocalidades", lt);
        ctx.render("nueva-heladera.hbs", this.model);
    }

    public void nuevaHeladera(Context ctx) {
        String ciudad = ctx.formParam("ciudad");
        String localidad = ctx.formParam("localidad");
        String calle = ctx.formParam("calle");
        String altura = ctx.formParam("altura");
        String nombre = ctx.formParam("nombre");
        String tamanio = ctx.formParam("tamanioPedido");
        String latitud = ctx.formParam("latitud");
        String longitud = ctx.formParam("longitud");

        Ubicacion nuevaUbicacion = new Ubicacion(ciudad,localidad, latitud, longitud, calle ,altura);
        Heladera nuevaHeladera = new Heladera(nombre,nuevaUbicacion, Integer.valueOf(tamanio), LocalDate.now(), null, null, EnumEstadoHeladera.PENDIENTE_INSTALACION);

        Colaborador colaborador = (Colaborador) getUsuario().getRol();

        ResponsableHeladera colaboracion = new ResponsableHeladera(colaborador, LocalDate.now(), nuevaHeladera);

        colaborador.realizarColaboracion(colaboracion);

        RepoHeladera.getInstance().add_Heladera(nuevaHeladera);

        ctx.redirect("/heladeras-o");
    }
}
