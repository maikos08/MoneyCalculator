package software.ulpgc.app.swing;

import software.ulpgc.architecture.control.Command;
import software.ulpgc.architecture.view.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class SwingMainFrame extends JFrame {
    private final Map<String, Command> commands;
    private MoneyDisplay moneyDisplay;
    private MoneyDialog moneyDialog;
    private CurrencyDialog currencyDialog;

    public SwingMainFrame() {
        commands = new HashMap<>();
        setTitle("Money Calculator");
        setSize(800, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        this.setLayout(new GridLayout(4, 1, 10, 10));
        setBackground(new Color(112, 128, 144)); // Slate gray

        add(createMoneyDialog());
        add(createCurrencyDialog());
        add(createExchangeButton());
        add(createMoneyDisplay());
    }

    private Component createExchangeButton() {
        JPanel panel = new JPanel();
        JButton button = createStyledButton("Convert Currency");
        button.addActionListener(_ -> handleExchangeButtonClick());
        panel.add(button);
        return panel;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(70, 130, 180)); // Steel blue
        button.setForeground(Color.WHITE);
        return button;
    }

    private void handleExchangeButtonClick() {
        String amountText = getAmountFieldText();

        if (isAmountFieldEmpty(amountText)) {
            showEmptyAmountWarning();
            return;
        }

        executeExchangeCommand();
    }

    private String getAmountFieldText() {
        SwingMoneyDialog moneyDialog = (SwingMoneyDialog) this.moneyDialog;
        return moneyDialog.getAmountFieldText();
    }

    private boolean isAmountFieldEmpty(String amountText) {
        return amountText == null || amountText.trim().isEmpty();
    }

    private void showEmptyAmountWarning() {
        JOptionPane.showMessageDialog(
                this,
                "Please enter an amount to exchange.",
                "Input Error",
                JOptionPane.WARNING_MESSAGE
        );
    }

    private void executeExchangeCommand() {
        commands.get("exchange money").execute();
    }


    private Component createMoneyDisplay() {
        SwingMoneyDisplay display = new SwingMoneyDisplay();
        this.moneyDisplay = display;
        return display;
    }

    private Component createCurrencyDialog() {
        SwingCurrencyDialog dialog = new SwingCurrencyDialog();
        this.currencyDialog = dialog;
        return dialog;
    }

    private Component createMoneyDialog() {
        SwingMoneyDialog dialog = new SwingMoneyDialog();
        this.moneyDialog = dialog;
        return dialog;
    }

    public MoneyDisplay getMoneyDisplay() {
        return moneyDisplay;
    }

    public MoneyDialog getMoneyDialog() {
        return moneyDialog;
    }

    public CurrencyDialog getCurrencyDialog() {
        return currencyDialog;
    }

    public void put(String key, Command value) {
        commands.put(key, value);
    }
}
