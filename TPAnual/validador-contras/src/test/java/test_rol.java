import domain.colaboraciones.Colaboracion;
import domain.colaboraciones.DonacionDinero;
import domain.heladera.Heladera;
import domain.incidente.EnumTipoDeFalla;
import domain.incidente.Incidente;
import domain.incidente.IncidenteAlarma;
import domain.persona.*;
import domain.rol.Colaborador;
import domain.rol.Tarjeta;
import domain.rol.Tecnico;
import domain.rol.UsoDeTarjeta;
import domain.vianda.ViandaRecogida;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class test_rol {
    MedioDeContacto medioDeContacto = new EmailDir();

    PersonaFisica personaFisica1 = new PersonaFisica();
    PersonaJuridica personaJuridica1 = new PersonaJuridica("Pepsi",EnumTipoPersonaJuridica.EMPRESA,"gaseosa");
    PersonaJuridica personaJuridica2 = new PersonaJuridica("Unicef",EnumTipoPersonaJuridica.ONG,"ayudar ninos");
    Heladera heladera1 = new Heladera();
    Heladera heladera2 = new Heladera();
    Colaborador colaboradorDistribuidor= new Colaborador();
    //Colaboracion colab1 = new DistribucionVianda(colaboradorDistribuidor,LocalDate.now(),heladera1,heladera2, Arrays.asList(2,3,4), EnumMotivosMovimientoVianda.FALTA_DE_VIANDAS);
    //PersonaJuridica personaJuridica1 = new PersonaJuridica();
    //Colaboracion colab1 = new DistribucionVianda();
    Colaboracion colab2 = new DonacionDinero();
    Tarjeta tar1 = new Tarjeta();
    UsoDeTarjeta usos1= new UsoDeTarjeta();
    ViandaRecogida vianda1 = new ViandaRecogida();
    //Colaborador colaborador1 = new Colaborador(personaFisica1, Arrays.asList(colab1,colab2), 0f,Arrays.asList(tar1),tar1);
    //Colaborador colaborador2 = new Colaborador(personaJuridica1, Arrays.asList(colab1,colab2),0f,Arrays.asList(tar1),tar1);
    //Vulnerable vulnerable1 = new Vulnerable(personaFisica1,LocalDate.now(), EnumSituacionCalle.NO_POSEE_HOGAR,2, Arrays.asList(vianda1),tar1,Arrays.asList(usos1),4);
    //Colaborador colaborador1 = new Colaborador(personaFisica1, Arrays.asList(colab1,colab2), 0f, Arrays.asList(tar1));
    //Colaborador colaborador2 = new Colaborador(personaJuridica1, Arrays.asList(colab1,colab2), 0f, Arrays.asList(tar1));
    //Vulnerable vulnerable1 = new Vulnerable(LocalDate.now(), EnumSituacionCalle.NO_POSEE_HOGAR,2, Arrays.asList(vianda1),tar1,Arrays.asList(usos1),4);
    List<String> localidades = Arrays.asList("LomasDeZamora","Palermo","Chacarita","Quilmes");
    //Incidente incidente1 = new IncidenteAlarma(heladera1, LocalDate.now(), EnumTipoDeFalla.FRAUDE);
    //Tecnico tecnico1 = new Tecnico(personaFisica1,localidades,Arrays.asList(incidente1), Arrays.asList());
    @Test
    public void crearColaboradorFisico() { // Se crea colaborador con persona fisica
        //Assertions.assertSame(personaFisica1.getClass(), colaborador1.getPersona().getClass());
        //Assertions.assertSame(personaFisica1.getClass(), colaborador1.getPersona().getClass());
    }

    @Test
    public void crearColaboradorJuridico() { // Se crea colaborador con persona juridica
        //Assertions.assertSame(personaJuridica1.getClass(), colaborador2.getPersona().getClass());
        Assertions.assertSame(personaJuridica1.getTipo(),EnumTipoPersonaJuridica.EMPRESA);
        Assertions.assertSame(personaJuridica2.getTipo(),EnumTipoPersonaJuridica.ONG);
        //Assertions.assertSame(personaJuridica1.getClass(), colaborador2.getPersona().getClass());
    }
    @Test
    public void crearVulnerable(){ //Se crea una persona fisica vulnerable
        //Assertions.assertSame(personaFisica1.getClass(),vulnerable1.getPersona().getClass());

    }
    @Test
    public void crearTecnico(){ //Se crea un técnico
        //Assertions.assertSame(personaFisica1.getClass(),tecnico1.getPersona().getClass());

    }
}
