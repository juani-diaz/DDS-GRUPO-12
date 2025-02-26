package controllers;

import com.ctc.wstx.shaded.msv.org_jp_gr_xml.dom.UDOM;
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
    String domain=System.getenv("DOMAIN");
    public void incidenteTemperatura(Context ctx){
        Map<String, Object> body = ctx.bodyAsClass(Map.class);

        Integer heladeraId = (Integer) body.getOrDefault("heladeraId", -1);
        Double temperatura = (Double) body.getOrDefault("temperatura", "");


        Broker broker=new Broker(domain);

         broker.setFallaTemperatura(heladeraId,temperatura.floatValue());
                 ctx.status(200);
    }

    public void solicitarAperturaHeladera(Context ctx){

        Map<String, Object> body = ctx.bodyAsClass(Map.class);

        Integer heladeraId = (Integer) body.getOrDefault("heladeraId", -1);
        Integer colaboradorId = (Integer) body.getOrDefault("colaboradorId", "");
        Integer motivoApertura = (Integer) body.getOrDefault("motivoApertura", "");

        Broker broker=new Broker(domain);

        broker.abrirHeladera(heladeraId,motivoApertura,colaboradorId);
        ctx.status(200);
    }

    public void sacarVianda(Context ctx){

        Map<String, Object> body = ctx.bodyAsClass(Map.class);

        Integer viandaId = (Integer) body.getOrDefault("viandaId", -1);

        Broker broker=new Broker(domain);

        broker.sacarVianda(viandaId);
        ctx.status(200);
    }
}
