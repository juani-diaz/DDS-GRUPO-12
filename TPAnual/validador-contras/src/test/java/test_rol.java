import domain.colaboraciones.Colaboracion;
import domain.colaboraciones.DistribucionVianda;
import domain.colaboraciones.DonacionDinero;
import domain.persona.Persona;
import domain.persona.PersonaFisica;
import domain.persona.PersonaJuridica;
import domain.rol.*;
import domain.vianda.Vianda;
import domain.vianda.ViandaRecogida;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class test_rol {

    PersonaFisica personaFisica1 = new PersonaFisica();
    PersonaJuridica personaJuridica1 = new PersonaJuridica();
    Colaboracion colab1 = new DistribucionVianda();
    Colaboracion colab2 = new DonacionDinero();
    Tarjeta tar1 = new Tarjeta();
    UsoDeTarjeta usos1= new UsoDeTarjeta();
    ViandaRecogida vianda1 = new ViandaRecogida();
    Colaborador colaborador1 = new Colaborador(personaFisica1, Arrays.asList(colab1,colab2), 0f, Arrays.asList(tar1));
    Colaborador colaborador2 = new Colaborador(personaJuridica1, Arrays.asList(colab1,colab2), 0f, Arrays.asList(tar1));
    Vulnerable vulnerable1 = new Vulnerable(LocalDate.now(), EnumSituacionCalle.NO_POSEE_HOGAR,2, Arrays.asList(vianda1),tar1,Arrays.asList(usos1),4);
    List<String> localidades = Arrays.asList("LomasDeZamora","Palermo","Chacarita","Quilmes");
    Tecnico tecnico1= new Tecnico(localidades);
    @Test
    public void crearColaboradorFisico() { // Se crea colaborador con persona fisica
        Assertions.assertSame(personaFisica1.getClass(), colaborador1.getPersona().getClass());
    }

    @Test
    public void crearColaboradorJuridico() { // Se crea colaborador con persona juridica
        Assertions.assertSame(personaJuridica1.getClass(), colaborador2.getPersona().getClass());
    }
    @Test
    public void crearVulnerable(){ //Se crea una persona fisica vulnerable

    }
    @Test
    public void crearTecnico(){ //Se crea un técnico

    }
}
