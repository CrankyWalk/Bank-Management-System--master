        package bank.management.system;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;

import bank.management.system.service.LegacyBankService;

public class BalanceEnquriy extends JFrame implements ActionListener {

    private final String pin;
    private JLabel balanceLabel;
    private JButton b1;

    private final Color teal = new Color(18, 112, 120);
    private final Color darkTeal = new Color(12, 65, 75);
    private final Color lightBackground = new Color(242, 247, 249);
    private final Color textColor = new Color(35, 52, 63);

    public BalanceEnquriy(String pin) {
        this.pin = pin;

        setTitle("Bank Management System - Balance Enquiry");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        // Main background
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(lightBackground);
        setContentPane(mainPanel);

        // Balance card
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(35, 45, 35, 45));
        card.setPreferredSize(new Dimension(480, 440));

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

        JLabel heading = new JLabel("Balance Enquiry");
        heading.setFont(new Font("SansSerif", Font.BOLD, 27));
        heading.setForeground(darkTeal);
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Your account balance at a glance");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitle.setForeground(new Color(115, 130, 140));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel currentBalanceLabel = new JLabel("AVAILABLE BALANCE");
        currentBalanceLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        currentBalanceLabel.setForeground(new Color(115, 130, 140));
        currentBalanceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Balance display
        balanceLabel = new JLabel("Loading...");
        balanceLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
        balanceLabel.setForeground(teal);
        balanceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Decorative separator
        JPanel separator = new JPanel();
        separator.setBackground(new Color(220, 232, 235));
        separator.setMaximumSize(new Dimension(320, 1));
        separator.setPreferredSize(new Dimension(320, 1));
        separator.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Back button
        b1 = new JButton("BACK TO MENU");
        b1.setFont(new Font("SansSerif", Font.BOLD, 14));
        b1.setForeground(Color.WHITE);
        b1.setBackground(teal);
        b1.setFocusPainted(false);
        b1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b1.setBorder(BorderFactory.createEmptyBorder(
                14, 20, 14, 20
        ));
        b1.setAlignmentX(Component.LEFT_ALIGNMENT);
        b1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        b1.addActionListener(this);

        // Assemble card
        card.add(iconLabel);
        card.add(Box.createVerticalStrut(20));
        card.add(heading);
        card.add(Box.createVerticalStrut(7));
        card.add(subtitle);
        card.add(Box.createVerticalStrut(35));
        card.add(currentBalanceLabel);
        card.add(Box.createVerticalStrut(12));
        card.add(balanceLabel);
        card.add(Box.createVerticalStrut(20));
        card.add(separator);
        card.add(Box.createVerticalStrut(25));
        card.add(b1);

        mainPanel.add(card);

        // Fetch account balance
        loadBalance();

        setVisible(true);
    }

    private void loadBalance() {
        try {
            BigDecimal balance =
                    new LegacyBankService().getBalance(pin);

            balanceLabel.setText(
                    "Rs. " + balance
                            .setScale(2, RoundingMode.HALF_UP)
                            .toPlainString()
            );

        } catch (Exception e) {
            balanceLabel.setText("Unavailable");

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage() == null
                            ? "Unable to retrieve account balance."
                            : e.getMessage(),
                    "Balance Lookup Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            dispose();
            new main_Class(pin);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(
                () -> new BalanceEnquriy("")
        );
    }
}
