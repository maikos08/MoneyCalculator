package software.ulpgc.app.swing;

import software.ulpgc.architecture.model.Money;
import software.ulpgc.architecture.view.MoneyDisplay;

import javax.swing.*;
import java.awt.*;

public class SwingMoneyDisplay extends JLabel implements MoneyDisplay {

    public SwingMoneyDisplay() {
        this.setHorizontalAlignment(JLabel.CENTER);
        this.setVerticalAlignment(JLabel.CENTER);
        this.setFont(new Font("Arial", Font.BOLD, 18));
        this.setForeground(new Color(25, 25, 112)); // Midnight blue
    }

    @Override
    public void show(Money money) {
        String formattedAmount = String.format("%.2f", money.amount());
        this.setText(formattedAmount + " " + money.currency().code());
    }
}
