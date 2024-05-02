package seguridad;

import lombok.experimental.StandardException;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
//import java.util.Arrays;
import java.util.Scanner; // Import the Scanner class to read text files
public class log_In {

    private static String N_Usuario;
    private static String Contra;
    private static final Integer Cant_Intentos_De_LogIn = 5;
    public static boolean paso_LogIn= false;

    public static void main(String[] args) {

        for(int i=0; i<Cant_Intentos_De_LogIn; i++){
            wait(i*1000); //Tiempo de espera entre intentos fallidos

            N_Usuario = ingresar_X_terminal.Preguntar("nombre de usuario");
            Contra = ingresar_X_terminal.Preguntar("contraseña");

            if(leer_archivo_txt()){
                paso_LogIn = true;
                break; //TODO agregar que pasa despues del logIn
            }

            if(i<Cant_Intentos_De_LogIn-1) System.out.println("Espere " + (i+1) + " segundos e intente nuevamente");
        }
        if(!paso_LogIn) System.out.println("Se superaró el limite de "+ Cant_Intentos_De_LogIn + " intentos fallidos");

    }

    public static boolean leer_archivo_txt(){
//Abre y lee el archivo de texto
        try {
            File archivo_txt = new File("usuario-contrasena.txt");
            Scanner myReader = new Scanner(archivo_txt);
            while (myReader.hasNextLine()){
                String data = myReader.nextLine();

                //Buscar Usuario y contra en texto
                if(N_Usuario.equals(data.split(" split ")[0])){
                    if(Contra.equals(data.split(" split ")[1])){
                        System.out.println("Bienvenido!");
                        return true;
                    }
                }
            }
            myReader.close();
        } catch (FileNotFoundException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        System.out.println("Usuario o Contraseña Incorrecta");
        return false;
    }

    public static void wait(int ms){
        try {
            Thread.sleep(ms);
        } catch(InterruptedException ex){
            Thread.currentThread().interrupt();
        }
    }



}
