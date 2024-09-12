package domain.servicios;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import domain.persona.Documento;
import domain.persona.MedioDeContacto;
import domain.persona.Persona;
import domain.persona.PersonaFisica;
import domain.rol.Colaborador;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LectorCsv {
    private InputStream stream;
    private Mailer mailer;
    private List<Colaborador> colaboradoresExistentes;


    public List<Colaborador> getColaboradoresExistentes() {
        return colaboradoresExistentes;
    }

    public LectorCsv(InputStream inputStream, Mailer mailer) {
        this.stream = inputStream;
        this.mailer = mailer;
        this.colaboradoresExistentes = new ArrayList<>();
    }

    private List<String[]> leerArchivo(InputStream inputStream) {
        try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream))) {
            return reader.readAll();
        } catch (IOException | CsvException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Colaborador> cargarArchivo() {
        List<Colaborador> nuevosColaboradores = new ArrayList<>();
        List<String[]> lineasArchivo = leerArchivo(stream);

        for (int i = 1; i < lineasArchivo.size(); i++) { // i =1 para saltar encabezado que tiene los nombres de columnas
            String[] linea = lineasArchivo.get(i);
            Colaborador colaborador = cargarColaborador(linea);
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

    private Colaborador cargarColaborador(String[] linea) {
        try {
            Documento documento = new Documento();
            documento.setTipo(linea[0]);
            documento.setNumero(linea[1]);
            String nombre = linea[2];
            String apellido = linea[3];
            MedioDeContacto medioDeContacto = new MedioDeContacto();
            medioDeContacto.setEmails(Collections.singletonList(linea[4]));
            LocalDate fechaNacimiento = LocalDate.parse(linea[5], DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            Persona p = new PersonaFisica(nombre, medioDeContacto, null, documento, apellido, null, null, fechaNacimiento);
            Colaborador colaborador = new Colaborador(p, new ArrayList<>(), null, new ArrayList<>(), null);

            return colaborador;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean existeColaborador(Colaborador colaborador) {
        for (Colaborador existente : colaboradoresExistentes) {
            if (existente.getPersona().getDocumento().equals(colaborador.getPersona().getDocumento())) {
                return true;
            }
        }
        return false;
    }

    private void enviarCorreoBienvenida(Colaborador colaborador) {
        String destinatario = colaborador.getPersona().getMedioDeContacto().getEmails().get(0);
        String header = "¡Bienvenido al sistema!";
        String body = "Hola " + colaborador.getPersona().getNombre() + ",\n\n" +
                "Gracias por unirte a nuestro sistema. ¡Estamos encantados de tenerte con nosotros!\n\n" +
                "Saludos,\nEl equipo.";

        Mailer mailer = new Mailer();
        mailer.enviarMail(header, body, destinatario);
    }
}
