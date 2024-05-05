package domain.archivos;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class LectorArchivo {
        private BufferedReader br;
        private String ruta;
        public String traerLinea(String ruta) {
            try {
                if (br == null || !ruta.equals(LectorArchivo.this.ruta)) {
                    br = new BufferedReader(new FileReader(ruta));
                    LectorArchivo.this.ruta = ruta;
                }
                String linea = br.readLine();
                if (linea == null) {
                    br.close();
                    br = new BufferedReader(new FileReader(ruta));
                    return null;
                }
                return linea;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }