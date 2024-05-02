package seguridad;

import java.util.Scanner;

public class ingresar_X_terminal {
    public static String Preguntar(String parametro){
        Scanner input1 = new Scanner(System.in);
        System.out.println("Ingrese " + parametro + " : ");
        return input1.next();
    }

}
