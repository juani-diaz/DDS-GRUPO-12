package views;

import domain.auth.JwtUtil;
import domain.auth.Usuario;
import domain.contra.ValidadorContras;
import domain.persona.*;
import domain.rol.Colaborador;
import domain.rol.LocalidadesTecnico;
import domain.rol.Rol;
import domain.rol.Tecnico;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.jsonwebtoken.Claims;
import obs.RespuestaCliente;
import persistence.Repos.RepoColaborador;
import persistence.Repos.RepoUsuarios;

import javax.persistence.EntityManager;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;

import static persistence.BDUtils.*;

public class UI_Registrar implements Handler {
    public void handle(Context ctx) throws Exception {
        Map<String, Object> model = new HashMap();
        LocalidadesTecnico lt = new LocalidadesTecnico();
        String token = ctx.cookie("Auth");
        Claims claims= JwtUtil.getClaimsFromToken(token);
        if(claims==null){
            model.put("listadoLocalidades", lt);
            ctx.render("page-register.hbs", model);
        }else{
            model.put("listadoLocalidades", lt);
            ctx.render("page-register-google.hbs", model);
        }

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

        ValidadorContras a = new ValidadorContras();
        if(!a.validarContra(contra, repetirContra)){
            RespuestaCliente.error(null, "/page-register", "Error en la validacion de contraseñas", ctx);
            return;
        }

        PersonaFisica nuevaPersona = new PersonaFisica();

        List<MedioDeContacto> medios = new ArrayList<MedioDeContacto>();
        for(String k : ctx.formParamMap().keySet().stream().filter(param -> param.contains("type")).toList()){
            Integer indice = Integer.valueOf(k.substring(8,9));
            MedioDeContacto m = null;
            String tipo = ctx.formParam(k);
            String valor = ctx.formParam("contact["+indice+"][value]");
            if(Objects.equals(tipo, "email")){
                m = new EmailDir(valor);
                m.setPersona(nuevaPersona);
            } else if(Objects.equals(tipo, "telefono")){
                m = new Telefono(valor);
                m.setPersona(nuevaPersona);
            } else if(Objects.equals(tipo, "whatsapp")){
                m = new WhatsApp(valor);
                m.setPersona(nuevaPersona);
            }
            medios.add(m);
        }

        Documento nuevoDocumento = new Documento(tipoDocumento, documento);
        nuevaPersona.setNombre(nombre);
        nuevaPersona.setMediosDeContacto(medios);
        nuevaPersona.setDireccion(direccion);
        nuevaPersona.setDocumento(nuevoDocumento);
        nuevaPersona.setGenero(genero);
        nuevaPersona.setApellido(apellido);
        nuevaPersona.setFechaNacimiento(LocalDate.parse(fecha));
        nuevaPersona.setSexo(sexo);
        Colaborador nuevoColaborador = new Colaborador(nuevaPersona);
        Usuario nuevoUsuario = new Usuario(usuario, contra, nuevoColaborador);

        RepoUsuarios r = RepoUsuarios.getInstance();
        r.add_Usuario(nuevoUsuario);

        RespuestaCliente.exito(null, "/page-login", "Registrado con exito", ctx);
    }

