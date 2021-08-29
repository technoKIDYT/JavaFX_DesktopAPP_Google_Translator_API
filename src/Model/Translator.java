package Model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import sample.Utility;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class Translator implements TranslatorHelper {

    private String host;
    private String charset;
    private String x_rapidapi_host;
    private String x_rapidapi_key;
    private String accept_encoding;
    private String baseUrl;
    private String content_type;
    private JsonObject jsonObject;
    private JsonArray jsonArray;
    private Utility utility;


    public Translator() {
        x_rapidapi_key = "<YOUR_KEY>";
        x_rapidapi_host = "google-translate1.p.rapidapi.com";
        accept_encoding = "application/gzip";
        baseUrl = "https://google-translate1.p.rapidapi.com/language/translate/v2/";
        content_type = "application/x-www-form-urlencoded";
        utility = new Utility();
    }

    @Override
    public ArrayList<Language> getAllLang() {
        Root root = null;
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(baseUrl + "languages"))
                .header("accept-encoding", accept_encoding)
                .header("x-rapidapi-key", x_rapidapi_key)
                .header("x-rapidapi-host", x_rapidapi_host)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> stringHttpResponse;

        try {
            stringHttpResponse = HttpClient.newHttpClient().send(httpRequest, HttpResponse.BodyHandlers.ofString());
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonElement jsonElement = JsonParser.parseString(stringHttpResponse.body().toString());
            System.out.println(jsonElement);
            String pretty = gson.toJson(jsonElement);
            System.out.println(pretty);

            ObjectMapper objectMapper = new ObjectMapper();
            root = objectMapper.readValue(jsonElement.toString(), Root.class);

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return root.getData().getLanguages();
    }

    @Override
    public Model.translated.data.Root translate(String from, String to, String text) {
        Model.translated.data.Root translateRoot = null;
        String encodedMessage = utility.encode(text);
        String query = "q=" + encodedMessage + "&target=" + to + "&source=" + from;
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(baseUrl))
                .header("content-type", content_type)
                .header("accept-encoding", accept_encoding)
                .header("x-rapidapi-key", x_rapidapi_key)
                .header("x-rapidapi-host", x_rapidapi_host)
                .method("POST", HttpRequest.BodyPublishers.ofString(query))
                .build();

        HttpResponse<String> stringHttpResponse;
        try {
            stringHttpResponse = HttpClient.newHttpClient().send(httpRequest, HttpResponse.BodyHandlers.ofString());
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonElement jsonElement = JsonParser.parseString(stringHttpResponse.body().toString());
            System.out.println(jsonElement);
            String pretty = gson.toJson(jsonElement);
            System.out.println(pretty);
            ObjectMapper objectMapper = new ObjectMapper();
            translateRoot = objectMapper.readValue(jsonElement.toString(), Model.translated.data.Root.class);
        } catch (Exception exception) {

        }
        return translateRoot;
    }
}
