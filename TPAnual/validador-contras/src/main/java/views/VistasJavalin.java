package views;

import controllers.HeladeraController;
import io.javalin.Javalin;



import java.io.IOException;
import java.util.function.Consumer;


    public class VistasJavalin {
        public static void main(String[] args) {
            Javalin app = Javalin.create(config -> {
                config.staticFiles.add("/front/rentrega4");
            }).start(8001);

            app.get("/{html}", ctx -> {
                ctx.result("El HTML es: " + ctx.pathParam("html"));
            });
            HeladeraController heladeraController = new HeladeraController();
            app.post("/vianda", heladeraController::agregarVianda);
        }
    }
