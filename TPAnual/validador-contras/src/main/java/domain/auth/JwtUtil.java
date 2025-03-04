package domain.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import persistence.Repos.RepoUsuarios;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JwtUtil {
    private static final Key SECRET_KEY;
    private static final long EXPIRATION_TIME = 86400000L;
    private static List<String> blacklist = new ArrayList<String>();

    public static String generateToken(String username,Integer roleId) {
        return Jwts.builder().setSubject(username).claim("usuario", username)
                .claim("roleId", roleId).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)).signWith(SECRET_KEY).compact();
    }

    public static String validateToken(String token) {
        try {
            if (blacklist.contains(token)) {
                throw new RuntimeException("Token en lista negra");
            }

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getSubject();
        } catch (Exception e) {
            System.out.println("Problema con sesión: " + e.getMessage());
            return null;
        }
    }


    public static void invalidateToken(String token) {
        blacklist.add(token);
    }

    public static Usuario validateTokenAndGetUser(String token) {
        String usu = validateToken(token);
        RepoUsuarios r = RepoUsuarios.getInstance();
        return r.findByUsuario(usu);
    }

    public static Claims getClaimsFromToken(String token) {
        try {
            if (token.startsWith("Bearer")) {
                token = token.substring(6).trim();
            }
            String decodedUrl=URLDecoder.decode(token, StandardCharsets.UTF_8);
            return Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(decodedUrl)
                    .getBody();
        } catch (Exception e) {
            System.out.println("Error al extraer claims: " + e.getMessage());
            return null;
        }
    }

    static {
        SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }
}
