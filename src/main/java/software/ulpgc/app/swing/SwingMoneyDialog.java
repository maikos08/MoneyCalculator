package software.ulpgc.app.swing;

import software.ulpgc.architecture.model.Currency;
import software.ulpgc.architecture.model.Money;
import software.ulpgc.architecture.view.CurrencyDialog;
import software.ulpgc.architecture.view.MoneyDialog;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SwingMoneyDialog extends JPanel implements MoneyDialog {
    private JTextField amountField;
    private CurrencyDialog currencyDialog;

    public SwingMoneyDialog() {
        this.setLayout(new FlowLayout());
        setBackground(new Color(240, 255, 240)); // Light green
    }

    @Override
    public MoneyDialog define(List<Currency> currencies, String header) {
        add(createStyledLabel(header));
        add(createAmountField());
        add(createCurrencyDialog(currencies));
        return this;
    }

    private Component createCurrencyDialog(List<Currency> currencies) {
        SwingCurrencyDialog dialog = new SwingCurrencyDialog();
        dialog.define(currencies, "Select source currency:");
        this.currencyDialog = dialog;
        return dialog;
    }

    private Component createAmountField() {
        JTextField textField = new JTextField();
        textField.setColumns(10);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        this.amountField = textField;
        return textField;
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setForeground(new Color(34, 139, 34)); // Forest green
        return label;
    }

    public String getAmountFieldText() {
        return amountField.getText();
    }


    @Override
    public Money get() {
        return new Money(toDouble(amountField.getText()), currencyDialog.get());
    }

    private double toDouble(String text) {
        return Double.parseDouble(text);
    }
}
