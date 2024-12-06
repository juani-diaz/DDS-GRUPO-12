package views;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import domain.api.ListadoLocalidades;
import domain.registro.SingletonSeguidorEstadistica;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.HttpStatus;
import io.javalin.rendering.JavalinRenderer;


import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import persistence.Demo;


public class VistasJavalin {
        public static void main(String[] args) throws JsonProcessingException {

            initTemplateEngine();

            Demo.main(null);

            Integer port = Integer.parseInt(System.getProperty("port", "8001"));
            Javalin app = Javalin.create(config -> {
                config.staticFiles.add("/front/rentrega4");
            }).start(port);


            app.get("/", ctx -> {
                ctx.render("index.hbs");
            });

            app.get("/index", ctx -> {
                ctx.render("index.hbs");
            });

//======================VIANDA================================
            UI_Vianda UIVianda = new UI_Vianda();

            app.get("/vianda", UIVianda);
            app.post("/vianda", UIVianda::agregarVianda);

//======================TRASLADO================================
            app.get("/traslado", ctx -> {
                ctx.render("traslado.hbs");
            });

//======================DINERO================================
            app.get("/dinero", ctx -> {
                ctx.render("dinero.hbs");
            });

//======================FALLAS================================
            app.get("/fallas", ctx -> {
                ctx.render("fallas.hbs");
            });

//======================HELADERAS-A================================
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

            app.get("/api/localizacion", ctx -> {
                String desdeQ = ctx.queryParam("desde");
                String hastaQ = ctx.queryParam("hasta");
                String soloQ = ctx.queryParam("soloSinHogar");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                LocalDate desde;
                try {
                    desde = LocalDate.parse(desdeQ, formatter);
                } catch (Exception e) {
                    desde = LocalDate.now().minusDays(1);
                }

                LocalDate hasta;
                try {
                    hasta = LocalDate.parse(hastaQ, formatter);
                } catch (Exception e) {
                    hasta = LocalDate.now();
                }

                boolean soloSinHogar;
                if(soloQ != null){
                    soloSinHogar = Boolean.parseBoolean(soloQ);
                } else {
                    soloSinHogar = true;
                }

                String json = null;
                try {
                    ObjectWriter ow = new ObjectMapper().writer();
                    SingletonSeguidorEstadistica se = SingletonSeguidorEstadistica.getInstance();
                    ListadoLocalidades l = se.encontrarLocalidades(soloSinHogar, desde, hasta);
                    json = ow.writeValueAsString(l);
                } catch (JsonProcessingException e) {
                    json = "Error, algo salio mal";
                }
                ctx.result(json);
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
