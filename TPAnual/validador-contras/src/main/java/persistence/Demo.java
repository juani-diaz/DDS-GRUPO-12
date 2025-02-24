package persistence;

import domain.auth.Usuario;
import domain.colaboraciones.*;
import domain.heladera.EnumEstadoHeladera;
import domain.heladera.Heladera;
import domain.heladera.Ubicacion;
import domain.incidente.EnumTipoDeFalla;
import domain.incidente.IncidenteAlarma;
import domain.incidente.IncidenteFallaTecnica;
import domain.persona.*;
import domain.rol.*;
import domain.servicios.Catalogo;
import domain.vianda.EnumEstadoVianda;
import domain.vianda.Vianda;
import domain.vianda.ViandaRecogida;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Demo {

    public static void main(String[] args) {

        Ubicacion direccion = new Ubicacion("BSAS","Villa Real", "1011", "200", "GUEMES" ,"4426");
        Heladera heladera = new Heladera("heladera2",direccion, 20, LocalDate.now(), 5f, 10.0f, EnumEstadoHeladera.INACTIVA_POR_FALLA);

        Ubicacion direccionMedrano = new Ubicacion("BSAS","Almagro", "-35", "-58", "Avenida Medrano" ,"951");
        Heladera heladeraMedrano = new Heladera("heladeraMedrano",direccionMedrano, 20, LocalDate.now(), 5f, 10.0f, EnumEstadoHeladera.DISPONIBLE);

        Ubicacion direccionConstitucion = new Ubicacion("BSAS", "Constitucion", "-34", "-58", "Avenida Brasil", "1120");
        Heladera heladeraConstitucion = new Heladera("heladeraConstitucion", direccionConstitucion, 150, LocalDate.now(), 5f, 10.0f, EnumEstadoHeladera.INACTIVA_POR_ALERTA);

        Ubicacion direccionLomasDeZamora = new Ubicacion("BSAS","Lomas de Zamora", "-34.760161891020516", "-58.396944175638076", "Carlos Pellegrini" ,"50");
        Heladera heladeraLomasDeZamora = new Heladera("estacion de Lomas",direccionLomasDeZamora, 50, LocalDate.now(), 5f, 10.0f, EnumEstadoHeladera.DISPONIBLE);

        Ubicacion direccionSubteB = new Ubicacion("BSAS","Almagro", "-34.60316616966819", "-58.421152913613476", "Avenida Corrientes" ,"1250");
        Heladera heladeraSubteB = new Heladera("Subte B",direccionSubteB, 50, LocalDate.now(), 5f, 10.0f, EnumEstadoHeladera.DISPONIBLE);

        Ubicacion direccionSubteC = new Ubicacion("BSAS","Chacarita", "-34.584036474171214", "-58.46678133733317", "Avenida Triunvirato" ,"1450");
        Heladera heladeraSubteC = new Heladera("Subte C",direccionSubteC, 50, LocalDate.now(), 5f, 10.0f, EnumEstadoHeladera.DISPONIBLE);

        Vianda vianda = new Vianda("carne", LocalDate.now(), LocalDate.now(),  "100", 300f, EnumEstadoVianda.ENTREGADO);
        Vianda vianda2 = new Vianda("papa", LocalDate.now(), LocalDate.now(),  "100", 300f, EnumEstadoVianda.ENTREGADO);
        Vianda viandaHeladeraMedrano = new Vianda("carne con fideos",LocalDate.now(),LocalDate.now(), "450", 350f,EnumEstadoVianda.NO_ENTREGADO);
        Vianda viandaLomasDeZamora = new Vianda("fideos", LocalDate.now(), LocalDate.now(),  "300", 300f, EnumEstadoVianda.NO_ENTREGADO);
        Vianda viandaSubteB = new Vianda("carne al horno con papas", LocalDate.now(), LocalDate.now(),  "420", 300f, EnumEstadoVianda.NO_ENTREGADO);
        Vianda viandaSubteC = new Vianda("guiso de albondigas", LocalDate.now(), LocalDate.now(),  "550", 300f, EnumEstadoVianda.NO_ENTREGADO);

        vianda.setHeladera(heladera);
        vianda2.setHeladera(heladera);
        viandaHeladeraMedrano.setHeladera(heladeraMedrano);
        viandaLomasDeZamora.setHeladera(heladeraLomasDeZamora);
        viandaSubteB.setHeladera(heladeraSubteB);
        viandaSubteC.setHeladera(heladeraSubteC);

        List<Vianda> viandas = new ArrayList<>();
        viandas.add(vianda);
        viandas.add(vianda2);

        heladera.ingresarViandas(viandas);

        Ubicacion direccion2 = new Ubicacion("BSAS","Caballito", "2011", "100", "LORIA" ,"23");
        Heladera heladera2 = new Heladera("heladera22",direccion2, 20, LocalDate.now(), 5f, 10.0f, EnumEstadoHeladera.INACTIVA_POR_FALLA);

        Vianda vianda3 = new Vianda("carne", LocalDate.now(), LocalDate.now(),  "100", 300f, EnumEstadoVianda.ENTREGADO);
        Vianda vianda4 = new Vianda("papa", LocalDate.now(), LocalDate.now(),  "100", 300f, EnumEstadoVianda.ENTREGADO);

        vianda3.setHeladera(heladera);
        vianda4.setHeladera(heladera);
        List<Vianda> viandas2 = new ArrayList<Vianda>();
        viandas2.add(vianda3);
        viandas2.add(vianda4);

        heladera2.ingresarViandas(viandas2);

        Tarjeta t1 = new Tarjeta("abc");
        Vulnerable v1 = new Vulnerable(LocalDate.now(), EnumSituacionCalle.NO_POSEE_HOGAR, 0, new ArrayList<ViandaRecogida>(), t1, new ArrayList<UsoDeTarjeta>(), 4);
        Documento d1 = new Documento("DNI", "43126980");
        v1.setPersona(new PersonaFisica("tomas", null, null, d1, "cerezo", "m", "m", LocalDate.now()));
        //v1.retirarVianda(0, heladera);

        Tarjeta t2 = new Tarjeta("abcd");
        Vulnerable v2 = new Vulnerable(LocalDate.now(), EnumSituacionCalle.NO_POSEE_HOGAR, 2, new ArrayList<ViandaRecogida>(), t2, new ArrayList<UsoDeTarjeta>(), 8);
        Documento d2 = new Documento("DNI", "44601269");
        v2.setPersona(new PersonaFisica("gabriela", null, null, d2, "varela", "f", "f", LocalDate.now()));
        //v2.retirarVianda(0, heladera2);

        Tarjeta t3 = new Tarjeta("abcde");
        Vulnerable v3 = new Vulnerable(LocalDate.now(), EnumSituacionCalle.POSEE_HOGAR, 0, new ArrayList<ViandaRecogida>(), t3, new ArrayList<UsoDeTarjeta>(), 4);
        Documento d3 = new Documento("DNI", "45142069");
        v3.setPersona(new PersonaFisica("agustin", null, "MASA 38", d3, "bevilacua", "m", "t", LocalDate.now()));
        //v3.retirarVianda(0, heladera);

        Tarjeta tarjeta4 = new Tarjeta("tarjeta4");
        //Vulnerable v4 = new Vulnerable(LocalDate.now(), EnumSituacionCalle.NO_POSEE_HOGAR, 2, new ArrayList<ViandaRecogida>(), t2, new ArrayList<UsoDeTarjeta>(), 8);
        Documento d4 = new Documento("DNI", "46260391");
        PersonaFisica personaFisica4 = new PersonaFisica("ana", null, null, d4, "dinardi", "f", "f", LocalDate.now());
        ViandaRecogida viandaRecogida4 = new ViandaRecogida();
        viandaRecogida4.setViandaRecogida(vianda4);
        ArrayList<ViandaRecogida> viandaRecogidaArrayList = new ArrayList<>();
        viandaRecogidaArrayList.add(viandaRecogida4);
        Vulnerable vulnerable4 = new Vulnerable();
            vulnerable4.setFechaRegistro(LocalDate.now());
            vulnerable4.setSituacionCalle(EnumSituacionCalle.NO_POSEE_HOGAR);
            vulnerable4.setMenoresACargo(2);
            vulnerable4.setTarjeta(tarjeta4);
            vulnerable4.setUsosRestantesPorDia(8);
            vulnerable4.setPersona(personaFisica4);
            vulnerable4.setViandasTomadas(viandaRecogidaArrayList);


        EntityManager em = BDUtils.getEntityManager();
        BDUtils.comenzarTransaccion(em);

        em.persist(direccion);
        em.persist(heladera);

        em.persist(direccionMedrano);
        em.persist(heladeraMedrano);

        em.persist(direccionConstitucion);
        em.persist(heladeraConstitucion);

        em.persist(direccionLomasDeZamora);
        em.persist(heladeraLomasDeZamora);

        em.persist(direccionSubteB);
        em.persist(heladeraSubteB);

        em.persist(direccionSubteC);
        em.persist(heladeraSubteC);

          em.persist(vianda);
        em.persist(vianda2);
        em.persist(viandaHeladeraMedrano);
        em.persist(viandaLomasDeZamora);
        em.persist(viandaSubteB);
        em.persist(viandaSubteC);

        em.persist(direccion2);
        em.persist(heladera2);

        em.persist(vianda3);


        em.persist(t1);

        em.persist(vianda2);
      em.persist(d1);

        em.persist(t2);
        em.persist(d2);

        em.persist(t3);
        em.persist(d3);

        em.persist(tarjeta4);
        em.persist(d4);
        em.persist(personaFisica4);
        em.persist(vianda4);
        //em.persist(v1);
        //em.persist(v2);
        //em.persist(v3);
        em.persist(viandaRecogida4);
        vulnerable4.retirarVianda(1, heladera2);

        //em.persist(vulnerable4);
        BDUtils.commit(em);

        usuarios();
    }

    public static void personas(String[] args) {
        EntityManager em = BDUtils.getEntityManager();
        BDUtils.comenzarTransaccion(em);



        EmailDir emailDirManuel = new EmailDir("manubocha@gmail.com");
        String direccionManuel = "Montes De Oca 2671";
        Documento documentoManuel = new Documento("DNI", "47112068");
        LocalDate fechaNacimientoManuel = LocalDate.of(2000, 10, 12);
        PersonaFisica manuelBochini = new PersonaFisica("Manuel", Arrays.asList(emailDirManuel),direccionManuel,documentoManuel, "Bochini","Hombre","Masculino", fechaNacimientoManuel);

        em.persist(emailDirManuel);
        em.persist(documentoManuel);
        em.persist(manuelBochini);
        //Persona fisica: ruffini

        EmailDir emailDirRuffini = new EmailDir("ruffus@gmail.com");
        String direccionRuffini = "Corrientes 2015";
        Documento documentoRuffini = new Documento("DNI", "25710420");
        LocalDate fechaNacimientoRuffini = LocalDate.of(2018, 12, 9);
        PersonaFisica ruffini = new PersonaFisica("ruffini", Arrays.asList(emailDirRuffini),direccionRuffini,documentoRuffini, "Perez","Hombre","Masculino", fechaNacimientoRuffini);

        em.persist(emailDirRuffini);
        em.persist(documentoRuffini);
        em.persist(ruffini);
        //Persona fisica: Emma

        EmailDir emailDirEmma = new EmailDir("Emma@gmail.com");
        String direccionEmma = "Rivadavia 5790";
        Documento documentoEmma = new Documento("DNI", "22190485");
        LocalDate fechaNacimientoEmma = LocalDate.of(2014, 9, 20);
        PersonaFisica emma = new PersonaFisica("Emma", Arrays.asList(emailDirEmma),direccionEmma,documentoEmma, "Gonzalez","Mujer","Femenino", fechaNacimientoEmma);

        em.persist(emailDirEmma);
        em.persist(documentoEmma);
        em.persist(emma);
        //Persona fisica: Adriana

        EmailDir emailDirAdriana = new EmailDir("adriana@hotmail.com");
        String direccionAdriana = "Marcelo T. De Alvear 1542";
        Documento documentoAdriana = new Documento("DNI", "37187059");
        LocalDate fechaNacimientoAdriana = LocalDate.of(1958, 7, 24);
        PersonaFisica adrianaCirulli = new PersonaFisica("Adriana", Arrays.asList(emailDirAdriana),direccionAdriana,documentoAdriana, "Cirulli","Mujer","Femenino", fechaNacimientoAdriana);

        em.persist(emailDirAdriana);
        em.persist(documentoAdriana);
        em.persist(adrianaCirulli);
        //Colaboradores:

        //Colaborador Manuel:

        Colaborador colaboradorManuel = new Colaborador();
        colaboradorManuel.setPersona(manuelBochini);

        //Colaborador Ruffini:

        Colaborador colaboradorRuffini = new Colaborador(ruffini, null,0f,null,null);

        //Colaborador Emma:

        Colaborador colaboradorEmma = new Colaborador(emma, null,0f,null,null);

        //Colaborador Manuel:

        Colaborador colaboradorAdriana = new Colaborador(adrianaCirulli, null,0f,null,null);


        //Persisto los colaboradores
        em.persist(colaboradorManuel);
        em.persist(colaboradorAdriana);
        //em.persist(colaboradorEmma);
        //em.persist(colaboradorRuffini);


        //PERSONAS JURIDICAS

        //Persona Juridica Kiosco
        EmailDir emailDirKiosco = new EmailDir("kiosco25hs@yahoo.com");
        String direccionKiosco = "Huego 349";
        Documento estatutoKiosco = new Documento("CUIT", "42823012");
        PersonaJuridica kiosco = new PersonaJuridica("Kiosco SRL", EnumTipoPersonaJuridica.EMPRESA,"Comercio", direccionKiosco, estatutoKiosco, Arrays.asList(emailDirKiosco));

        em.persist(emailDirKiosco);
        em.persist(estatutoKiosco);
        em.persist(kiosco);
        //Persona Juridica ONG
        EmailDir emailDirONG = new EmailDir("AyudasONG@yahoo.com");
        String direccionONG = "Gurruchaga 2345";
        Documento estatutoONG = new Documento("CUIT", "42823024");
        PersonaJuridica ong = new PersonaJuridica("ONG", EnumTipoPersonaJuridica.ONG,"Ayuda", direccionONG, estatutoONG, Arrays.asList(emailDirONG));

        em.persist(emailDirONG);
        em.persist(estatutoONG);
        em.persist(ong);


        //Persisto las personas juridicas
        em.persist(kiosco);
        em.persist(ong);

        //PERSONAS TECNICAS
        EmailDir emailDirTecnico1 = new EmailDir("reparaTodo@gmail.com");
        String direccionTecnico1 = "Serrano 741";
        Documento documentoTecnico1 = new Documento("DNI", "25814920");
        LocalDate fechaNacimientoTecnico1 = LocalDate.of(1987, 5, 8);
        PersonaFisica albertoTecnico = new PersonaFisica("Alberto", Arrays.asList(emailDirTecnico1),direccionTecnico1, documentoTecnico1, "Fernandez", "Hombre", "Masculino", fechaNacimientoTecnico1);


        em.persist(emailDirTecnico1);
        em.persist(documentoTecnico1);
        //em.persist(albertoTecnico);


        //PERSONAS TECNICAS
        EmailDir emailDirTecnico2 = new EmailDir("destapaTodo@gmail.com");
        String direccionTecnico2 = "Santa Fe 4405";
        Documento documentoTecnico2 = new Documento("DNI", "27123547");
        LocalDate fechaNacimientoTecnico2 = LocalDate.of(1989, 1, 28);
        PersonaFisica marioTecnico = new PersonaFisica("Mario", Arrays.asList(emailDirTecnico2),direccionTecnico2, documentoTecnico2, "Ruiz", "Hombre", "Masculino", fechaNacimientoTecnico2);

        em.persist(emailDirTecnico2);
        em.persist(documentoTecnico2);
        em.persist(marioTecnico);

        //Persisto los tecnicos
        em.persist(albertoTecnico);
        em.persist(marioTecnico);

        BDUtils.commit(em);

    }

    public static void colaboraciones(String[] args) {

        EntityManager em = BDUtils.getEntityManager();
        BDUtils.comenzarTransaccion(em);

        Tarjeta tarjeta1 = new Tarjeta("Tarjeta1");
        Tarjeta tarjeta2 = new Tarjeta("Tarjeta2");
        Tarjeta tarjeta3 = new Tarjeta("Tarjeta3");
        Tarjeta card4 = new Tarjeta("Card4");

        em.persist(tarjeta1);
        em.persist(tarjeta2);
        em.persist(tarjeta3);
        em.persist(card4);

        List<Tarjeta> tarjetasParaEntregarElsa = new ArrayList();
        tarjetasParaEntregarElsa.add(tarjeta1);
        tarjetasParaEntregarElsa.add(tarjeta2);
        tarjetasParaEntregarElsa.add(tarjeta3);
        tarjetasParaEntregarElsa.add(card4);

        EmailDir emailDirElsa = new EmailDir("elsa_pato@hotmail.com");
        String direccionElsa = "San Mateo 1200";
        Documento documentoElsa = new Documento("DNI", "14294741");
        LocalDate fechaNacimientoElsa = LocalDate.of(1960, 12, 24);
        PersonaFisica elsa = new PersonaFisica("Elsa", Arrays.asList(emailDirElsa),direccionElsa,documentoElsa, "Pato","Mujer","Femenino", fechaNacimientoElsa);
        Colaborador colaboradorElsa = new Colaborador(elsa, null,0f,tarjetasParaEntregarElsa,null);

        em.persist(emailDirElsa);
        em.persist(documentoElsa);
        em.persist(elsa);
        em.persist(colaboradorElsa);

        //Dar de alta una persona vulnerable
        EmailDir emailDirJuan = new EmailDir("JuanVulnerable@gmail.com");
        String direccionJuan = "Medrano 1480";
        Documento documentoJuan = new Documento("DNI", "30156290");
        LocalDate fechaNacimientoJuan = LocalDate.of(1990, 8, 30);
        PersonaFisica juan = new PersonaFisica("Juan", Arrays.asList(emailDirJuan),direccionJuan, documentoJuan, "Bores", "Hombre", "Masculino", fechaNacimientoJuan);
        Vulnerable vulnerableJuan = new Vulnerable(juan, LocalDate.now(), EnumSituacionCalle.POSEE_HOGAR, 0, null, null, null);

        em.persist(emailDirJuan);
        em.persist(documentoJuan);
        em.persist(juan);
        em.persist(vulnerableJuan);

        RegistroPersonaVulnerable registroJuan = new RegistroPersonaVulnerable(colaboradorElsa,LocalDate.now(),vulnerableJuan);
        registroJuan.ejecutar();

        em.persist(registroJuan);
        //Dar de alta segunda persona vulnerable

        EmailDir emailDirIñaki = new EmailDir("IñakiVulnerable@gmail.com");
        String direccionIñaki = "Federico Lacroze 3273";
        Documento documentoIñaki = new Documento("DNI", "42871305");
        LocalDate fechaNacimientoIñaki = LocalDate.of(2000, 10, 20);
        PersonaFisica iñaki = new PersonaFisica("Iñaki", Arrays.asList(emailDirIñaki),direccionIñaki, documentoIñaki, "Ansa", "Hombre", "Masculino", fechaNacimientoIñaki);
        Vulnerable vulnerableIñaki = new Vulnerable(iñaki, LocalDate.now(), EnumSituacionCalle.POSEE_HOGAR, 0, null, null, null);

        em.persist(emailDirIñaki);
        em.persist(documentoIñaki);
        em.persist(iñaki);
        em.persist(vulnerableIñaki);

        RegistroPersonaVulnerable registroIñaki = new RegistroPersonaVulnerable(colaboradorElsa,LocalDate.now(),vulnerableIñaki);
        registroIñaki.ejecutar();

        em.persist(registroIñaki);
        //Dar de alta tercera persona vulnerable

        EmailDir emailDirTomas = new EmailDir("tomas_martinez@gmail.com");
        String direccionTomas = "Azul 50";
        Documento documentoTomas = new Documento("DNI", "21420492");
        LocalDate fechaNacimientoTomas = LocalDate.of(1980, 11, 15);
        PersonaFisica tomas = new PersonaFisica("Tomas", Arrays.asList(emailDirTomas),direccionTomas, documentoTomas, "Martinez", "Hombre", "Masculino", fechaNacimientoTomas);
        Vulnerable vulnerableTomas = new Vulnerable(tomas, LocalDate.now(), EnumSituacionCalle.NO_POSEE_HOGAR, 0, null, null, null);

        em.persist(emailDirTomas);
        em.persist(documentoTomas);
        em.persist(tomas);
        em.persist(vulnerableTomas);

        RegistroPersonaVulnerable registroTomas = new RegistroPersonaVulnerable(colaboradorElsa,LocalDate.now(),vulnerableTomas);
        registroTomas.ejecutar();

        em.persist(registroTomas);
        //Dar de alta cuarta persona vulnerable

        EmailDir emailDirLuciana = new EmailDir("luciana_gonzales@gmail.com");
        String direccionLuciana = "Thames 2580";
        Documento documentoLuciana = new Documento("DNI", "42872158");
        LocalDate fechaNacimientoLuciana = LocalDate.of(2000, 11, 3);
        PersonaFisica luciana = new PersonaFisica("Lucio", Arrays.asList(emailDirLuciana),direccionLuciana, documentoLuciana, "Gonzalez", "Mujer", "Femenino", fechaNacimientoLuciana);
        Vulnerable vulnerableLuciana = new Vulnerable(luciana, LocalDate.now(), EnumSituacionCalle.POSEE_HOGAR, 0, null, null, null);

        em.persist(emailDirLuciana);
        em.persist(documentoLuciana);
        em.persist(luciana);
        em.persist(vulnerableLuciana);


        RegistroPersonaVulnerable registroLuciana = new RegistroPersonaVulnerable(colaboradorElsa,LocalDate.now(),vulnerableLuciana);
        registroLuciana.ejecutar();

        em.persist(registroLuciana);

        //COLABORACIONES ENCARGADO HELADERA
        //Genero las 4 heladeras
        Ubicacion direccionCabildo = new Ubicacion("BSAS","Congreso", "-34.608607", "-58.373399", "Bolivar" ,"1087");
        Heladera heladeraCabildo = new Heladera("heladera Cabildo",direccionCabildo, 20, LocalDate.now(), 5f, 10.0f, EnumEstadoHeladera.INACTIVA_POR_FALLA);


        em.persist(direccionCabildo);
        em.persist(heladeraCabildo);


        Ubicacion direccionPlazaItalia = new Ubicacion("BSAS","Palermo", "-34.581467 ", "-58.421326", "Avenida Santa Fe" ,"1425");
        Heladera heladeraPlazaItalia = new Heladera("heladera Plaza Italia",direccionPlazaItalia, 20, LocalDate.now(), 5f, 10.0f, EnumEstadoHeladera.DISPONIBLE);

        em.persist(direccionPlazaItalia);
        em.persist(heladeraPlazaItalia);

        Ubicacion direccionParqueCentenario = new Ubicacion("BSAS","Almagro", "-34.607189", "-58.432794", "Avenida Patricias Argentinas" ,"150");
        Heladera heladeraParqueCentenario = new Heladera("heladera Parque Centenario",direccionParqueCentenario, 150, LocalDate.now(), 5f, 10.0f, EnumEstadoHeladera.INACTIVA_POR_ALERTA);

        em.persist(direccionParqueCentenario);
        em.persist(heladeraParqueCentenario);

        Ubicacion direccionParqueRivadia = new Ubicacion("BSAS","Caballito", "-34.616963", "-58.433521", "Avenida Rivadavia" ,"4815");
        Heladera heladeraParqueRivadavia = new Heladera("heladera Parque Rivadavia",direccionParqueRivadia, 50, LocalDate.now(), 5f, 10.0f, EnumEstadoHeladera.DISPONIBLE);

        em.persist(direccionParqueRivadia);
        em.persist(heladeraParqueRivadavia);

        //Empiezo a asignar a Adriana como responsable de las 4
        ResponsableHeladera responsableUnaHeladeraAdriana = new ResponsableHeladera(colaboradorElsa,LocalDate.now(),heladeraCabildo);
        //responsableUnaHeladeraAdriana.ejecutar();

        em.persist(responsableUnaHeladeraAdriana);

        ResponsableHeladera responsableDosHeladerasAdriana = new ResponsableHeladera(colaboradorElsa,LocalDate.now(),heladeraPlazaItalia);
        //responsableDosHeladerasAdriana.ejecutar();

        em.persist(responsableDosHeladerasAdriana);

        ResponsableHeladera responsableTresHeladerasAdriana = new ResponsableHeladera(colaboradorElsa,LocalDate.now(),heladeraParqueCentenario);
        //responsableTresHeladerasAdriana.ejecutar();

        em.persist(responsableTresHeladerasAdriana);

        ResponsableHeladera responsableCuatroHeladerasAdriana = new ResponsableHeladera(colaboradorElsa,LocalDate.now(),heladeraParqueRivadavia);
        //responsableCuatroHeladerasAdriana.ejecutar();

        em.persist(responsableCuatroHeladerasAdriana);

        //DONACIONES DE VIANDA A CADA HELADERA


        Vianda primerVianda = new Vianda("carne", LocalDate.now(), LocalDate.now(),  "100", 300f, EnumEstadoVianda.ENTREGADO);
        Vianda segundaVianda = new Vianda("fideos", LocalDate.now(), LocalDate.now(),  "300", 400f, EnumEstadoVianda.ENTREGADO);
        Vianda tercerVianda = new Vianda("pizza", LocalDate.now(), LocalDate.now(),  "500", 250f, EnumEstadoVianda.ENTREGADO);
        Vianda cuartaVianda = new Vianda("carne", LocalDate.now(), LocalDate.now(),  "150", 250f, EnumEstadoVianda.ENTREGADO);
        Vianda quintaVianda = new Vianda("milanesa", LocalDate.now(), LocalDate.now(),  "320", 400f, EnumEstadoVianda.ENTREGADO);
        Vianda sextaVianda = new Vianda("tarta", LocalDate.now(), LocalDate.now(),  "470", 600f, EnumEstadoVianda.ENTREGADO);
        Vianda septimaVianda = new Vianda("pastel de papa", LocalDate.now(), LocalDate.now(),  "230", 420f, EnumEstadoVianda.ENTREGADO);
        Vianda octavaVianda = new Vianda("torta", LocalDate.now(), LocalDate.now(),  "500", 300f, EnumEstadoVianda.ENTREGADO);
        Vianda novenaVianda = new Vianda("manzana", LocalDate.now(), LocalDate.now(),  "100", 250f, EnumEstadoVianda.ENTREGADO);

        primerVianda.setHeladera(heladeraCabildo);
        segundaVianda.setHeladera(heladeraPlazaItalia);
        tercerVianda.setHeladera(heladeraParqueCentenario);
        cuartaVianda.setHeladera(heladeraParqueRivadavia);
        quintaVianda.setHeladera(heladeraCabildo);
        sextaVianda.setHeladera(heladeraPlazaItalia);
        septimaVianda.setHeladera(heladeraParqueCentenario);
        octavaVianda.setHeladera(heladeraParqueRivadavia);
        novenaVianda.setHeladera(heladeraCabildo);

        em.persist(primerVianda);
        em.persist(segundaVianda);
        em.persist(tercerVianda);
        em.persist(cuartaVianda);
        em.persist(quintaVianda);
        em.persist(sextaVianda);
        em.persist(septimaVianda);
        em.persist(octavaVianda);
        em.persist(novenaVianda);

        DonacionVianda primerDonacion = new DonacionVianda(colaboradorElsa,LocalDate.now(),primerVianda,heladeraCabildo);
        DonacionVianda segundaDonacion = new DonacionVianda(colaboradorElsa,LocalDate.now(),segundaVianda,heladeraCabildo);

        primerDonacion.ejecutar();
        segundaDonacion.ejecutar();

        DonacionVianda tercerDonacion = new DonacionVianda(colaboradorElsa,LocalDate.now(),tercerVianda,heladeraPlazaItalia);
        DonacionVianda cuartaDonacion = new DonacionVianda(colaboradorElsa,LocalDate.now(),cuartaVianda,heladeraPlazaItalia);

        tercerDonacion.ejecutar();
        cuartaDonacion.ejecutar();

        DonacionVianda quintaDonacion = new DonacionVianda(colaboradorElsa,LocalDate.now(),quintaVianda,heladeraParqueCentenario);
        DonacionVianda sextaDonacion = new DonacionVianda(colaboradorElsa,LocalDate.now(),sextaVianda,heladeraParqueCentenario);
        DonacionVianda septimaDonacion = new DonacionVianda(colaboradorElsa,LocalDate.now(),septimaVianda,heladeraParqueCentenario);

        quintaDonacion.ejecutar();
        sextaDonacion.ejecutar();
        septimaDonacion.ejecutar();

        DonacionVianda octavaDonacion = new DonacionVianda(colaboradorElsa,LocalDate.now(),octavaVianda,heladeraParqueRivadavia);
        DonacionVianda novenaDonacion = new DonacionVianda(colaboradorElsa,LocalDate.now(),novenaVianda,heladeraParqueRivadavia);

        octavaDonacion.ejecutar();
        novenaDonacion.ejecutar();

        //Persistimos las dinaciones de vianda
        em.persist(primerDonacion);
        em.persist(segundaDonacion);
        em.persist(tercerDonacion);
        em.persist(cuartaDonacion);
        em.persist(quintaDonacion);
        em.persist(sextaDonacion);
        em.persist(septimaDonacion);
        em.persist(octavaDonacion);
        em.persist(novenaDonacion);

        //TRASLADOS DE VIANDA

        DistribucionVianda primeraDistribucion = new DistribucionVianda(colaboradorElsa, LocalDate.now(),heladeraPlazaItalia,heladeraParqueCentenario,2, EnumMotivosMovimientoVianda.FALTA_DE_VIANDAS);
        primeraDistribucion.ejecutar();

        DistribucionVianda segundaDistribucion = new DistribucionVianda(colaboradorElsa, LocalDate.now(),heladeraParqueRivadavia,heladeraCabildo,1, EnumMotivosMovimientoVianda.FALTA_DE_VIANDAS);
        segundaDistribucion.ejecutar();

        //DistribucionVianda terceraDistribucion = new DistribucionVianda(colaboradorElsa, LocalDate.now(),heladeraParqueCentenario,heladeraPlazaItalia,1, EnumMotivosMovimientoVianda.FALTA_DE_VIANDAS);
        //terceraDistribucion.ejecutar();

        //Persistimos las distribuciones
        em.persist(primeraDistribucion);
        em.persist(segundaDistribucion);
        //em.persist(terceraDistribucion);

        //DONACION DE DINERO

        Colaboracion donacionDinero = new DonacionDinero(colaboradorElsa,fechaNacimientoElsa ,3500F, "1 vez por semana", null);
        donacionDinero.ejecutar(); //el ejecutar no hace nada pero bueno

        //Persistimos donaciones de dinero
        em.persist(donacionDinero);

        // PRESENTACION OFERTAS
        Catalogo catalogo = Catalogo.getInstance();

        Colaboracion presentarOfertaMartillo = new PresentacionOferta(colaboradorElsa,LocalDate.now(), "Metalurgica","Martillo","una oferta",200F,"imagenMartillo");
        presentarOfertaMartillo.ejecutar();

        Colaboracion presentarOfertaPelota = new PresentacionOferta(colaboradorElsa,LocalDate.now(), "Deporte","Pelota Jabulani","una oferta",250F,"imagenJabulani");
        presentarOfertaPelota.ejecutar();

        // Persistimos las ofertas
        em.persist(presentarOfertaMartillo);
        em.persist(presentarOfertaPelota);


        BDUtils.commit(em);
    }

    public static void servicios(String[] args){
        EntityManager em = BDUtils.getEm();
        BDUtils.comenzarTransaccion(em);

        Tarjeta tarjeta1 = new Tarjeta("Tarjeta1");
        Tarjeta tarjeta2 = new Tarjeta("Tarjeta2");
        Tarjeta tarjeta3 = new Tarjeta("Tarjeta3");
        Tarjeta tarjeta4 = new Tarjeta("Tarjeta4");

        em.persist(tarjeta1);
        em.persist(tarjeta2);
        em.persist(tarjeta3);
        em.persist(tarjeta4);


        List<Tarjeta> tarjetasParaEntregarEdgar = new ArrayList();
        tarjetasParaEntregarEdgar.add(tarjeta1);
        tarjetasParaEntregarEdgar.add(tarjeta2);
        tarjetasParaEntregarEdgar.add(tarjeta3);
        tarjetasParaEntregarEdgar.add(tarjeta4);

        EmailDir emailDirEdgar = new EmailDir("edgar_pato@hotmail.com");
        String direccionEdgar= "San Mateo 1220";
        Documento documentoEdgar = new Documento("DNI", "143948541");
        LocalDate fechaNacimientoEdgar = LocalDate.of(1962, 12, 24);
        PersonaFisica edgar = new PersonaFisica("Edgar", Arrays.asList(emailDirEdgar),direccionEdgar,documentoEdgar, "Pato","Masculino","Masculino", fechaNacimientoEdgar);
        Colaborador colaboradorEdgar = new Colaborador(edgar, null,250000f,tarjetasParaEntregarEdgar,null);

        em.persist(emailDirEdgar);
        em.persist(documentoEdgar);
        em.persist(edgar);
        //persisto el colaborador
        em.persist(colaboradorEdgar);

        Catalogo catalogo = Catalogo.getInstance();

        //persisto el catalogo
        //em.persist(catalogo);

        Colaboracion presentarOfertaDestornillador = new PresentacionOferta(colaboradorEdgar,LocalDate.now(), "Metalurgica","Martillo","una oferta",200F,"imagenMartillo");
        presentarOfertaDestornillador.ejecutar();

        Colaboracion presentarOfertaBotines = new PresentacionOferta(colaboradorEdgar,LocalDate.now(), "Deporte","Pelota Jabulani","una oferta",250F,"imagenJabulani");
        presentarOfertaBotines.ejecutar();

        //catalogo.otorgar(0,colaboradorEdgar);

        //catalogo.otorgar(1,colaboradorEdgar);

        //persisto las ofertas
        em.persist(presentarOfertaDestornillador);
        em.persist(presentarOfertaBotines);
        //persisto los canjes de puntos?? como??

        BDUtils.commit(em);
    }

    public static void incidentes(String[] args) {

        EntityManager em = BDUtils.getEntityManager();
        BDUtils.comenzarTransaccion(em);

        //Primero creo Colaborador
        EmailDir emailDirEzequiel = new EmailDir("ezequiel_colapinto@gmail.com");
        String direccionEzequiel = "Laprida 2534";
        Documento documentoEzequiel = new Documento("DNI", "41376905");
        LocalDate fechaNacimientoEzequiel = LocalDate.of(2000, 10, 12);

        em.persist(documentoEzequiel);
        em.persist(emailDirEzequiel);

        //Lo hago colaborador
        PersonaFisica ezequiel_colapinto = new PersonaFisica("Ezequiel", Arrays.asList(emailDirEzequiel),direccionEzequiel,documentoEzequiel, "Colapinto","Hombre","Masculino", fechaNacimientoEzequiel);
        Colaborador colaboradorEzequiel = new Colaborador(ezequiel_colapinto, null,0f,null,null);

        em.persist(ezequiel_colapinto);
        //Persisto al colaborador
        em.persist(colaboradorEzequiel);

        //Creamos las heladeras
        Ubicacion direccionBanfield = new Ubicacion("BSAS","Banfield", "-34.743360", "-58.396135", "Alem" ,"1460");
        Heladera heladeraBanfield = new Heladera("heladera Estacion de Banfield",direccionBanfield, 20, LocalDate.now(), 5f, 10.0f, EnumEstadoHeladera.INACTIVA_POR_FALLA);

        Ubicacion direccionLaBoca = new Ubicacion("BSAS","La Boca", "-34.636439", "-58.364152", "Brandsen" ,"805");
        Heladera heladeraBoca = new Heladera("heladera Estadio Bombonera",direccionLaBoca, 200, LocalDate.now(), 5f, 10.0f, EnumEstadoHeladera.INACTIVA_POR_FALLA);

        em.persist(direccionBanfield);
        em.persist(direccionLaBoca);

        //Persisto la heladera
        em.persist(heladeraBanfield);
        em.persist(heladeraBoca);

        //Creamos los incidentes
        IncidenteFallaTecnica incidenteReportado = new IncidenteFallaTecnica(heladeraBoca, LocalDate.now(),colaboradorEzequiel,"Se rompio un caño", "imagen");

        IncidenteAlarma incidentePorAlarma = new IncidenteAlarma(heladeraBoca, LocalDate.now(), EnumTipoDeFalla.TEMPERATURA);

        //Persisto las fallas
        em.persist(incidenteReportado);
        em.persist(incidentePorAlarma);


        BDUtils.commit(em);

    }

    private static void crearAdmin() {
        EntityManager em = BDUtils.getEntityManager();
        BDUtils.comenzarTransaccion(em);

        Documento dni1 = new Documento("dni","43403588");
        PersonaFisica tomas = new PersonaFisica("tomas", null, "nogoya 6367", dni1, "cerezo", "m", "m", LocalDate.of(2001, 6, 28));
        Administrador a = new Administrador(tomas);
        Usuario admin = new Usuario("admin", "fc43", a);

        em.persist(dni1);
        em.persist(tomas);
        em.persist(a);
        em.persist(admin);

        BDUtils.commit(em);
    }

    private static void usuarios(){
        crearAdmin();

        EntityManager em = BDUtils.getEntityManager();
        BDUtils.comenzarTransaccion(em);

        //creo tarjetas para sergio
        //Tarjeta tarjetaParaVulnerable = new Tarjeta("0001");
        //Tarjeta tarjetaParaVulnerable2 = new Tarjeta("0002");

        //List<Tarjeta> tarjetasSergio= new ArrayList<>();
        //tarjetasSergio.add(tarjetaParaVulnerable);
        //tarjetasSergio.add(tarjetaParaVulnerable2);


        Documento dni2 = new Documento("dni","21328838");
        EmailDir emailDirSergio = new EmailDir("juanpolito02@gmail.com");

        List<MedioDeContacto> medioDeContactosSergio =new ArrayList<MedioDeContacto>();
        medioDeContactosSergio.add(emailDirSergio);

        PersonaFisica sergio = new PersonaFisica("sergio", medioDeContactosSergio, "nogoya 6367", dni2, "cerezo", "m", "m", LocalDate.of(1970, 5, 28));
        //Colaborador s = new Colaborador(sergio,tarjetasSergio);
        Colaborador s = new Colaborador(sergio);
        //s.setTarjetasParaEntregar(tarjetasSergio);

        // le pongo tarjetas a sergio
        //Tarjeta tarjetaParaVulnerable = new Tarjeta("0001");
        //Tarjeta tarjetaParaVulnerable2 = new Tarjeta("0002");

        //List<Tarjeta> tarjetasSergio= new ArrayList<>();
        //tarjetasSergio.add(tarjetaParaVulnerable);
        //tarjetasSergio.add(tarjetaParaVulnerable2);

        //s.recibirTarjetas(tarjetasSergio);

        Usuario ser = new Usuario("sergio", "fc43", s);

        Documento cuit1 = new Documento("cuit","67");
        PersonaJuridica utn = new PersonaJuridica("utn", EnumTipoPersonaJuridica.ONG, "uni", "mozart 2300", cuit1, null);
        Colaborador u = new Colaborador(utn);
        Usuario utnfrba = new Usuario("utn", "fc43", u);

        Documento cuit2 = new Documento("cuit","69");
        PersonaFisica hei = new PersonaFisica("heimer", null, "nogoya 6367", cuit2, "dinger", "m", "m", LocalDate.of(1000, 5, 28));
        Tecnico t = new Tecnico(hei);
        Usuario tec = new Usuario("heimer", "fc43", t);

        //em.persist(tarjetaParaVulnerable);
        //em.persist(tarjetaParaVulnerable2);

        em.persist(dni2);
        em.persist(sergio);
        em.persist(s);
        em.persist(ser);

        em.persist(cuit1);
        em.persist(utn);
        em.persist(u);
        em.persist(utnfrba);

        em.persist(cuit2);
        em.persist(hei);
        em.persist(t);
        em.persist(tec);

        BDUtils.commit(em);
    }
}