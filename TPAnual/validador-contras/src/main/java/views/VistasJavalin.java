package views;

import com.github.jknack.handlebars.Handlebars;
import controllers.HeladeraController;
import domain.heladera.EnumEstadoHeladera;
import domain.heladera.Heladera;
import domain.heladera.RepoHeladera;
import domain.heladera.Ubicacion;
import domain.vianda.Vianda;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.HttpStatus;
import io.javalin.rendering.JavalinRenderer;


import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;


    public class VistasJavalin {
        public static void main(String[] args) {
            //System.out.println("estou en javalin");
            Javalin app = Javalin.create(config -> {
                config.staticFiles.add("/front/rentrega4");
            }).start(8001);

            Ubicacion ubicacion = new Ubicacion();
            LocalDate fechaFuncionamiento = LocalDate.now();
            Heladera hela = new Heladera("heladera1",ubicacion, 100, fechaFuncionamiento,12F,20F, EnumEstadoHeladera.DISPONIBLE);


            app.get("/vianda", ctx -> {
                ctx.render("vianda.html"); //Probar sin render
            });
            HeladeraController heladeraController = new HeladeraController();

            app.post("/vianda", heladeraController::agregarVianda);
        }


    }
