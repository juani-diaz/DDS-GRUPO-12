package domain.servicios;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import domain.heladera.EnumMotivoApertura;
import domain.rol.Tarjeta;
import okhttp3.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.awt.*;
import java.io.IOException;
import java.util.Optional;

public class Broker {
    private final String serverUrl;
    private final OkHttpClient httpClient;
    private final Gson gson;

    public Broker(String serverUrl) {
        this.serverUrl = serverUrl;
        this.httpClient = new OkHttpClient();
        this.gson = new Gson();
    }

    public void setFallaTemperatura(Integer heladeraId, Float temperatura) {

        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("heladeraId", heladeraId);
        requestBody.addProperty("tipoFalla", "TEMPERATURA");
        requestBody.addProperty("temperaturaFalla", temperatura);

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(serverUrl + "/api/temperatura")
                .post(RequestBody.create(requestBody.toString(), MediaType.parse("application/json")))
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Error al registrar la temperatura: " + response.body().string());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al conectar con el servidor", e);
        }
    }

    public void abrirHeladera(Integer heladeraId, Integer motivo, Integer colaboradoId) {

        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("heladeraId", heladeraId);
        requestBody.addProperty("colaboradorId", colaboradoId);
        requestBody.addProperty("motivoApertura", motivo);

        Request request = new Request.Builder()
                .url(serverUrl + "/api/solicitarApertura")
                .post(RequestBody.create(requestBody.toString(), MediaType.parse("application/json")))
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Error al solicitar apertura: " + response.body().string());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al conectar con el servidor", e);
        }
    }


    public void sacarVianda(Integer viandaId) {

        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("viandaId", viandaId);

        Request request = new Request.Builder()
                .url(serverUrl + "/api/sacarVianda")
                .post(RequestBody.create(requestBody.toString(), MediaType.parse("application/json")))
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Error al solicitar apertura: " + response.body().string());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al conectar con el servidor", e);
        }
    }


}
