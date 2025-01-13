package controllers;

import domain.heladera.AdministradorSolicitudes;
import domain.heladera.EnumMotivoApertura;
import domain.heladera.Heladera;
import domain.heladera.PedidoApertura;
import domain.incidente.EnumTipoDeFalla;
import domain.incidente.IncidenteAlarma;
import domain.rol.Colaborador;
import domain.rol.Tarjeta;
import io.javalin.http.Context;
import persistence.Repos.RepoColaborador;
import persistence.Repos.RepoHeladera;

import java.util.Date;
import java.util.Map;

public class APIControllers {

 public void crearIncidenteTemperatura(Context ctx){
     RepoHeladera repoHeladera = RepoHeladera.getInstance();
     Map<String, Object> body = ctx.bodyAsClass(Map.class);

    int heladeraId = (int) body.getOrDefault("heladeraId", -1);
    Integer tipoFalla = (Integer) body.getOrDefault("tipoFalla", "");

    Heladera heladera = repoHeladera.getHeladeras().get(heladeraId);
            if (heladera == null) {
        ctx.status(404).result("Heladera no encontrada");
        return;
    }

    EnumTipoDeFalla enumTipoDeFalla;
            try {
        enumTipoDeFalla = EnumTipoDeFalla.values()[tipoFalla];
    } catch (IllegalArgumentException e) {
        ctx.status(400).result("Tipo de falla inválido");
        return;
    }

    IncidenteAlarma incidente = new IncidenteAlarma(heladera, new Date(), enumTipoDeFalla);
            ctx.status(201).json(incidente);
}

 public void solicitarAperturaHeladera(Context ctx){
     RepoHeladera repoHeladera = RepoHeladera.getInstance();
     RepoColaborador repoColaborador=RepoColaborador.getInstance();
     AdministradorSolicitudes administradorSolicitudes = new AdministradorSolicitudes();

     Map<String, Object> body = ctx.bodyAsClass(Map.class);

     int heladeraId = (int) body.getOrDefault("heladeraId", -1);
     Integer colaboradorId = (Integer) body.getOrDefault("colaboradorId", "");
     Integer motivoApertura = (Integer) body.getOrDefault("motivoApertura", "");

     Heladera heladera = repoHeladera.getHeladeras().get(heladeraId);
     Tarjeta tarjeta = ((Colaborador) repoColaborador.getColaboradores().stream().filter(colaborador -> colaborador.getTarjetaColaborador().getId()==colaboradorId).toArray()[0]).getTarjetaColaborador();

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
}
