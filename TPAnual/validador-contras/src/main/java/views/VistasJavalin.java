package views;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import controllers.HeladeraController;
import controllers.UIListaHeladerasController;
import domain.heladera.EnumEstadoHeladera;
import domain.heladera.Heladera;
import domain.heladera.RepoHeladera;
import domain.heladera.Ubicacion;
import domain.vianda.Vianda;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;
import io.javalin.rendering.JavalinRenderer;


import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;


    public class VistasJavalin {
        public static void main(String[] args) {

            initTemplateEngine();

            RepoHeladera hela = new RepoHeladera();

            Integer port = Integer.parseInt(System.getProperty("port", "8001"));
            Javalin app = Javalin.create(config -> {
                config.staticFiles.add("/front/rentrega4");
            }).start(port);

            app.get("/vianda", new UIListaHeladerasController(hela));

            HeladeraController heladeraController = new HeladeraController();

            app.post("/vianda", heladeraController::agregarVianda);

            app.get("/", ctx -> {
                ctx.render("index.hbs");
            });

            app.get("/index", ctx -> {
                ctx.render("index.hbs");
            });
            app.get("/traslado", ctx -> {
                ctx.render("traslado.hbs");
            });
            app.get("/dinero", ctx -> {
                ctx.render("dinero.hbs");
            });
            app.get("/fallas", ctx -> {
                ctx.render("fallas.hbs");
            });
            app.get("/heladeras-a", ctx -> {
                ctx.render("heladeras-a.hbs");
            });
            app.get("/heladeras-o", ctx -> {
                ctx.render("heladeras-o.hbs");
            });
            app.get("/heladeras-p", ctx -> {
                ctx.render("heladeras-p.hbs");
            });
            app.get("/landing", ctx -> {
                ctx.render("landing.hbs");
            });
            app.get("/migracion", ctx -> {
                ctx.render("migracion.hbs");
            });
            app.get("/ofertas", ctx -> {
                ctx.render("ofertas.hbs");
            });
            app.get("/page-login", ctx -> {
                ctx.render("page-login.hbs");
            });
            app.get("/page-register", ctx -> {
                ctx.render("page-register.hbs");
            });
            app.get("/puntos", ctx -> {
                ctx.render("puntos.hbs");
            });
            app.get("/registrar-personas", ctx -> {
                ctx.render("registrar-personas.hbs");
            });
            app.get("/reportes", ctx -> {
                ctx.render("reportes.hbs");
            });
            app.get("/traslado", ctx -> {
                ctx.render("traslado.hbs");
            });

        }
        private static void initTemplateEngine() {
            JavalinRenderer.register(
                (path, model, context) -> { // Función que renderiza el template
                    Handlebars handlebars = new Handlebars();
                    Template template = null;
                    try {
                        template = handlebars.compile("templates/" + path.replace(".hbs", ""));
                        return template.apply(model);
                    } catch (IOException e) {
                        //
                        e.printStackTrace();
                        context.status(HttpStatus.NOT_FOUND);
                        return "No se encuentra la página indicada...";
                    }
                }, ".hbs" // Extensión del archivo de template
            );
        }

        private static Consumer<JavalinConfig> config() {
            return config -> {
                config.staticFiles.add(staticFiles -> {
                    staticFiles.hostedPath = "/";
                    staticFiles.directory = "/public";
                });
            };
        }

    }
