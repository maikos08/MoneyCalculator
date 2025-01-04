package software.ulpgc.architecture.io;

import software.ulpgc.architecture.model.Currency;

import java.util.List;

public interface CurrenciesLoader {
    List<Currency> load();
}
