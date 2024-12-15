package views;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import domain.api.ListadoLocalidades;
import domain.api.Localizacion;
import domain.heladera.Ubicacion;
import domain.registro.SingletonSeguidorEstadistica;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.HttpStatus;
import io.javalin.json.JavalinJackson;
import io.javalin.rendering.JavalinRenderer;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import persistence.ArchivosUtils;
import persistence.Demo;


public class VistasJavalin {
        public static void main(String[] args) throws JsonProcessingException {

            initTemplateEngine();

            //Demo.main(null);
            //Demo.personas(null);
            //Demo.colaboraciones(null);
            Demo.servicios(null);

            JavalinJackson.defaultMapper();

            Integer port = Integer.parseInt(System.getProperty("port", "8001"));
            Javalin app = Javalin.create(config -> {
                config.staticFiles.add("/front/rentrega4");
            }).start(port);

            app.get("/uploads/*", ctx -> {
                String subpath = ctx.path().substring("/uploads".length());
                Path imagePath = Paths.get(ArchivosUtils.getInstance().getUploadsPath(), subpath);
                if (Files.exists(imagePath)) {
                    ctx.contentType("image/");
                    ctx.result(Files.newInputStream(imagePath));
                } else {
                    ctx.status(404).result("Archivo no encontrado");
                }
            });

            app.get("/", ctx -> {
                ctx.redirect("/page-login");
            });

//======================INDEX================================
            UI_Index UIndex = new UI_Index();

            app.get("/index", UIndex);

//======================VIANDA================================
            UI_Vianda UIVianda = new UI_Vianda();

            app.get("/vianda", UIVianda);
            app.post("/vianda", UIVianda::agregarVianda);

//======================TRASLADO================================
            UI_Traslado UITraslado = new UI_Traslado();

            app.get("/traslado", UITraslado);
            app.post("/traslado", UITraslado::trasladarCantViandas);

//======================DINERO================================
            UI_Dinero UIDinero = new UI_Dinero();

            app.get("/dinero", UIDinero);
            app.post("/dinero", UIDinero::agregarDonacion);

//======================FALLAS================================
            app.get("/fallas", ctx -> {
                ctx.render("fallas.hbs");
            });

//======================HELADERAS-A================================
            UI_HeladerasA UIHeladerasA = new UI_HeladerasA();

            app.get("/heladeras-a", UIHeladerasA);
            app.post("/heladeras-a", UIHeladerasA::botonInfo);

//====================== HELADERAS-O================================
            UI_HeladerasO UIHeladerasO = new UI_HeladerasO();

            app.get("/heladeras-o", UIHeladerasO);

//====================== HELADERAS-P================================
            UI_HeladerasP UIHeladerasP = new UI_HeladerasP();

            app.get("/heladeras-p", UIHeladerasP);
            app.post("/heladeras-p", UIHeladerasP::botonesInfo);

//======================
            app.get("/landing", ctx -> {
                ctx.render("landing.hbs");
            });

//======================
            app.get("/migracion", ctx -> {
                ctx.render("migracion.hbs");
            });

//====================== OFERTAS
            UI_Ofertas UIOfertas = new UI_Ofertas();

            app.get("/ofertas", UIOfertas);

//======================
            UI_Oferta UIOferta = new UI_Oferta();

            app.get("/oferta", UIOferta);
            app.post("/nueva-oferta", UIOferta::nuevaOferta);

//====================== LOGIN ================================
            UI_Login UILogin = new UI_Login();

            app.get("/page-login", UILogin);
            app.post("/login", UILogin::login);
            app.post("/logout", UILogin::logout);

//======================
            UI_Registrar UIRegistrar = new UI_Registrar();

            app.get("/page-register", UIRegistrar);
            app.post("/registrar-p", UIRegistrar::registrarPersona);
            app.post("/registrar-o", UIRegistrar::registrarOrg);
            app.post("/registrar-t", UIRegistrar::registrarTecnico);

//====================== PUNTOS ================================
            UI_Puntos UIPuntos = new UI_Puntos();

            app.get("/puntos",UIPuntos);
            app.post("/puntos/canje",UIPuntos::canjearPuntos);

//====================== REGISTRAR PERSONAS ================================
            UI_RegistrarPersona UIregistrarPersona = new UI_RegistrarPersona();

            app.get("/registrar-persona", UIregistrarPersona);
            app.post("/registrar-persona", UIregistrarPersona::agregarPersona);

//====================== REPORTES
            UI_Reporte repo = new UI_Reporte();
            app.get("/reportes",repo);

//======================
            app.get("/api/localizacion", ctx -> {
                ctx.result(Localizacion.localizar(ctx));
            });
        }

        private static void initTemplateEngine() {
            Handlebars handlebars = new Handlebars();

            handlebars.registerHelpers(new HelperSource());

            JavalinRenderer.register(
                (path, model, context) -> { // Función que renderiza el template
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
