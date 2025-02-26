package obs;

import domain.auth.Usuario;

import java.time.LocalDate;

public class Logger {
    public static void print(Usuario u, String mensaje){
        System.out.println("============================== LOG ==============================");
        if(u != null){
            System.out.println("#" + u.getId() + " " + u.getUsuario());
        }
        System.out.println(mensaje);
    }
}
