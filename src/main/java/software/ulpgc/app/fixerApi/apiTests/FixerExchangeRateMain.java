package software.ulpgc.app.fixerApi.apiTests;

import software.ulpgc.app.fixerApi.FixerApiExchangeRateLoader;
import software.ulpgc.architecture.model.Currency;
import software.ulpgc.architecture.model.ExchangeRate;

public class FixerExchangeRateMain {
    public static void main(String[] args) {
        FixerApiExchangeRateLoader loader = new FixerApiExchangeRateLoader();
        ExchangeRate exchangeRate1 = loader.load(
                new Currency("EUR", "euro"),
                new Currency("JPY", "japanese yen"));
        ExchangeRate exchangeRate2 = loader.load(
                new Currency("USD", "dollar"),
                new Currency("JPY", "japanese yen"));
        System.out.println(exchangeRate1);
        System.out.println(exchangeRate2);
    }
}
