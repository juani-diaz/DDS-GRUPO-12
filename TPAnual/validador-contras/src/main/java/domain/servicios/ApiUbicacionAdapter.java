package domain.servicios;
public class ApiUbicaciones implements AdapterUbicacionesInterface{

    private String adaptadoURL;

    private static String genericGet(String url, String key) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + key))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    private List<Ubicacion> ApiUbicacionAdapter(Ubicacion ubicacion){

        //depende como lo devuelve la api habria que modificarlo a nuestra manera de usarlo, como hacemos nosotros el postman asumo que no sera necesario

        String ubicaciones = genericGet(adaptadoURL, ubicacion);


        return ubicaciones;
    }
}