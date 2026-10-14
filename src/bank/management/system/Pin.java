        package bank.management.system;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Pin extends JFrame implements ActionListener {

    private final String pin;

    private JButton b1, b2;
    private JPasswordField p1, p2;

    private final Color teal = new Color(18, 112, 120);
    private final Color darkTeal = new Color(12, 65, 75);
    private final Color lightBackground = new Color(242, 247, 249);
    private final Color textColor = new Color(35, 52, 63);

    public Pin(String pin) {
        this.pin = pin;

        setTitle("Bank Management System - Change PIN");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        // Main background
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(lightBackground);
        setContentPane(mainPanel);

        // Main card
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(32, 42, 32, 42));
        card.setPreferredSize(new Dimension(470, 550));

        // Header icon
        JLabel iconLabel = new JLabel("🔒");
        iconLabel.setFont(new Font("SansSerif", Font.PLAIN, 35));
        iconLabel.setForeground(Color.WHITE);
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabel.setOpaque(true);
        iconLabel.setBackground(teal);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        iconLabel.setPreferredSize(new Dimension(70, 70));
        iconLabel.setMaximumSize(new Dimension(70, 70));

        JLabel heading = new JLabel("Change Your PIN");
        heading.setFont(new Font("SansSerif", Font.BOLD, 26));
        heading.setForeground(darkTeal);
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Keep your account secure");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitle.setForeground(new Color(115, 130, 140));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // PIN fields
        JLabel newPinLabel = new JLabel("New 4-digit PIN");
        styleLabel(newPinLabel);

        p1 = createPasswordField();

        JLabel confirmPinLabel = new JLabel("Confirm new PIN");
        styleLabel(confirmPinLabel);

        p2 = createPasswordField();

        // Buttons
        b1 = new JButton("CHANGE PIN");
        styleButton(b1, teal, Color.WHITE);
        b1.addActionListener(this);

        b2 = new JButton("BACK TO MENU");
        styleButton(b2, new Color(235, 242, 244), darkTeal);
        b2.setBorder(BorderFactory.createLineBorder(
                new Color(210, 224, 228)
        ));
        b2.addActionListener(this);

        // Assemble card
        card.add(iconLabel);
        card.add(Box.createVerticalStrut(15));
        card.add(heading);
        card.add(Box.createVerticalStrut(6));
        card.add(subtitle);
        card.add(Box.createVerticalStrut(30));

        card.add(newPinLabel);
        card.add(Box.createVerticalStrut(8));
        card.add(p1);
        card.add(Box.createVerticalStrut(18));

        card.add(confirmPinLabel);
        card.add(Box.createVerticalStrut(8));
        card.add(p2);
        card.add(Box.createVerticalStrut(25));

        card.add(b1);
        card.add(Box.createVerticalStrut(12));
        card.add(b2);

        mainPanel.add(card);

        getRootPane().setDefaultButton(b1);
        setVisible(true);
        p1.requestFocusInWindow();
    }

    private void styleLabel(JLabel label) {
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        label.setForeground(textColor);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private JPasswordField createPasswordField() {
        JPasswordField field = new JPasswordField();

        field.setFont(new Font("SansSerif", Font.PLAIN, 20));
        field.setForeground(textColor);
        field.setBackground(new Color(249, 251, 252));
        field.setCaretColor(teal);
        field.setEchoChar('\u2022');
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(205, 218, 223), 1
                ),
                new EmptyBorder(10, 12, 10, 12)
        ));

        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);

        return field;
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
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
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

        char[] first = p1.getPassword();
        char[] second = p2.getPassword();

        String newPin = new String(first);
        String confirmPin = new String(second);

        try {
            if (newPin.isEmpty() || confirmPin.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Please fill in both PIN fields.",
                        "Missing PIN",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            if (!newPin.matches("\\d{4}")) {
                JOptionPane.showMessageDialog(
                        this,
                        "Your PIN must contain exactly 4 digits.",
                        "Invalid PIN",
                        JOptionPane.WARNING_MESSAGE
                );
                p1.requestFocusInWindow();
                return;
            }

            if (!newPin.equals(confirmPin)) {
                JOptionPane.showMessageDialog(
                        this,
                        "The PIN entries do not match.",
                        "PIN Mismatch",
                        JOptionPane.WARNING_MESSAGE
                );
                p2.requestFocusInWindow();
                return;
            }

            if (pin == null || pin.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "No current PIN was supplied. Please open this "
                                + "screen from the ATM menu after login.",
                        "Session Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            updatePin(newPin);

        } finally {
            java.util.Arrays.fill(first, '\0');
            java.util.Arrays.fill(second, '\0');
        }
    }

    private void updatePin(String newPin) {
        Connection connection = null;

        try {
            Connn db = new Connn();
            connection = db.connection;

            if (connection == null || connection.isClosed()) {
                throw new Exception(
                        "Database connection is unavailable."
                );
            }

            connection.setAutoCommit(false);

            int loginRows;
            int signupRows;

            try (PreparedStatement ps = connection.prepareStatement(
                    "UPDATE login SET pin = ? WHERE pin = ?")) {
                ps.setString(1, newPin);
                ps.setString(2, pin);
                loginRows = ps.executeUpdate();
            }

            try (PreparedStatement ps = connection.prepareStatement(
                    "UPDATE signupthree SET pin = ? WHERE pin = ?")) {
                ps.setString(1, newPin);
                ps.setString(2, pin);
                signupRows = ps.executeUpdate();
            }

            if (loginRows == 0 || signupRows == 0) {
                connection.rollback();

                JOptionPane.showMessageDialog(
                        this,
                        "The PIN could not be updated in all required "
                                + "records. Please verify the database.",
                        "PIN Update Failed",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            try (PreparedStatement ps = connection.prepareStatement(
                    "UPDATE bank SET pin = ? WHERE pin = ?")) {
                ps.setString(1, newPin);
                ps.setString(2, pin);
                ps.executeUpdate();
            }

            connection.commit();

            JOptionPane.showMessageDialog(
                    this,
                    "Your PIN has been changed successfully.",
                    "PIN Changed",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();
            new main_Class(newPin);

        } catch (Exception ex) {

            if (connection != null) {
                try {
                    connection.rollback();
                } catch (Exception rollbackError) {
                    ex.addSuppressed(rollbackError);
                }
            }

            JOptionPane.showMessageDialog(
                    this,
                    "PIN change failed: " + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Pin(""));
    }
}
