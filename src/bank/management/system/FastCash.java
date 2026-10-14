        package bank.management.system;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import bank.management.system.service.LegacyBankService;

public class FastCash extends JFrame implements ActionListener {

    private final String pin;

    private JButton b1, b2, b3, b4, b5, b6, b7;

    private final Color teal = new Color(18, 112, 120);
    private final Color darkTeal = new Color(12, 65, 75);
    private final Color lightBackground = new Color(242, 247, 249);
    private final Color textColor = new Color(35, 52, 63);

    public FastCash(String pin) {
        this.pin = pin;

        setTitle("Bank Management System - Fast Cash");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        // Main background
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(lightBackground);
        setContentPane(mainPanel);

        // Fast cash card
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(30, 40, 30, 40));
        card.setPreferredSize(new Dimension(540, 570));

        // Header icon
        JLabel iconLabel = new JLabel("₹");
        iconLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        iconLabel.setForeground(Color.WHITE);
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabel.setOpaque(true);
        iconLabel.setBackground(teal);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        iconLabel.setPreferredSize(new Dimension(70, 70));
        iconLabel.setMaximumSize(new Dimension(70, 70));

        JLabel heading = new JLabel("Fast Cash");
        heading.setFont(new Font("SansSerif", Font.BOLD, 27));
        heading.setForeground(darkTeal);
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Choose an amount to withdraw");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitle.setForeground(new Color(115, 130, 140));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel limitLabel = new JLabel(
                "Select a preset amount in Indian Rupees"
        );
        limitLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        limitLabel.setForeground(textColor);
        limitLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Preset amount buttons
        b1 = new JButton("₹ 100");
        b2 = new JButton("₹ 500");
        b3 = new JButton("₹ 1,000");
        b4 = new JButton("₹ 2,000");
        b5 = new JButton("₹ 5,000");
        b6 = new JButton("₹ 10,000");

        JButton[] amountButtons = {b1, b2, b3, b4, b5, b6};

        for (JButton button : amountButtons) {
            styleAmountButton(button);
            button.addActionListener(this);
        }

        JPanel amountPanel = new JPanel(new GridLayout(3, 2, 15, 15));
        amountPanel.setBackground(Color.WHITE);
        amountPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        for (JButton button : amountButtons) {
            amountPanel.add(button);
        }

        // Back button
        b7 = new JButton("BACK TO MENU");
        b7.setFont(new Font("SansSerif", Font.BOLD, 14));
        b7.setForeground(darkTeal);
        b7.setBackground(new Color(235, 242, 244));
        b7.setFocusPainted(false);
        b7.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b7.setBorder(BorderFactory.createLineBorder(
                new Color(210, 224, 228)
        ));
        b7.setAlignmentX(Component.LEFT_ALIGNMENT);
        b7.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
        b7.addActionListener(this);

        // Add components
        card.add(iconLabel);
        card.add(Box.createVerticalStrut(15));
        card.add(heading);
        card.add(Box.createVerticalStrut(6));
        card.add(subtitle);
        card.add(Box.createVerticalStrut(22));
        card.add(limitLabel);
        card.add(Box.createVerticalStrut(18));
        card.add(amountPanel);
        card.add(Box.createVerticalStrut(20));
        card.add(b7);

        mainPanel.add(card);

        setVisible(true);
    }

    private void styleAmountButton(JButton button) {
        button.setFont(new Font("SansSerif", Font.BOLD, 17));
        button.setForeground(Color.WHITE);
        button.setBackground(teal);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(
                15, 12, 15, 12
        ));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == b7) {
            dispose();
            new main_Class(pin);
            return;
        }

        JButton selectedButton = (JButton) e.getSource();

        try {
            // Read the preset amount from the selected button
            String rawAmount = selectedButton.getText()
                    .replace("₹", "")
                    .replace(",", "")
                    .trim();

            BigDecimal amount = new BigDecimal(rawAmount);

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Withdraw Rs. " + amount.setScale(2) + "?",
                    "Confirm Withdrawal",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            new LegacyBankService().transact(
                    pin, "WITHDRAWAL", amount
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Rs. " + amount.setScale(2)
                            + " withdrawn successfully.",
                    "Withdrawal Successful",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();
            new main_Class(pin);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage() == null
                            ? "Unable to complete the withdrawal."
                            : ex.getMessage(),
                    "Fast Cash Failed",
                    JOptionPane.ERROR_MESSAGE
            );
            // Stay on this screen so the user can retry.
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FastCash(""));
    }
}
