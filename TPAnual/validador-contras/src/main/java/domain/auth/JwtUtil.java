package domain.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import persistence.Repos.RepoUsuarios;

public class JwtUtil {
    private static final Key SECRET_KEY;
    private static final long EXPIRATION_TIME = 86400000L;

    public static String generateToken(String username) {
        return Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)).signWith(SECRET_KEY).compact();
    }

    public static String validateToken(String token) {
        String usu = null;

        try {
            usu = ((Claims)Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody()).getSubject();
        } catch (Exception var3) {
            System.out.println("Problema con sesion");
        }

        return usu;
    }

    public static Usuario validateTokenAndGetUser(String token) {
        String usu = validateToken(token);
        RepoUsuarios r = new RepoUsuarios();
        return r.findByUsuario(usu);
    }

    static {
        SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }
}
