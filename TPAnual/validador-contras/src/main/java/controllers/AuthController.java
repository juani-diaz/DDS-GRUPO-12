package controllers;
import io.javalin.http.Context;

import java.io.Console;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class AuthController {
    private static final String CLIENT_ID = "1sv8eP0H0PEH7BVbhHR9LHqBoDKtsarH";
    private static final String CLIENT_SECRET = "UPpsCPKr86OpaoaD81bJY8PIh39p73TArtukkBkqn8cgBJXbM9-hkSR5S6uaTGbq";
    private static final String DOMAIN = "dev-op3wuvbve1mpduir.us.auth0.com";  // Ej: tu-empresa.auth0.com
    private static final String REDIRECT_URI = "http://localhost:8001/index";

    private static final String AUTH_URL = "https://" + DOMAIN + "/authorize";
    private static final String TOKEN_URL = "https://" + DOMAIN + "/oauth/token";

    public static void loginWithGoogle(Context ctx) {
        System.out.println("Estoy adentro de log in with google");
        String authUrl = AUTH_URL + "?response_type=code" +
                "&client_id=" + CLIENT_ID +
                "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI, StandardCharsets.UTF_8) +
                "&scope=openid profile email";
        ctx.redirect(authUrl);
    }

    public static void handleCallback(Context ctx) {
        System.out.println("Estoy adentro de callback");
        String code = ctx.queryParam("code");
        System.out.println("code: " +  code);
        if (code == null) {
            ctx.result("Error: No authorization code received.");
            return;
        }

        // Intercambiar código por token de acceso
        Map<String, String> tokenResponse = exchangeCodeForToken(code);
        if (tokenResponse.containsKey("access_token")) {
            String accessToken = tokenResponse.get("access_token");
            Map<String, String> userInfo = getUserInfo(accessToken);
            ctx.sessionAttribute("user", userInfo);
            ctx.redirect("/index");
        } else {
            ctx.result("Error al obtener el token de acceso.");
        }
    }

    public static void logout(Context ctx) {
        ctx.sessionAttribute("user", null);
        ctx.redirect("https://" + DOMAIN + "/v2/logout?returnTo=http://localhost:8001/landing");
    }

    // Intercambiar código por un token de acceso
    private static Map<String, String> exchangeCodeForToken(String code) {
        String body = "grant_type=authorization_code" +
                "&client_id=" + CLIENT_ID +
                "&client_secret=" + CLIENT_SECRET +
                "&code=" + code +
                "&redirect_uri=" + REDIRECT_URI;

        return sendRequest(TOKEN_URL, body);
    }

    // Obtener los datos del usuario usando el token de acceso
    private static Map<String, String> getUserInfo(String accessToken) {
        String authorizationHeader = "Bearer " + accessToken;
        return sendRequest("https://" + DOMAIN + "/userinfo", authorizationHeader);
    }

    // Realizar la solicitud HTTP con un header de autorización
    private static Map<String, String> sendRequest(String url, String authorizationHeader) {
        // Implementa esta parte como en el código anterior.
        Map<String, String> unMapping = new HashMap<>();
        return unMapping;
    }
}
