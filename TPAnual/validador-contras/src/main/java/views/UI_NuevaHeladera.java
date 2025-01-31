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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public void obtenerPuntosSugeridos(Context ctx) {
        double latitud = Double.parseDouble(ctx.queryParam("latitud"));
        double longitud = Double.parseDouble(ctx.queryParam("longitud"));
        double radio = Double.parseDouble(ctx.queryParam("radio"));

        // Generar puntos sugeridos (aquí puedes implementar tu lógica de negocio)
        List<PuntoSugerido> puntosSugeridos = generarPuntosSugeridos(latitud, longitud, radio);

        // Devolver la lista de puntos en formato JSON
        ctx.json(puntosSugeridos);
    }

    private List<PuntoSugerido> generarPuntosSugeridos(double latitud, double longitud, double radio) {
        List<PuntoSugerido> puntos = new ArrayList<>();
        Random random = new Random();

        // Generar 6 puntos aleatorios dentro del radio (esto es un ejemplo, puedes cambiarlo)
        for (int i = 0; i < 6; i++) {
            double randomLat = latitud + (random.nextDouble() * 2 - 1) * (radio / 111320);
            double randomLng = longitud + (random.nextDouble() * 2 - 1) * (radio / 111320);
            puntos.add(new PuntoSugerido(randomLat, randomLng));
        }

        return puntos;
    }

    // Clase interna para representar un punto sugerido
    private static class PuntoSugerido {
        private double latitud;
        private double longitud;

        public PuntoSugerido(double latitud, double longitud) {
            this.latitud = latitud;
            this.longitud = longitud;
        }

        public double getLatitud() {
            return latitud;
        }

        public double getLongitud() {
            return longitud;
        }
    }
}
