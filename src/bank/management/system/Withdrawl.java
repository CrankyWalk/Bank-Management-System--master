        package bank.management.system;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import bank.management.system.service.LegacyBankService;

public class Withdrawl extends JFrame implements ActionListener {

    private final String pin;
    private JTextField textField;
    private JButton b1, b2;

    private final Color teal = new Color(18, 112, 120);
    private final Color darkTeal = new Color(12, 65, 75);
    private final Color lightBackground = new Color(242, 247, 249);
    private final Color textColor = new Color(35, 52, 63);

    public Withdrawl(String pin) {
        this.pin = pin;

        setTitle("Bank Management System - Withdraw");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        // Main background
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(lightBackground);
        setContentPane(mainPanel);

        // Withdrawal card
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(35, 45, 35, 45));
        card.setPreferredSize(new Dimension(460, 480));

        // Header icon
        JLabel iconLabel = new JLabel("₹");
        iconLabel.setFont(new Font("SansSerif", Font.BOLD, 38));
        iconLabel.setForeground(Color.WHITE);
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabel.setOpaque(true);
        iconLabel.setBackground(teal);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        iconLabel.setPreferredSize(new Dimension(75, 75));
        iconLabel.setMaximumSize(new Dimension(75, 75));

        JLabel heading = new JLabel("Withdraw Money");
        heading.setFont(new Font("SansSerif", Font.BOLD, 27));
        heading.setForeground(darkTeal);
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Withdraw funds from your account");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitle.setForeground(new Color(115, 130, 140));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Withdrawal limit
        JLabel limitLabel = new JLabel("Maximum withdrawal: Rs. 10,000");
        limitLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        limitLabel.setForeground(teal);
        limitLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel amountLabel = new JLabel("Enter withdrawal amount (INR)");
        amountLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        amountLabel.setForeground(textColor);
        amountLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Amount input
        textField = new JTextField();
        textField.setFont(new Font("SansSerif", Font.PLAIN, 21));
        textField.setForeground(textColor);
        textField.setCaretColor(teal);
        textField.setBackground(new Color(249, 251, 252));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(205, 218, 223), 1
                ),
                new EmptyBorder(12, 14, 12, 14)
        ));
        textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 52));
        textField.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Buttons
        b1 = new JButton("WITHDRAW NOW");
        styleButton(b1, teal, Color.WHITE);
        b1.addActionListener(this);

        b2 = new JButton("BACK TO MENU");
        styleButton(b2, new Color(235, 242, 244), darkTeal);
        b2.setBorder(BorderFactory.createLineBorder(
                new Color(210, 224, 228)
        ));
        b2.addActionListener(this);

        // Add components to card
        card.add(iconLabel);
        card.add(Box.createVerticalStrut(18));
        card.add(heading);
        card.add(Box.createVerticalStrut(7));
        card.add(subtitle);
        card.add(Box.createVerticalStrut(25));
        card.add(limitLabel);
        card.add(Box.createVerticalStrut(15));
        card.add(amountLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(textField);
        card.add(Box.createVerticalStrut(23));
        card.add(b1);
        card.add(Box.createVerticalStrut(12));
        card.add(b2);

        mainPanel.add(card);

        getRootPane().setDefaultButton(b1);
        setVisible(true);
        textField.requestFocusInWindow();
    }

    private void styleButton(
            JButton button, Color background, Color foreground) {

        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBackground(background);
        button.setForeground(foreground);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(
                13, 15, 13, 15
        ));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == b2) {
            dispose();
            new main_Class(pin);
            return;
        }

        if (e.getSource() != b1) {
            return;
        }

        try {
            String rawAmount = textField.getText().trim();

            if (rawAmount.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Please enter the amount you want to withdraw.",
                        "Amount Required",
                        JOptionPane.WARNING_MESSAGE
                );
                textField.requestFocusInWindow();
                return;
            }

            BigDecimal amount = new BigDecimal(rawAmount);

            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "Amount must be greater than zero.",
                        "Invalid Amount",
                        JOptionPane.WARNING_MESSAGE
                );
                textField.requestFocusInWindow();
                return;
            }

            if (amount.compareTo(new BigDecimal("10000.00")) > 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "Maximum withdrawal is Rs. 10,000.",
                        "Withdrawal Limit",
                        JOptionPane.WARNING_MESSAGE
                );
                textField.requestFocusInWindow();
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

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Enter a valid numeric amount.",
                    "Invalid Amount",
                    JOptionPane.ERROR_MESSAGE
            );
            textField.requestFocusInWindow();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage() == null
                            ? "Unable to complete withdrawal."
                            : ex.getMessage(),
                    "Withdrawal Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Withdrawl(""));
    }
}