    public void registrarOrg(Context ctx) throws Exception {
        String razonSocial = ctx.formParam("razonSocial");
        String rubro = ctx.formParam("rubro");
        String tipoJur = ctx.formParam("tipoJur");
        EnumTipoPersonaJuridica tipoJuridica = null;
        if(Objects.equals(tipoJur, "ONG")){
            tipoJuridica = EnumTipoPersonaJuridica.ONG;
        } else if(Objects.equals(tipoJur, "EMPRESA")){
            tipoJuridica = EnumTipoPersonaJuridica.EMPRESA;
        } else if(Objects.equals(tipoJur, "INSTITUCION")){
            tipoJuridica = EnumTipoPersonaJuridica.INSTITUCION;
        } else if(Objects.equals(tipoJur, "GUBERNAMENTAL")){
            tipoJuridica = EnumTipoPersonaJuridica.GUBERNAMENTAL;
        }
        String direccion = ctx.formParam("direccion");
        String tipoDocumento = ctx.formParam("tipoDocumento");
        String documento = ctx.formParam("documento");
        String usuario = ctx.formParam("usuario");
        String contra = ctx.formParam("contra");
        String repetirContra = ctx.formParam("repetirContra");

        ValidadorContras a = new ValidadorContras();
        if(!a.validarContra(contra, repetirContra)){
            RespuestaCliente.error(null, "/page-register", "Error en la validacion de contraseñas", ctx);
            return;
        }

        Documento nuevoDocumento = new Documento(tipoDocumento,documento);

        List<MedioDeContacto> medios = new ArrayList<MedioDeContacto>();
        PersonaJuridica nuevaOrg = new PersonaJuridica(razonSocial, tipoJuridica, rubro, direccion, nuevoDocumento, medios);

        for(String k : ctx.formParamMap().keySet().stream().filter(param -> param.contains("type")).toList()){
            Integer indice = Integer.valueOf(k.substring(8,9));
            MedioDeContacto m = null;
            String tipo = ctx.formParam(k);
            String valor = ctx.formParam("contact["+indice+"][value]");
            if(Objects.equals(tipo, "email")){
                m = new EmailDir(valor);
            } else if(Objects.equals(tipo, "telefono")){
                m = new Telefono(valor);
            } else if(Objects.equals(tipo, "whatsapp")){
                m = new WhatsApp(valor);
            }
            nuevaOrg.agregarMedioDeContacto(m);
        }

        Colaborador nuevoColaborador = new Colaborador(nuevaOrg);
        Usuario nuevoUsuario = new Usuario(usuario, contra, nuevoColaborador);

        RepoUsuarios r = RepoUsuarios.getInstance();
        r.add_Usuario(nuevoUsuario);

        RespuestaCliente.exito(null, "/page-login", "Registrado con exito", ctx);
    }

    public void registrarTecnico(Context ctx) throws Exception {
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

        ValidadorContras a = new ValidadorContras();
        if(!a.validarContra(contra, repetirContra)){
            RespuestaCliente.error(null, "/page-register", "Error en la validacion de contraseñas", ctx);
            return;
        }

        List<MedioDeContacto> medios = new ArrayList<MedioDeContacto>();
        PersonaFisica nuevaPersona = new PersonaFisica();
        for(String k : ctx.formParamMap().keySet().stream().filter(param -> param.contains("type")).toList()){
            Integer indice = Integer.valueOf(k.substring(8,9));
            MedioDeContacto m = null;
            String tipo = ctx.formParam(k);
            String valor = ctx.formParam("contact["+indice+"][value]");
            if(Objects.equals(tipo, "email")){
                m = new EmailDir(valor);
            } else if(Objects.equals(tipo, "telefono")){
                m = new Telefono(valor);
            } else if(Objects.equals(tipo, "whatsapp")){
                m = new WhatsApp(valor);
            }
            medios.add(m);
        }

        LocalidadesTecnico lt = new LocalidadesTecnico();
        List<String> area = new ArrayList<>(ctx.formParamMap().keySet().stream().filter(param -> lt.nombres().contains(param)).toList());

        Documento nuevoDocumento = new Documento(tipoDocumento, documento);
        nuevaPersona.setNombre(nombre);
        nuevaPersona.setMediosDeContacto(medios);
        nuevaPersona.setDireccion(direccion);
        nuevaPersona.setDocumento(nuevoDocumento);
        nuevaPersona.setGenero(genero);
        nuevaPersona.setApellido(apellido);
        nuevaPersona.setFechaNacimiento(LocalDate.parse(fecha));
        nuevaPersona.setSexo(sexo);
        Tecnico nuevoTecnico = new Tecnico(nuevaPersona, area);
        Usuario nuevoUsuario = new Usuario(usuario, contra, nuevoTecnico);

        RepoUsuarios r = RepoUsuarios.getInstance();
        r.add_Usuario(nuevoUsuario);

        RespuestaCliente.exito(null, "/page-login", "Registrado con exito", ctx);
    }

