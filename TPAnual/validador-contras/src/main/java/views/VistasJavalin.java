package views;

import controllers.HeladeraController;
import io.javalin.Javalin;



import java.io.IOException;
import java.util.function.Consumer;


    public class VistasJavalin {
        public static void main(String[] args) {
            System.out.println("estou en javalin");
            Javalin app = Javalin.create(config -> {
                config.staticFiles.add("/front/rentrega4");
            }).start(8001);

            app.get("/vianda", ctx -> {
                ctx.render("vianda.html");
            });
            HeladeraController heladeraController = new HeladeraController();
            System.out.println("estou en javalin");

            app.post("/vianda", heladeraController::agregarVianda);
        }
    }
