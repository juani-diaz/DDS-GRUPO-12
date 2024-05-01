package seguridad;

import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;
import lombok.Getter;
import lombok.Setter;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors

import java.util.Scanner;

@Getter
@Setter
public class crearUsuario {
  private static String N_Usuario;
  private static String Contra;
  private static boolean buena_contra = false;

  public static void main(String[] args){
    System.out.println("Sistema de Registro de Nuevo Usuario: ");

    N_Usuario = ingresar_X_terminal.Preguntar("nombre de usuario");
    //String Contra;

    while(!buena_contra){

      Contra = ingresar_X_terminal.Preguntar("contraseña");

      if(comparar_contra_contra_top10000(Contra) & largo_contra_OK(Contra)){
        buena_contra = true;
      }

    }

    escribir_en_archivo_txt();

    evaluar_contrasenia(Contra); //Opcional, se puede sacar

  }

  public static void escribir_en_archivo_txt(){
    //Abre y escribe en archivo
    try {
      FileWriter archivo_txt = new FileWriter("usuario-contrasena.txt", true);
      archivo_txt.write(System.lineSeparator());
      archivo_txt.write(N_Usuario +" split " + Contra);
      archivo_txt.close();
      System.out.println("Successfully wrote to the file.");
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }

  }
  public static boolean largo_contra_OK(String contra){

    if(contra.length() > 7 && contra.length() < 64){
      return true;
    }else{
      System.out.println("ERROR. La contraseña debe tener entre 8 y 64 caracteres ");
      return false;
    }
  }
  public static boolean comparar_contra_contra_top10000(String contra){
    try {
      File archivo_txt = new File("TPAnual/top10000_Peores_Contras.txt");
      Scanner myReader = new Scanner(archivo_txt);

      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();

        if(contra.equals(data)){
          System.out.println("La contraseña pertenece al TOP 10000 peores");
          return false;
        }
      }
      myReader.close();

    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
    return true;
  }
  public static void evaluar_contrasenia(String contra){
    //Utilizo la libreria zxcvbn4j para asegurarme de que sea una buena contraseña
    Zxcvbn zxcvbn = new Zxcvbn();
    Strength strength = zxcvbn.measure(contra);

    // Integer from 0-4 (useful for implementing a strength bar)
    // 0 Weak        （guesses < 10^3 + 5）
    // 1 Fair        （guesses < 10^6 + 5）
    // 2 Good        （guesses < 10^8 + 5）
    // 3 Strong      （guesses < 10^10 + 5）
    // 4 Very strong （guesses >= 10^10 + 5）
    int Contra_Score = strength.getScore();

    System.out.println("Contra_Score: "+ Contra_Score);
  }


}









