package software.ulpgc.architecture.view;

import software.ulpgc.architecture.model.Currency;

import java.util.List;

public interface CurrencyDialog {
    CurrencyDialog define(List<Currency> currencies, String header);
    Currency get();
}
