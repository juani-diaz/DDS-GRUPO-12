package controllers;

import domain.heladera.AdministradorSolicitudes;
import domain.heladera.EnumMotivoApertura;
import domain.heladera.Heladera;
import domain.heladera.PedidoApertura;
import domain.incidente.EnumTipoDeFalla;
import domain.incidente.IncidenteAlarma;
import domain.rol.Colaborador;
import domain.rol.Tarjeta;
import domain.servicios.Broker;
import io.javalin.http.Context;
import persistence.Repos.RepoColaborador;
import persistence.Repos.RepoHeladera;

import javax.persistence.criteria.CriteriaBuilder;
import java.lang.invoke.StringConcatFactory;
import java.util.Date;
import java.util.Map;

public class BrokerControllers {
    public void incidenteTemperatura(Context ctx){
        Map<String, Object> body = ctx.bodyAsClass(Map.class);

        Integer heladeraId = (Integer) body.getOrDefault("heladeraId", -1);
        Float temperatura = (Float) body.getOrDefault("temperatura", "");


        Broker broker=new Broker("http://localhost:8001/");

         broker.setFallaTemperatura(heladeraId,temperatura);
                 ctx.status(200);
    }

    public void solicitarAperturaHeladera(Context ctx){

        Map<String, Object> body = ctx.bodyAsClass(Map.class);

        Integer heladeraId = (Integer) body.getOrDefault("heladeraId", -1);
        Integer colaboradorId = (Integer) body.getOrDefault("colaboradorId", "");
        Integer motivoApertura = (Integer) body.getOrDefault("motivoApertura", "");

        Broker broker=new Broker("http://localhost:8001/");

        broker.abrirHeladera(heladeraId,motivoApertura,colaboradorId);
        ctx.status(200);
    }
}
