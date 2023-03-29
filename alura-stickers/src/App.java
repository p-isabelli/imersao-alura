
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {

    public static void main(String[] args) throws Exception {

        // fazer uma conexão HTTP e buscar os top 250 filmes
        // String url = "https://imdb-api.com/en/API/Top250Movies/k_0ojt0yvm";
        String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        // extrair só os dados que interessam (titulo, poster, classificação)
        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);

        // exibir e manipular os dados 
        for(int i = 0; i < 3; i++) {
            Map<String, String> filme = listaDeFilmes.get(i);
            System.out.println("\u001b[1m=========================================================================================================\u001b[m ");
            System.out.println();
            System.out.println("\u001b[37m\u001b[1m\u001b[44mTitulo:\u001b[m " + filme.get("title"));
            System.out.println();
            System.out.println("\u001b[37m\u001b[1m\u001b[44mPoster:\u001b[m " + filme.get("image"));
            System.out.println();
            System.out.println("\u001b[37m\u001b[1m\u001b[44mNota:\u001b[m " + filme.get("imDbRating"));
            System.out.println();
            double rating = Double.parseDouble(filme.get("imDbRating"));
            int estrelas = (int) rating;

            for(int n=0; n < estrelas; n++){
                System.out.print("🌟");
            }

            System.out.println("\n");
        }
    }
}