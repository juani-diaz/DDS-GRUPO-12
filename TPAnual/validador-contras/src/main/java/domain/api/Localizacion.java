package domain.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import domain.registro.SingletonSeguidorEstadistica;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import io.javalin.http.Context;

public class Localizacion {
    public static String localizar(Context ctx){
        String desdeQ = ctx.queryParam("desde");
        String hastaQ = ctx.queryParam("hasta");
        String soloQ = ctx.queryParam("soloSinHogar");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate desde;
        try {
            desde = LocalDate.parse(desdeQ, formatter);
        } catch (Exception e) {
            desde = LocalDate.now().minusDays(1);
        }

        LocalDate hasta;
        try {
            hasta = LocalDate.parse(hastaQ, formatter);
        } catch (Exception e) {
            hasta = LocalDate.now();
        }

        boolean soloSinHogar;
        if(soloQ != null){
            soloSinHogar = Boolean.parseBoolean(soloQ);
        } else {
            soloSinHogar = true;
        }

        String json = null;
        try {
            ObjectWriter ow = new ObjectMapper().writer();
            SingletonSeguidorEstadistica se = SingletonSeguidorEstadistica.getInstance();
            ListadoLocalidades l = se.encontrarLocalidades(soloSinHogar, desde, hasta);
            json = ow.writeValueAsString(l);
        } catch (JsonProcessingException e) {
            json = "Error, algo salio mal";
        }
        return json;
    }
}
