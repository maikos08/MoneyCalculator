package software.ulpgc.architecture.control;

import software.ulpgc.architecture.io.ExchangeRateLoader;
import software.ulpgc.architecture.model.Currency;
import software.ulpgc.architecture.model.ExchangeRate;
import software.ulpgc.architecture.model.Money;
import software.ulpgc.architecture.view.CurrencyDialog;
import software.ulpgc.architecture.view.MoneyDialog;
import software.ulpgc.architecture.view.MoneyDisplay;

public class ExchangeMoneyCommand implements Command {
    private final CurrencyDialog currencyDialog;
    private final MoneyDialog moneyDialog;
    private final MoneyDisplay display;
    private final ExchangeRateLoader loader;

    public ExchangeMoneyCommand(CurrencyDialog currencyDialog, MoneyDialog moneyDialog, MoneyDisplay display, ExchangeRateLoader loader) {
        this.currencyDialog = currencyDialog;
        this.moneyDialog = moneyDialog;
        this.display = display;
        this.loader = loader;
    }

    @Override
    public void execute() {
        Money money = moneyDialog.get();
        Currency currency = currencyDialog.get();
        ExchangeRate exchangeRate = loader.load(money.currency(), currency);
        Money result = new Money((double) (money.amount() * exchangeRate.rate()), currency);
        display.show(result);
    }
}
