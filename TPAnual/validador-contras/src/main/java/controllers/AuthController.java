package controllers;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.auth.JwtUtil;
import domain.auth.Usuario;
import domain.persona.PersonaFisica;
import domain.rol.Colaborador;
import domain.rol.Rol;
import io.javalin.http.Context;
import persistence.Repos.RepoUsuarios;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class AuthController {
    private static final String CLIENT_ID = "Rf2Q4KhiZE9Ycm67ka3nsxrfv7Lloln2";
    private static final String CLIENT_SECRET = "QJnWAB-CfR-e3HWb6VPs7uhSZwAtKDdBw0JFXYnvH8qVH8KSW4hE-ir2svT7CFQs";
    private static final String DOMAIN = "dev-jly51lbvfz6xm0f4.us.auth0.com";  // Ej: tu-empresa.auth0.com
    private static final String REDIRECT_URI = "http://localhost:8001/auth/callback";

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

        System.out.println("Código recibido: " + code);

        if (code == null) {
            ctx.result("Error: No authorization code received.");
            return;
        }

        // Intercambiar código por token de acceso
        Map<String, String> tokenResponse = exchangeCodeForToken(code);
        System.out.println("Código recibido: " + tokenResponse);

        if (!tokenResponse.containsKey("access_token")) {
            ctx.result("Error al obtener el token de acceso.");
            return;
        }

        String accessToken = tokenResponse.get("access_token");

        System.out.println("llego a user info");
        System.out.println("Access Token recibido: " + accessToken);


        Map<String, String> userInfo = getUserInfo(accessToken);


        System.out.println("userInfo: "+userInfo);

        if (userInfo == null || !userInfo.containsKey("email")) {
            ctx.result("Error: No se pudo obtener información del usuario.");
            return;
        }

        String email = userInfo.get("email");
        System.out.println("Usuario autenticado: " + email);

        RepoUsuarios repo= RepoUsuarios.getInstance();

        // Verificar si el usuario existe en la base de datos
        Usuario usuario = repo.findByUsuario(email);




        if (usuario == null) {
            // Si no existe, crearlo
            PersonaFisica personaUsuario = new PersonaFisica();
            personaUsuario.setNombre(userInfo.get("given_name"));
            personaUsuario.setApellido(userInfo.get("family_name"));
            Rol rol= new Colaborador();
            rol.setPersona(personaUsuario);
            usuario = new Usuario();
            usuario.setUsuario(email);
            usuario.setRol(rol);
            System.out.println("usuario A crear: "+usuario +usuario.getRol()+usuario.getUsuario());
            repo.add_Usuario(usuario);
            System.out.println("Usuario registrado en la base de datos.");
        } else {
            System.out.println("Usuario encontrado en la base de datos.");
        }

        // Guardar usuario en la sesión
        System.out.println(usuario);
        ctx.sessionAttribute("user", usuario);
        String token = JwtUtil.generateToken(email,usuario.getRol().getId());
        String encodedToken = URLEncoder.encode(token, StandardCharsets.UTF_8);
        ctx.cookie("Auth", "Bearer" + encodedToken);
        ctx.redirect("/index");
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

        Map<String, String> request=sendRequest(TOKEN_URL, body,code);
        System.out.println("requestSend"+request);

        return request;
    }

    // Obtener los datos del usuario usando el token de acceso
    private static Map<String, String> getUserInfo(String accessToken) {
        String authorizationHeader = "Bearer " + accessToken;
        return sendRequest("https://" + DOMAIN + "/userinfo", authorizationHeader,accessToken);
    }

    // Realizar la solicitud HTTP con un header de autorización
    private static Map<String, String> sendRequest(String url, String body, String token) {
        Map<String, String> responseMap = new HashMap<>();

        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // Configurar la solicitud
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            if (token != null) {
                con.setRequestProperty("Authorization", "Bearer " + token);
            }
            con.setDoOutput(true);

            // Enviar el cuerpo de la solicitud (si aplica)
            if (body != null && !body.isEmpty()) {
                try (OutputStream os = con.getOutputStream()) {
                    byte[] input = body.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }
            }

            // Leer la respuesta
            int responseCode = con.getResponseCode();
            System.out.println("Código de respuesta HTTP: " + responseCode);

            try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }

                // Convertir la respuesta JSON a un Map
                responseMap = parseJson(response.toString());
            }

        } catch (Exception e) {
            System.out.println("Error en sendRequest: " + e.getMessage());
        }

        return responseMap;
    }

    private static Map<String, String> parseJson(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, new TypeReference<Map<String, String>>() {});
        } catch (Exception e) {
            System.out.println("Error al parsear JSON: " + e.getMessage());
            return new HashMap<>();
        }
    }


}
