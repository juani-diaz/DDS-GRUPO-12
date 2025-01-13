package domain.servicios;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import domain.heladera.EnumMotivoApertura;
import domain.rol.Tarjeta;
import okhttp3.*;

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

    public void setTemperatura(String heladeraId, Float temperatura) {

        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("heladeraId", Integer.parseInt(heladeraId));
        requestBody.addProperty("tipoFalla", "TEMPERATURA");

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

    public void abrirHeladera(String heladeraId, EnumMotivoApertura motivo, Tarjeta tarjeta) {

        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("heladeraId", Integer.parseInt(heladeraId));
        requestBody.addProperty("colaboradorId", tarjeta.getId());
        requestBody.addProperty("motivoApertura", motivo.name());

        Request request = new Request.Builder()
                .url(serverUrl + "/api/abrirHeladera")
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
