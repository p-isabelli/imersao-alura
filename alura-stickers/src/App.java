import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {

    public static void main(String[] args) throws Exception {

        // fazer uma conexão HTTP e buscar os top 250 filmes
        //String url = "https://imdb-api.com/en/API/Top250Movies/k_0ojt0yvm";
        //String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";
        String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/MostPopularTVs.json";
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        // extrair só os dados que interessam (titulo, poster, classificação)
        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);

        var diretorio = new File("../figurinhas../");
        diretorio.mkdir();

        // exibir e manipular os dados 
        var geradora = new GeradoraDeFigurinhas();
        for (int index = 0; index < 5; index++) {
            var filme = listaDeFilmes.get(index);
            

            String urlImagem = filme.get("image");
            String titulo = filme.get("title");
            double classificacao = Double.parseDouble(filme.get("imDbRating"));
            
            String textoFigurinha;
            if (classificacao >= 8.0){
                textoFigurinha = "TOPZERA";
            } else {
                textoFigurinha = "RUIM...";
            }

            InputStream inputStream = new URL(urlImagem).openStream();
            String nomeArquivo = "../figurinhas/" + titulo + ".png";

            geradora.cria(inputStream, nomeArquivo, textoFigurinha);

            System.out.println(titulo);
            System.out.println();
        }
    }
}