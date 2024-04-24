package com.dds12.app;

import com.dds12.empleado.Empleado;
import com.dds12.empleado.EnumEmpleado;
import com.dds12.entidad.Entidad;
import com.dds12.envio.Envio;
import com.dds12.envio.carta.Carta;
import com.dds12.envio.carta.EnumCarta;
import com.dds12.envio.carta.EnumSellado;
import com.dds12.envio.encomienda.Encomienda;
import com.dds12.envio.encomienda.EnumEncomienda;
import com.dds12.envio.giro.Giro;
import com.dds12.envio.telegrama.EnumTelegrama;
import com.dds12.envio.telegrama.Telegrama;
import com.dds12.rastreo.Rastreo;
import com.dds12.sucursal.Sucursal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class App  {

    private static List<Sucursal> sucursales;

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

        Integer id_sucursal = sucursales.size();
        while(id_sucursal > sucursales.size() - 1){
            System.out.println("Número de sucursal a realizar el envio:");
            id_sucursal = Integer.parseInt(scanner.nextLine());
            if(id_sucursal > sucursales.size() - 1){
                System.out.println("Número de sucursal inexistente, intente otra vez.");
            }
        }

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

        // Tipo y confirmación
        System.out.println("Tipo de envío (C->Carta, G->Giro, E->Encomienda, T->Telegrama):");
        String tipo = scanner.nextLine();
        Number precio = obtenerPrecioSegunTipo(tipo);

        System.out.println("El precio sería de $"+String.valueOf(precio)+", desea continuar? (S/n):");
        String respuesta = scanner.nextLine();
        if(respuesta.equals("s") || respuesta.equals("S") || respuesta.equals("")) {
            Envio envio = new Envio();
            Integer nuevo_codigo_rastreo = obtenerCodigoRastreo();
            Entidad remitente = new Entidad(
                    nombre_remitente,
                    direccion_remitente,
                    localidad_remitente,
                    cp_remitente
            );
            Entidad destinatario = new Entidad(
                    nombre_destinatario,
                    direccion_destinatario,
                    localidad_destinatario,
                    cp_destinatario
            );
            Rastreo rastreo = new Rastreo(
                    nuevo_codigo_rastreo,
                    new Date(),
                    null,
                    new ArrayList<Date>(),
                    new ArrayList<String>()
            );
            switch(tipo) {
                case "C":
                    System.out.println("Tipo de carta (SIMPLE, CERTIFICADA, EXPRESA):");
                    EnumCarta tipoCarta = EnumCarta.valueOf(scanner.nextLine());

                    System.out.println("Ya tiene estampilla? (S/n):");
                    String respuestaEstampilla = scanner.nextLine();
                    EnumSellado sellado;
                    if(respuestaEstampilla.equals("s") || respuestaEstampilla.equals("S") || respuestaEstampilla.equals("")) {
                        sellado = EnumSellado.NEGRO;
                    } else {
                        sellado = EnumSellado.ROJO;
                    }

                    Carta carta = new Carta(
                        remitente,
                        destinatario,
                        precio,
                        nuevo_codigo_rastreo,
                        rastreo,
                        sucursales.get(id_sucursal).asignarCartero(),
                        sellado,
                        tipoCarta
                    );
                    envio = carta;
                break;
                case "G":
                    System.out.println("Importe del giro $:");
                    Number importe = Float.parseFloat(scanner.nextLine());

                    Giro giro = new Giro(
                            remitente,
                            destinatario,
                            precio,
                            nuevo_codigo_rastreo,
                            rastreo,
                            sucursales.get(id_sucursal).asignarCartero(),
                            importe
                    );
                    envio = giro;
                break;
                case "E":
                    System.out.println("Ya armó su encomienda o necesita packets del correo? (S/n):");
                    String respuesta_enc = scanner.nextLine();
                    EnumEncomienda tipo_encomienda;
                    if(respuesta_enc.equals("s") || respuesta_enc.equals("S") || respuesta_enc.equals("")) {
                        tipo_encomienda = EnumEncomienda.POR_PERSONA;
                    } else {
                        tipo_encomienda = EnumEncomienda.PACKET;
                    }

                    Encomienda encomienda = new Encomienda(
                            remitente,
                            destinatario,
                            precio,
                            nuevo_codigo_rastreo,
                            rastreo,
                            sucursales.get(id_sucursal).asignarCartero(),
                            tipo_encomienda
                    );
                    envio = encomienda;
                break;
                case "T":
                    System.out.println("Escriba su telegrama:");
                    String texto = scanner.nextLine();

                    System.out.println("Clase de telegrama (CARTA_DOCUMENTO, EVENTO_DETERMINADO, INVITACION):");
                    EnumTelegrama clase_telegrama = EnumTelegrama.valueOf(scanner.nextLine());

                    Telegrama telegrama = new Telegrama(
                            remitente,
                            destinatario,
                            precio,
                            nuevo_codigo_rastreo,
                            rastreo,
                            sucursales.get(id_sucursal).asignarCartero(),
                            texto,
                            clase_telegrama
                    );
                    envio = telegrama;
                break;
            }
            sucursales.get(id_sucursal).realizarEnvio(envio);
            System.out.println("Su código de rastreo es "+nuevo_codigo_rastreo);
            return nuevo_codigo_rastreo;
        } else {
            System.out.println("Envío cancelado");
        }
        return 0;
    }

    private void rastrearEnvio() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Número de sucursal que realizó el envío:");
        Integer id_sucursal = Integer.parseInt(scanner.nextLine());

        if(id_sucursal > sucursales.size() - 1){
            System.out.println("Sucursal inexistente.");
            return;
        }

        System.out.println("Código de rastreo:");
        Integer codigo_rastreo = Integer.parseInt(scanner.nextLine());

        Sucursal sucursal = sucursales.get(id_sucursal);
        for(Envio e : sucursal.getEnvios()){
            if(e.getCodigo_rastreo() == codigo_rastreo){
                imprimirRastreo(e.getRastreo_envio());
                return;
            }
        }
        System.out.println("No se encontró el envío en la sucursal.");
    }

    private void imprimirRastreo(Rastreo rastreo) {
        if(rastreo.getFecha_recibido() == null) {
            System.out.println("ENVÍO EN CAMINO");
        } else {
            System.out.println("ENVÍO ENTREGADO");
        }

        for(int i=0; i<rastreo.getFechas_en_sucursal().size(); i++) {
            System.out.println(rastreo.getFechas_en_sucursal().get(i).toString() +" - "+rastreo.getLugares_enviado().get(i));
        }
    }

    private void avanzarEnvio() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Número de sucursal que realizó el envío:");
        Integer id_sucursal = Integer.parseInt(scanner.nextLine());

        if(id_sucursal > sucursales.size() - 1){
            System.out.println("Sucursal inexistente.");
            return;
        }

        System.out.println("Código de rastreo:");
        Integer codigo_rastreo = Integer.parseInt(scanner.nextLine());

        Sucursal sucursal = sucursales.get(id_sucursal);
        for(Envio e : sucursal.getEnvios()){
            if(e.getCodigo_rastreo() == codigo_rastreo){
                System.out.println("Dónde llegó?:");
                String localidad = scanner.nextLine();
                sucursal.avanzarEnvio(codigo_rastreo, localidad);
                return;
            }
        }
        System.out.println("No se encontró el envío en la sucursal.");
    }

    private void entregarEnvio() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Número de sucursal que realizó el envío:");
        Integer id_sucursal = Integer.parseInt(scanner.nextLine());

        if(id_sucursal > sucursales.size() - 1){
            System.out.println("Sucursal inexistente.");
            return;
        }

        System.out.println("Código de rastreo:");
        Integer codigo_rastreo = Integer.parseInt(scanner.nextLine());

        Sucursal sucursal = sucursales.get(id_sucursal);
        for(Envio e : sucursal.getEnvios()){
            if(e.getCodigo_rastreo() == codigo_rastreo){
                e.getRastreo_envio().setFecha_recibido(new Date());
                System.out.println("Entregado por "+e.getCartero().getNombre()+" en "+e.getDestinatario().getDireccion());
                return;
            }
        }
        System.out.println("No se encontró el envío en la sucursal.");
    }

    private void menu() {
        Scanner scanner = new Scanner(System.in);
        Integer opcion = 0;
        while(opcion != 9) {
            System.out.println("1 - Realizar envío");
            System.out.println("2 - Rastrear envío");
            System.out.println("3 - Avanzar envío");
            System.out.println("4 - Entregar envío");
            System.out.println("9 - Salir");
            opcion = Integer.parseInt(scanner.nextLine());
            if(opcion == 1) { recibirEnvio(); }
            if(opcion == 2) { rastrearEnvio(); }
            if(opcion == 3) { avanzarEnvio(); }
            if(opcion == 4) { entregarEnvio(); }
        }
    }

    public static void main( String[] args ) {
        App app = new App();

        // Algunos datos
        sucursales = new ArrayList<>();
        List<Empleado> empleados = new ArrayList<>();
        empleados.add(new Empleado("Tomás", "Cerezo", 67, EnumEmpleado.CARTERO));
        Sucursal sucursal_0 = new Sucursal(0, "Nogoyá 6367", "Villa Real", empleados, new ArrayList<>());
        sucursales.add(sucursal_0);

        app.menu();
    }
}
