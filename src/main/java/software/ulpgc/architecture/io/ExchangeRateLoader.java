package software.ulpgc.architecture.io;

import software.ulpgc.architecture.model.Currency;
import software.ulpgc.architecture.model.ExchangeRate;

public interface ExchangeRateLoader {
    ExchangeRate load(Currency from, Currency to);
}