    public void registrarPersonaGoogle(Context ctx) throws Exception {
        String nombre = ctx.formParam("nombre");
        String apellido = ctx.formParam("apellido");
        String sexo = ctx.formParam("sexo");
        String genero = ctx.formParam("genero");
        String fecha = ctx.formParam("fecha");
        String direccion = ctx.formParam("direccion");
        String tipoDocumento = ctx.formParam("tipoDocumento");
        String documento = ctx.formParam("documento");

        PersonaFisica nuevaPersona = new PersonaFisica();

        List<MedioDeContacto> medios = new ArrayList<MedioDeContacto>();
        for(String k : ctx.formParamMap().keySet().stream().filter(param -> param.contains("type")).toList()){
            Integer indice = Integer.valueOf(k.substring(8,9));
            MedioDeContacto m = null;
            String tipo = ctx.formParam(k);
            String valor = ctx.formParam("contact["+indice+"][value]");
            if(Objects.equals(tipo, "email")){
                m = new EmailDir(valor);
                m.setPersona(nuevaPersona);
            } else if(Objects.equals(tipo, "telefono")){
                m = new Telefono(valor);
                m.setPersona(nuevaPersona);
            } else if(Objects.equals(tipo, "whatsapp")){
                m = new WhatsApp(valor);
                m.setPersona(nuevaPersona);
            }
            medios.add(m);
        }

        Documento nuevoDocumento = new Documento(tipoDocumento, documento);
        String token = ctx.cookie("Auth");
        token = token.replace("Bearer", "");
        String decodedToken = URLDecoder.decode(token, StandardCharsets.UTF_8);
        Usuario u = JwtUtil.validateTokenAndGetUser(decodedToken);
        System.out.println("medios:");
        System.out.println(medios);

        nuevaPersona.setNombre(nombre);
        nuevaPersona.setMediosDeContacto(medios);
        nuevaPersona.setDireccion(direccion);
        nuevaPersona.setDocumento(nuevoDocumento);
        nuevaPersona.setGenero(genero);
        nuevaPersona.setApellido(apellido);
        nuevaPersona.setFechaNacimiento(LocalDate.parse(fecha));
        nuevaPersona.setSexo(sexo);
        Colaborador nuevoColaborador = new Colaborador(nuevaPersona);
        RepoColaborador c = RepoColaborador.getInstance();

        c.add_Colaborador(nuevoColaborador);

        u.setRol(nuevoColaborador);

        RepoUsuarios r = RepoUsuarios.getInstance();
        r.update_Usuario(u);

        RespuestaCliente.exito(null, "/page-login", "Registrado con exito", ctx);
    }

    public void registrarOrgGoogle(Context ctx) throws Exception {
        String razonSocial = ctx.formParam("razonSocial");
        String rubro = ctx.formParam("rubro");
        String tipoJur = ctx.formParam("tipoJur");
        EnumTipoPersonaJuridica tipoJuridica = null;
        if(Objects.equals(tipoJur, "ONG")){
            tipoJuridica = EnumTipoPersonaJuridica.ONG;
        } else if(Objects.equals(tipoJur, "EMPRESA")){
            tipoJuridica = EnumTipoPersonaJuridica.EMPRESA;
        } else if(Objects.equals(tipoJur, "INSTITUCION")){
            tipoJuridica = EnumTipoPersonaJuridica.INSTITUCION;
        } else if(Objects.equals(tipoJur, "GUBERNAMENTAL")){
            tipoJuridica = EnumTipoPersonaJuridica.GUBERNAMENTAL;
        }
        String direccion = ctx.formParam("direccion");
        String tipoDocumento = ctx.formParam("tipoDocumento");
        String documento = ctx.formParam("documento");


        Documento nuevoDocumento = new Documento(tipoDocumento,documento);


        List<MedioDeContacto> medios = new ArrayList<MedioDeContacto>();
        PersonaJuridica nuevaOrg = new PersonaJuridica(razonSocial, tipoJuridica, rubro, direccion, nuevoDocumento, medios);

        for(String k : ctx.formParamMap().keySet().stream().filter(param -> param.contains("type")).toList()){
            Integer indice = Integer.valueOf(k.substring(8,9));
            MedioDeContacto m = null;
            String tipo = ctx.formParam(k);
            String valor = ctx.formParam("contact["+indice+"][value]");
            if(Objects.equals(tipo, "email")){
                m = new EmailDir(valor);
            } else if(Objects.equals(tipo, "telefono")){
                m = new Telefono(valor);
            } else if(Objects.equals(tipo, "whatsapp")){
                m = new WhatsApp(valor);
            }
            nuevaOrg.agregarMedioDeContacto(m);
        }

        Colaborador nuevoColaborador = new Colaborador(nuevaOrg);
        String token = ctx.cookie("Auth");
        token = token.replace("Bearer", "");
        String decodedToken = URLDecoder.decode(token, StandardCharsets.UTF_8);
        Usuario u = JwtUtil.validateTokenAndGetUser(decodedToken);
        u.setRol(nuevoColaborador);
        EntityManager em = getEm();
        comenzarTransaccion(em);
        em.persist(nuevoDocumento);
        em.persist(nuevaOrg);
        em.persist(nuevoColaborador);
        commit(em);
        RepoUsuarios r = RepoUsuarios.getInstance();
        r.update_Usuario(u);

        RespuestaCliente.exito(null, "/page-login", "Registrado con exito", ctx);
    }

