package software.ulpgc.app.fixerApi;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import software.ulpgc.architecture.io.CurrenciesLoader;
import software.ulpgc.architecture.model.Currency;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;

public class FixerApiCurrenciesLoader implements CurrenciesLoader {
    private final static String BASE_URL = "http://data.fixer.io/api/symbols?access_key=" + FixerApi.apiKey;

    @Override
    public List<Currency> load() {
        try {
            return getCurrenciesOf(loadBody());
        } catch (IOException e) {
            return emptyList();
        }
    }

    private List<Currency> getCurrenciesOf(String body) {
        Map<String, JsonElement> symbols = parseSymbolsFromBody(body);
        return createCurrencyListFromSymbols(symbols);
    }

    private Map<String, JsonElement> parseSymbolsFromBody(String body) {
        return new Gson().fromJson(body, JsonObject.class)
                .get("symbols")
                .getAsJsonObject()
                .asMap();
    }

    private List<Currency> createCurrencyListFromSymbols(Map<String, JsonElement> symbols) {
        List<Currency> currencies = new ArrayList<>();
        for (String symbol : symbols.keySet()) {
            currencies.add(new Currency(symbol, symbols.get(symbol).getAsString()));
        }
        return currencies;
    }


    private String loadBody() throws IOException {
        HttpURLConnection connection = createHttpConnection(BASE_URL);
        validateHttpResponse(connection);

        try (InputStream is = connection.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            return adaptBody(reader);
        }
    }

    private HttpURLConnection createHttpConnection(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        return connection;
    }

    private void validateHttpResponse(HttpURLConnection connection) throws IOException {
        int statusCode = connection.getResponseCode();
        if (statusCode < 200 || statusCode > 299) {
            throw new IOException("Error while loading Currencies list. HTTP error: " + statusCode);
        }
    }

    private static String adaptBody(BufferedReader reader) throws IOException {
        StringBuilder body = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            body.append(line);
        }
        return body.toString();
    }
}
