package software.ulpgc.app.swing;

import software.ulpgc.architecture.model.Currency;
import software.ulpgc.architecture.view.CurrencyDialog;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SwingCurrencyDialog extends JPanel implements CurrencyDialog {
    private JComboBox<Currency> currencySelector;

    public SwingCurrencyDialog() {
        setLayout(new FlowLayout());
        setBackground(new Color(240, 248, 255)); // Light blue
    }

    @Override
    public CurrencyDialog define(List<Currency> currencies, String header) {
        add(createStyledLabel(header));
        add(createCurrencySelector(currencies));
        return this;
    }

    private Component createCurrencySelector(List<Currency> currencies) {
        JComboBox<Currency> selector = new JComboBox<>();
        selector.setFont(new Font("Arial", Font.PLAIN, 14));
        for (Currency currency : currencies) selector.addItem(currency);
        this.currencySelector = selector;
        return selector;
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setForeground(new Color(0, 51, 102)); // Dark blue
        return label;
    }

    @Override
    public Currency get() {
        return currencySelector.getItemAt(currencySelector.getSelectedIndex());
    }
}