    public void registrarTecnicoGoogle(Context ctx) throws Exception {
        String nombre = ctx.formParam("nombre");
        String apellido = ctx.formParam("apellido");
        String sexo = ctx.formParam("sexo");
        String genero = ctx.formParam("genero");
        String fecha = ctx.formParam("fecha");
        String direccion = ctx.formParam("direccion");
        String tipoDocumento = ctx.formParam("tipoDocumento");
        String documento = ctx.formParam("documento");
        PersonaFisica nuevaPersona = new PersonaFisica();

        List<MedioDeContacto> medios = new ArrayList<MedioDeContacto>();
        for(String k : ctx.formParamMap().keySet().stream().filter(param -> param.contains("type")).toList()){
            Integer indice = Integer.valueOf(k.substring(8,9));
            MedioDeContacto m = null;
            String tipo = ctx.formParam(k);
            String valor = ctx.formParam("contact["+indice+"][value]");
            if(Objects.equals(tipo, "email")){
                m = new EmailDir(valor);
                m.setPersona(nuevaPersona);
            } else if(Objects.equals(tipo, "telefono")){
                m = new Telefono(valor);
                m.setPersona(nuevaPersona);
            } else if(Objects.equals(tipo, "whatsapp")){
                m = new WhatsApp(valor);
                m.setPersona(nuevaPersona);
            }
            medios.add(m);
        }

        LocalidadesTecnico lt = new LocalidadesTecnico();
        System.out.println(ctx.formParamMap().keySet());
        List<String> area = new ArrayList<>(ctx.formParamMap().keySet().stream().filter(param -> lt.nombres().contains(param)).toList());
        System.out.println(area);

        Documento nuevoDocumento = new Documento(tipoDocumento, documento);
        nuevaPersona.setNombre(nombre);
        nuevaPersona.setMediosDeContacto(medios);
        nuevaPersona.setDireccion(direccion);
        nuevaPersona.setDocumento(nuevoDocumento);
        nuevaPersona.setGenero(genero);
        nuevaPersona.setApellido(apellido);
        nuevaPersona.setFechaNacimiento(LocalDate.parse(fecha));
        nuevaPersona.setSexo(sexo);
        Tecnico nuevoTecnico = new Tecnico(nuevaPersona, area);
        EntityManager em = getEm();
        comenzarTransaccion(em);
        em.persist(nuevoDocumento);
        em.persist(nuevaPersona);
        em.persist(nuevoTecnico);
        commit(em);
        String token = ctx.cookie("Auth");
        token = token.replace("Bearer", "");
        String decodedToken = URLDecoder.decode(token, StandardCharsets.UTF_8);
        Usuario u = JwtUtil.validateTokenAndGetUser(decodedToken);
        u.setRol(nuevoTecnico);
        RepoUsuarios r = RepoUsuarios.getInstance();
        r.update_Usuario(u);

        RespuestaCliente.exito(null, "/page-login", "Registrado con exito", ctx);
    }
}
