/*
package domain.servicios;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import com.opencsv.exceptions.CsvException;


public class LectorCsv {
        private String rutaArchivo;
        private Mailer mailer;

    public String cargarArchivo() {


        List<PersonaColaboradora> colaboradores = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(csvFile))) {
            List<String[]> lines = reader.readAll();

            for (int i = 1; i < lines.size(); i++) {
                String[] line = lines.get(i);
                PersonaColaboradora colaborador = new PersonaColaboradora(
                );
                //cargar colaboradores
                colaboradores.add(colaborador);
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }

    }

    private void cargarColaborador(){

    new

    }

}
*/