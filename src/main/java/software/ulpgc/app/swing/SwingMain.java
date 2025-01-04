package software.ulpgc.app.swing;

import software.ulpgc.app.fixerApi.FixerApiCurrenciesLoader;
import software.ulpgc.app.fixerApi.FixerApiExchangeRateLoader;
import software.ulpgc.architecture.control.Command;
import software.ulpgc.architecture.control.ExchangeMoneyCommand;
import software.ulpgc.architecture.model.Currency;

import java.util.List;

public class SwingMain {
    public static void main(String[] args) {
        SwingMainFrame mainFrame = new SwingMainFrame();
        List<Currency> currencies = new FixerApiCurrenciesLoader().load();
        Command command = new ExchangeMoneyCommand(
                mainFrame.getCurrencyDialog().define(currencies, "Select target currency:"),
                mainFrame.getMoneyDialog().define(currencies, "Enter amount to exchange:"),
                mainFrame.getMoneyDisplay(),
                new FixerApiExchangeRateLoader());
        mainFrame.put("exchange money", command);
        mainFrame.setVisible(true);
    }
}
