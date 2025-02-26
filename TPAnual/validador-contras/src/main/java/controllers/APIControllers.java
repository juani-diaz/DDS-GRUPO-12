package controllers;

import domain.heladera.AdministradorSolicitudes;
import domain.heladera.EnumMotivoApertura;
import domain.heladera.Heladera;
import domain.heladera.PedidoApertura;
import domain.incidente.EnumTipoDeFalla;
import domain.incidente.IncidenteAlarma;
import domain.rol.Colaborador;
import domain.rol.Tarjeta;
import domain.rol.Vulnerable;
import domain.suscripcion.Publicador;
import domain.vianda.EnumEstadoVianda;
import domain.vianda.Vianda;
import domain.vianda.ViandaRecogida;
import io.javalin.http.Context;
import persistence.Repos.RepoColaborador;
import persistence.Repos.RepoHeladera;
import persistence.Repos.RepoVianda;
import persistence.Repos.RepoVulnerable;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

public class APIControllers {

 public void crearIncidenteTemperatura(Context ctx) throws IOException {
     RepoHeladera repoHeladera = RepoHeladera.getInstance();
     Map<String, Object> body = ctx.bodyAsClass(Map.class);

    int heladeraId = (int) body.getOrDefault("heladeraId", -1);

     if (heladeraId == -1) {
         ctx.status(404).result("heladeraId no se encuentra en el body");
         return;
     }
    Heladera heladera = repoHeladera.getHeladeras().get(heladeraId);
            if (heladera == null) {
        ctx.status(404).result("Heladera no encontrada");
        return;
    }


    IncidenteAlarma incidente = new IncidenteAlarma(heladera, LocalDate.now(), EnumTipoDeFalla.TEMPERATURA);
     Publicador publicador=new Publicador();
     publicador.notifyObservers();
     ctx.status(201).json(incidente);

}

 public void solicitarAperturaHeladera(Context ctx){
     RepoHeladera repoHeladera = RepoHeladera.getInstance();
     RepoColaborador repoColaborador=RepoColaborador.getInstance();
     AdministradorSolicitudes administradorSolicitudes = new AdministradorSolicitudes();

     Map<String, Object> body = ctx.bodyAsClass(Map.class);

     Integer heladeraId = (Integer) body.getOrDefault("heladeraId", -1);
     Integer colaboradorId = (Integer) body.getOrDefault("colaboradorId", "");
     Integer motivoApertura = (Integer) body.getOrDefault("motivoApertura", "");

     Heladera heladera = repoHeladera.getHeladeras().get(heladeraId);
     Tarjeta tarjeta = ((Colaborador) repoColaborador.getColaboradores().stream().filter(colaborador -> {
         if (colaborador.getTarjetaColaborador() != null) {
             return colaborador.getTarjetaColaborador().getId() == colaboradorId;
         }else {return false;}
     }).toArray()[0]).getTarjetaColaborador();

     if (heladera == null || tarjeta == null) {
         ctx.status(404).result("Heladera o tarjeta no encontrada");
         return;
     }

     EnumMotivoApertura enumMotivoApertura;
     try {
         enumMotivoApertura = EnumMotivoApertura.values()[motivoApertura];
     } catch (IllegalArgumentException e) {
         ctx.status(400).result("Motivo de apertura inválido");
         return;
     }

     PedidoApertura apertura = administradorSolicitudes.solicitarApertura(tarjeta, heladeraId, enumMotivoApertura);
     ctx.status(201).json(apertura);
 }
    public void sacarViandas(Context ctx){
        Map<String, Object> body = ctx.bodyAsClass(Map.class);
        int viandaId = (int) body.getOrDefault("viandaId", -1);
        int vulnerableId = (int) body.getOrDefault("vulnerableId", -1);
        RepoVulnerable repoVulnerable= RepoVulnerable.getInstance();
        Vulnerable vulnerable=repoVulnerable.findById_Vulnerable(vulnerableId);
        RepoVianda repoVianda = RepoVianda.getInstance();
        Vianda vianda=repoVianda.findById_Vianda(viandaId);
        Heladera heladera=vianda.getHeladera();
        vulnerable.retirarVianda(viandaId,heladera);
        vianda.setHeladera(null);
        vianda.setEstado(EnumEstadoVianda.ENTREGADO);
        repoVianda.updateVianda(vianda);
    }
}
