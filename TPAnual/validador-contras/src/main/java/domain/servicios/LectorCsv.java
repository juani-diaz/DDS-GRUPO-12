package domain.servicios;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import domain.personas_empresas.ColaboradorHumano;
import domain.personas_empresas.Documento;
import domain.personas_empresas.MedioContacto;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LectorCsv {
    private String rutaArchivo;
    private Mailer mailer;
    private List<ColaboradorHumano> colaboradoresExistentes; // este podria ser abstraido a una clase tipo repo de colaboradores

    public LectorCsv(String rutaArchivo, Mailer mailer) {
        this.rutaArchivo = rutaArchivo;
        this.mailer = mailer;
        this.colaboradoresExistentes = new ArrayList<>();
    }

    private List<String[]> leerArchivo(String csvFile) {
        try (CSVReader reader = new CSVReader(new FileReader(csvFile))) {
            return reader.readAll();
        } catch (IOException | CsvException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<ColaboradorHumano> cargarArchivo() {
        List<ColaboradorHumano> nuevosColaboradores = new ArrayList<>();
        List<String[]> lineasArchivo = leerArchivo(rutaArchivo);

        for (int i = 1; i < lineasArchivo.size(); i++) { // i =1 para saltar encabezado que tiene los nombres de columnas
            String[] linea = lineasArchivo.get(i);
            ColaboradorHumano colaborador = cargarColaborador(linea);
            if (colaborador != null) {
                if (!existeColaborador(colaborador)) {
                    nuevosColaboradores.add(colaborador);
                    colaboradoresExistentes.add(colaborador);
                    enviarCorreoBienvenida(colaborador);
                }
            }
        }

        return nuevosColaboradores;
    }

    private ColaboradorHumano cargarColaborador(String[] linea) {
        try {
            Documento documento = new Documento();
            documento.setTipo(linea[0]);
            documento.setNumero(linea[1]);
            String nombre = linea[2];
            String apellido = linea[3];
            MedioContacto medioDeContacto = new MedioContacto();
            medioDeContacto.setEmails(Collections.singletonList(linea[4]));
            LocalDate fechaNacimiento = LocalDate.parse(linea[5], DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            ColaboradorHumano colaborador = new ColaboradorHumano();
            colaborador.setNombre(nombre);
            colaborador.setApellido(apellido);
            colaborador.setDocumento(documento);
            colaborador.setMedioDeContacto(medioDeContacto);
            colaborador.setFechaNacimiento(fechaNacimiento);

            return colaborador;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean existeColaborador(ColaboradorHumano colaborador) {
        for (ColaboradorHumano existente : colaboradoresExistentes) {
            if (existente.getDocumento().equals(colaborador.getDocumento())) {
                return true;
            }
        }
        return false;
    }

    private void enviarCorreoBienvenida(ColaboradorHumano colaborador) {
        String destinatario = colaborador.getMedioDeContacto().getEmails().get(0);
        String header = "¡Bienvenido al sistema!";
        String body = "Hola " + colaborador.getNombre() + ",\n\n" +
                "Gracias por unirte a nuestro sistema. ¡Estamos encantados de tenerte con nosotros!\n\n" +
                "Saludos,\nEl equipo.";

        Mailer mailer = new Mailer(header, body, destinatario);
        mailer.enviarMail();
    }
}
