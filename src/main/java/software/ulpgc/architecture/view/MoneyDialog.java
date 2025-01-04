package software.ulpgc.architecture.view;

import software.ulpgc.architecture.model.Currency;
import software.ulpgc.architecture.model.Money;

import java.util.List;

public interface MoneyDialog {
    MoneyDialog define(List<Currency> currencies, String header);
    Money get();
}
