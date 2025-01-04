package software.ulpgc.app.fixerApi.apiTests;

import software.ulpgc.app.fixerApi.FixerApiCurrenciesLoader;
import software.ulpgc.architecture.model.Currency;

import java.util.List;

public class FixerCurrenciesMain {
    public static void main(String[] args) {
        FixerApiCurrenciesLoader currenciesLoader = new FixerApiCurrenciesLoader();
        List<Currency> currencies = currenciesLoader.load();
        for (Currency currency : currencies) {
            System.out.println(currency);
        }
    }
}
