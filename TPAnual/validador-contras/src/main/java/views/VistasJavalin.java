package views;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.FileTemplateLoader;
import domain.api.Localizacion;
import domain.auth.AccesoUsuarios;
import domain.auth.LinkMenu;
import domain.auth.PermisosMetodo;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;
import io.javalin.json.JavalinJackson;
import io.javalin.rendering.JavalinRenderer;
import persistence.ArchivosUtils;
import persistence.Demo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;


public class VistasJavalin {
        public static void main(String[] args) throws JsonProcessingException {

            initTemplateEngine();

            //Demo.main(null);
            //Demo.personas(null);
            //Demo.colaboraciones(null);
            //Demo.servicios(null);

            JavalinJackson.defaultMapper();

            Integer port = Integer.parseInt(System.getProperty("port", "8001"));
            Javalin app = Javalin.create(config -> {
                config.staticFiles.add("/front/rentrega4");
            }).start(port);

            app.before(ctx -> {
                AccesoUsuarios.getInstance().revisarPermiso(ctx);
            });

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

            AccesoUsuarios acceso = AccesoUsuarios.getInstance();

//====================== INDEX
            UI_Index UIndex = new UI_Index();

            app.get("/index", UIndex);
            acceso.agregarRuta(new PermisosMetodo("index", true, true, true, true));

            app.get("/landing", ctx -> {
                ctx.render("landing.hbs");
            });

//====================== VIANDA
            UI_Vianda UIVianda = new UI_Vianda();

            app.get("/vianda", UIVianda);
            app.post("/vianda", UIVianda::agregarVianda);
            acceso.agregarRuta(new LinkMenu(
                    "vianda",
                    "Donar Vianda",
                    "icon-cup",
                    true,
                    false,
                    false,
                    false
            ));

//====================== TRASLADO
            UI_Traslado UITraslado = new UI_Traslado();

            app.get("/traslado", UITraslado);
            app.post("/traslado", UITraslado::trasladarCantViandas);
            acceso.agregarRuta(new LinkMenu(
                    "traslado",
                    "Traslados",
                    "icon-direction",
                    true,
                    false,
                    false,
                    false
            ));

//====================== REGISTRAR PERSONAS
            UI_RegistrarPersona UIregistrarPersona = new UI_RegistrarPersona();

            app.get("/registrar-persona", UIregistrarPersona);
            app.post("/registrar-persona", UIregistrarPersona::agregarPersona);
            app.post("/solicitar-tarjeta", UIregistrarPersona::solicitarTarjeta);
            acceso.agregarRuta(new PermisosMetodo("solicitar-tarjeta", true, false, false, false));
            acceso.agregarRuta(new LinkMenu(
                    "registrar-persona",
                    "Registrar Personas",
                    "icon-user-follow",
                    true,
                    false,
                    false,
                    false
            ));

//====================== DINERO
            UI_Dinero UIDinero = new UI_Dinero();

            app.get("/dinero", UIDinero);
            app.post("/dinero", UIDinero::agregarDonacion);
            acceso.agregarRuta(new LinkMenu(
                    "dinero",
                    "Donar Dinero",
                    "icon-wallet",
                    true,
                    true,
                    false,
                    false
            ));

//====================== OFERTAS
            UI_Ofertas UIOfertas = new UI_Ofertas();

            app.get("/ofertas", UIOfertas);
            acceso.agregarRuta(new LinkMenu(
                    "ofertas",
                    "Ofertas",
                    "icon-present",
                    false,
                    true,
                    false,
                    false
            ));

//====================== NUEVA OFERTA
            UI_Oferta UIOferta = new UI_Oferta();

            app.get("/oferta", UIOferta);
            acceso.agregarRuta(new PermisosMetodo("oferta", false, true, false, false));
            app.post("/nueva-oferta", UIOferta::nuevaOferta);
            acceso.agregarRuta(new PermisosMetodo("nueva-oferta", false, true, false, false));

//====================== HELADERAS-A
            UI_HeladerasA UIHeladerasA = new UI_HeladerasA();

            app.get("/heladeras-a", UIHeladerasA);
            app.post("/heladeras-a", UIHeladerasA::botonInfo);
            acceso.agregarRuta(new LinkMenu(
                    "heladeras-a",
                    "Heladeras",
                    "icon-heladera",
                    false,
                    false,
                    false,
                    true
            ));

//====================== HELADERAS-O
            UI_HeladerasO UIHeladerasO = new UI_HeladerasO();

            app.get("/heladeras-o", UIHeladerasO);
            acceso.agregarRuta(new LinkMenu(
                    "heladeras-o",
                    "Heladeras",
                    "icon-heladera",
                    false,
                    true,
                    false,
                    false
            ));

//====================== HELADERAS-P
            UI_HeladerasP UIHeladerasP = new UI_HeladerasP();

            app.get("/heladeras-p", UIHeladerasP);
            app.post("/heladeras-p", UIHeladerasP::botonesInfo);
            acceso.agregarRuta(new LinkMenu(
                    "heladeras-p",
                    "Heladeras",
                    "icon-heladera",
                    true,
                    false,
                    false,
                    false
            ));

//====================== PUNTOS
            UI_Puntos UIPuntos = new UI_Puntos();

            app.get("/puntos",UIPuntos);
            app.post("/puntos",UIPuntos::canjearPuntos);
            acceso.agregarRuta(new LinkMenu(
                    "puntos",
                    "Puntos",
                    "icon-diamond",
                    true,
                    true,
                    false,
                    false
            ));

//====================== REPORTES
            UI_Reporte repo = new UI_Reporte();

            app.get("/reportes",repo);
            acceso.agregarRuta(new LinkMenu(
                    "reportes",
                    "Reportes",
                    "icon-chart",
                    false,
                    false,
                    false,
                    true
            ));

//====================== MIGRACIÓN
            UI_Migracion UI_Migracion = new UI_Migracion();
            app.get("/migracion",UI_Migracion);
            app.post("/migracion", UI_Migracion::cargarArchivoCSV);
            acceso.agregarRuta(new LinkMenu(
                    "migracion",
                    "Migración",
                    "icon-cloud-upload",
                    false,
                    false,
                    false,
                    true
            ));

//====================== FALLAS
            app.get("/fallas", ctx -> {
                ctx.render("fallas.hbs");
            });

//====================== LOGIN
            UI_Login UILogin = new UI_Login();

            app.get("/page-login", UILogin);
            app.post("/login", UILogin::login);
            app.post("/logout", UILogin::logout);

//====================== REGISTRO
            UI_Registrar UIRegistrar = new UI_Registrar();

            app.get("/page-register", UIRegistrar);
            app.post("/registrar-p", UIRegistrar::registrarPersona);
            app.post("/registrar-o", UIRegistrar::registrarOrg);
            app.post("/registrar-t", UIRegistrar::registrarTecnico);

//====================== API
            app.get("/api/localizacion", ctx -> {
                ctx.result(Localizacion.localizar(ctx));
            });

//====================== NO ENCONTRADO
            app.error(404, ctx -> {
                ctx.redirect("/denegado");
            });
        }

        private static void initTemplateEngine() {
            Handlebars handlebars = new Handlebars();

            handlebars.registerHelpers(new HelperSource());

            JavalinRenderer.register(
                (path, model, context) -> {
                    Template template = null;
                    try {
                        template = handlebars.compile("templates/" + path.replace(".hbs", ""));
                        return template.apply(model);
                    } catch (IOException e) {
                        //
                        e.printStackTrace();
                        context.status(HttpStatus.NOT_FOUND);
                        return "No se encuentra la pagina indicada...";
                    }
                }, ".hbs"
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
