package com.dds12.app;

import com.dds12.*;

import java.util.Scanner;

public class App  {

    public static void main( String[] args ) {
        System.out.println( "Hello World!" );
    }

    private Integer sig_codigo_rastreo = 0;
    private Integer obtenerCodigoRastreo() { sig_codigo_rastreo++; return sig_codigo_rastreo - 1; }

    private Number obtenerPrecioSegunTipo(String tipo) {
        if(tipo.equals("G")) {
            return 10.0;
        } else if(tipo.equals("T")) {
            return 15.0;
        } else if(tipo.equals("C")) {
            return 25.0;
        } else if(tipo.equals("E")) {
            return 50.0;
        } else {
            return 1.0; // error
        }
    }

    private Integer recibirEnvio() {
        Scanner scanner = new Scanner(System.in);

        // Remitente
        System.out.println("Nombre del remitente:");
        String nombre_remitente = scanner.nextLine();

        System.out.println("Dirección del remitente:");
        String direccion_remitente = scanner.nextLine();

        System.out.println("Localidad del remitente:");
        String localidad_remitente = scanner.nextLine();

        System.out.println("CP del remitente (solo numeros):");
        Integer cp_remitente = Integer.parseInt(scanner.nextLine());

        // Destinatario
        System.out.println("Nombre del destinatario:");
        String nombre_destinatario = scanner.nextLine();

        System.out.println("Dirección del destinatario:");
        String direccion_destinatario = scanner.nextLine();

        System.out.println("Localidad del destinatario:");
        String localidad_destinatario = scanner.nextLine();

        System.out.println("CP del destinatario (solo numeros):");
        Integer cp_destinatario = Integer.parseInt(scanner.nextLine());

        System.out.println("Tipo de envío (C->Carta, G->Giro, E->Encomienda, T->Telegrama):");
        String tipo = scanner.nextLine();
        Number precio = obtenerPrecioSegunTipo(tipo);

        System.out.println("El precio sería de $"+String.valueOf(precio)+", desea continuar? (S/n):");
        String respuesta = scanner.nextLine();
        if(respuesta.equals("s") || respuesta.equals("S") || respuesta.equals("")) {
            switch(tipo) {
                case "C":

                break;
                case "G":

                break;
                case "E":

                break;
                case "T":

                break;
            }
        } else {
            System.out.println("Envío cancelado");
        }
        return 0;
    }
}
