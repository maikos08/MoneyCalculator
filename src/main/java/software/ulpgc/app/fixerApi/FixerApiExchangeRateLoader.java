package software.ulpgc.app.fixerApi;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import software.ulpgc.architecture.io.ExchangeRateLoader;
import software.ulpgc.architecture.model.Currency;
import software.ulpgc.architecture.model.ExchangeRate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

public class FixerApiExchangeRateLoader implements ExchangeRateLoader {
    private final static String BASE_URL = "https://api.exchangeratesapi.io/v1/latest?access_key="
            + FixerApi.apiKey;

    @Override
    public ExchangeRate load(Currency from, Currency to) {
        try {
            LocalDate currentDate = getCurrentDate();
            double rate = getRateBetween(from, to);
            return new ExchangeRate(from, to, currentDate, rate);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static LocalDate getCurrentDate() {
        return LocalDate.now();
    }

    private double getRateBetween(Currency from, Currency to) throws IOException {
        String urlCustomized = buildCustomizedUrl(from, to);
        String body = fetchBodyFromUrl(urlCustomized);
        return calculateExchangeRate(body, from, to);
    }

    private String buildCustomizedUrl(Currency from, Currency to) {
        return BASE_URL + "&symbols=" + from.code() + "," + to.code();
    }

    private String fetchBodyFromUrl(String urlString) throws IOException {
        HttpURLConnection connection = createHttpConnection(new URL(urlString));
        validateHttpResponse(connection);
        return readResponseBody(connection);
    }

    private HttpURLConnection createHttpConnection(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        return connection;
    }

    private void validateHttpResponse(HttpURLConnection connection) throws IOException {
        int statusCode = connection.getResponseCode();
        if (statusCode < 200 || statusCode > 299) {
            throw new IOException("Error while loading rate. HTTP error: " + statusCode);
        }
    }

    private String readResponseBody(HttpURLConnection connection) throws IOException {
        try (InputStream is = connection.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            return adaptBody(reader);
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

    private double calculateExchangeRate(String body, Currency from, Currency to) {
        JsonObject rates = parseRatesFromBody(body);
        double fromRate = getRateForCurrency(rates, from);
        double toRate = getRateForCurrency(rates, to);
        return toRate / fromRate;
    }

    private JsonObject parseRatesFromBody(String body) {
        return new Gson().fromJson(body, JsonObject.class)
                .getAsJsonObject("rates");
    }

    private double getRateForCurrency(JsonObject rates, Currency currency) {
        return rates.getAsJsonPrimitive(currency.code()).getAsDouble();
    }
}
