package views;

import domain.auth.Usuario;
import domain.persona.*;
import domain.rol.Colaborador;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import persistence.BDUtils;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.*;

public class UI_Registrar implements Handler {
    public void handle(Context ctx) throws Exception {
        Map<String, Object> model = new HashMap();
        ctx.render("page-register.hbs", model);
    }

    public void registrarPersona(Context ctx) throws Exception {
        String nombre = ctx.formParam("nombre");
        String apellido = ctx.formParam("apellido");
        String sexo = ctx.formParam("sexo");
        String genero = ctx.formParam("genero");
        String fecha = ctx.formParam("fecha");
        String direccion = ctx.formParam("direccion");
        String tipoDocumento = ctx.formParam("tipoDocumento");
        String documento = ctx.formParam("documento");
        String usuario = ctx.formParam("usuario");
        String contra = ctx.formParam("contra");
        String repetirContra = ctx.formParam("repetirContra");

        List<MedioDeContacto> medios = new ArrayList<MedioDeContacto>();
        for(String k : ctx.formParamMap().keySet().stream().filter(param -> param.contains("type")).toList()){
            Integer indice = Integer.valueOf(k.substring(8,9));
            MedioDeContacto m = null;
            String tipo = ctx.formParam(k);
            String valor = ctx.formParam("contact["+indice+"][value]");
            if(Objects.equals(tipo, "email")){
                m = new Email(valor);
            } else if(Objects.equals(tipo, "telefono")){
                m = new Telefono(valor);
            } else if(Objects.equals(tipo, "whatsapp")){
                m = new WhatsApp(valor);
            }
            medios.add(m);
        }

        Documento nuevoDocumento = new Documento(tipoDocumento, documento);
        PersonaFisica nuevaPersona = new PersonaFisica(nombre, medios, direccion, nuevoDocumento, apellido, sexo, genero, LocalDate.parse(fecha));
        Colaborador nuevoColaborador = new Colaborador(nuevaPersona);
        Usuario nuevoUsuario = new Usuario(usuario, contra, nuevoColaborador);

        EntityManager em = BDUtils.getEntityManager();
        BDUtils.comenzarTransaccion(em);

        em.persist(nuevoDocumento);
        em.persist(nuevaPersona);
        em.persist(nuevoColaborador);
        em.persist(nuevoUsuario);

        BDUtils.commit(em);
    }
}
