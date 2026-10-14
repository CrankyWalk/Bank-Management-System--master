
package bank.management.system;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login extends JFrame implements ActionListener {

    private JTextField cardField;
    private JPasswordField pinField;
    private JButton loginButton, clearButton, signupButton;
    private JCheckBox showPinCheckBox;

    private static final Color NAVY = new Color(12, 35, 68);
    private static final Color BLUE = new Color(32, 110, 235);
    private static final Color LIGHT_BG = new Color(246, 249, 253);
    private static final Color TEXT = new Color(36, 52, 75);
    private static final Color MUTED = new Color(112, 128, 148);
    private static final Color BORDER = new Color(220, 228, 238);

    public Login() {
        super("Bank Management System");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 560);
        setMinimumSize(new Dimension(760, 500));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Left branding panel
        JPanel branding = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(
                        RenderingHints.KEY_RENDERING,
                        RenderingHints.VALUE_RENDER_QUALITY
                );

                g2.setPaint(new GradientPaint(
                        0, 0, new Color(10, 37, 78),
                        getWidth(), getHeight(),
                        new Color(22, 104, 190)
                ));

                g2.fillRect(0, 0, getWidth(), getHeight());

                // Decorative circles
                g2.setColor(new Color(255, 255, 255, 18));
                g2.fillOval(-90, getHeight() - 190, 270, 270);
                g2.fillOval(getWidth() - 130, -70, 220, 220);

                g2.dispose();
            }
        };

        branding.setPreferredSize(new Dimension(330, 560));
        branding.setLayout(new BoxLayout(branding, BoxLayout.Y_AXIS));
        branding.setBorder(new EmptyBorder(65, 35, 40, 35));

        JLabel brandIcon = new JLabel("\u25C8");
        brandIcon.setFont(new Font("SansSerif", Font.BOLD, 46));
        brandIcon.setForeground(Color.WHITE);
        brandIcon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel brandTitle = new JLabel(
                "<html><div style='text-align:center'>BANK<br>MANAGEMENT<br>SYSTEM</div></html>"
        );
        brandTitle.setFont(new Font("SansSerif", Font.BOLD, 27));
        brandTitle.setForeground(Color.WHITE);
        brandTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel brandSubtitle = new JLabel(
                "<html><div style='text-align:center'>Simple. Secure. Reliable.</div></html>"
        );
        brandSubtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        brandSubtitle.setForeground(new Color(218, 231, 250));
        brandSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel brandFooter = new JLabel(
                "<html><div style='text-align:center'>Your money, managed with confidence.</div></html>"
        );
        brandFooter.setFont(new Font("SansSerif", Font.PLAIN, 12));
        brandFooter.setForeground(new Color(205, 223, 246));
        brandFooter.setAlignmentX(Component.CENTER_ALIGNMENT);

        branding.add(Box.createVerticalGlue());
        branding.add(brandIcon);
        branding.add(Box.createVerticalStrut(20));
        branding.add(brandTitle);
        branding.add(Box.createVerticalStrut(16));
        branding.add(brandSubtitle);
        branding.add(Box.createVerticalStrut(65));
        branding.add(brandFooter);
        branding.add(Box.createVerticalGlue());

        // Right login area
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(LIGHT_BG);
        rightPanel.setBorder(new EmptyBorder(30, 35, 30, 35));

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBackground(Color.WHITE);
        form.setBorder(new EmptyBorder(32, 34, 30, 34));
        form.setPreferredSize(new Dimension(430, 425));
        form.setMaximumSize(new Dimension(430, 425));

        JLabel welcome = new JLabel("Welcome back");
        welcome.setFont(new Font("SansSerif", Font.BOLD, 29));
        welcome.setForeground(TEXT);
        welcome.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel description = new JLabel("Sign in to access your bank account");
        description.setFont(new Font("SansSerif", Font.PLAIN, 13));
        description.setForeground(MUTED);
        description.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel cardLabel = createLabel("Card Number");
        cardField = new JTextField();
        styleTextField(cardField);
        cardField.setToolTipText("Enter your bank card number");

        JLabel pinLabel = createLabel("PIN");
        pinField = new JPasswordField();
        styleTextField(pinField);
        pinField.setToolTipText("Enter your PIN");

        showPinCheckBox = new JCheckBox("Show PIN");
        showPinCheckBox.setFont(new Font("SansSerif", Font.PLAIN, 12));
        showPinCheckBox.setForeground(MUTED);
        showPinCheckBox.setBackground(Color.WHITE);
        showPinCheckBox.setFocusPainted(false);
        showPinCheckBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        showPinCheckBox.addActionListener(e -> {
            pinField.setEchoChar(
                    showPinCheckBox.isSelected() ? (char) 0 : '\u2022'
            );
        });

        loginButton = new JButton("Sign In");
        styleButton(loginButton, BLUE, Color.WHITE);
        loginButton.addActionListener(this);

        clearButton = new JButton("Clear");
        styleButton(clearButton, new Color(239, 243, 249), TEXT);
        clearButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(10, 15, 10, 15)
        ));
        clearButton.addActionListener(this);

        signupButton = new JButton("Create an account");
        signupButton.setFont(new Font("SansSerif", Font.BOLD, 13));
        signupButton.setForeground(BLUE);
        signupButton.setBackground(Color.WHITE);
        signupButton.setBorderPainted(false);
        signupButton.setContentAreaFilled(false);
        signupButton.setFocusPainted(false);
        signupButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signupButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        signupButton.addActionListener(this);

        form.add(welcome);
        form.add(Box.createVerticalStrut(8));
        form.add(description);
        form.add(Box.createVerticalStrut(30));

        form.add(cardLabel);
        form.add(Box.createVerticalStrut(8));
        form.add(cardField);
        form.add(Box.createVerticalStrut(20));

        form.add(pinLabel);
        form.add(Box.createVerticalStrut(8));
        form.add(pinField);
        form.add(Box.createVerticalStrut(8));
        form.add(showPinCheckBox);
        form.add(Box.createVerticalStrut(22));

        JPanel buttons = new JPanel(new GridLayout(1, 2, 12, 0));
        buttons.setBackground(Color.WHITE);
        buttons.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttons.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        buttons.add(loginButton);
        buttons.add(clearButton);

        form.add(buttons);
        form.add(Box.createVerticalStrut(18));
        form.add(signupButton);

        rightPanel.add(form);
        add(branding, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);

        getRootPane().setDefaultButton(loginButton);

        setVisible(true);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 13));
        label.setForeground(TEXT);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private void styleTextField(JTextField field) {
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setForeground(TEXT);
        field.setBackground(Color.WHITE);
        field.setCaretColor(BLUE);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(10, 12, 10, 12)
        ));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private void styleButton(
            JButton button, Color background, Color foreground) {

        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setForeground(foreground);
        button.setBackground(background);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(12, 15, 12, 15));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == clearButton) {
            cardField.setText("");
            pinField.setText("");
            showPinCheckBox.setSelected(false);
            pinField.setEchoChar('\u2022');
            cardField.requestFocusInWindow();
            return;
        }

        if (e.getSource() == signupButton) {
            setVisible(false);
            new Signup();
            dispose();
            return;
        }

        if (e.getSource() == loginButton) {
            String cardno = cardField.getText().trim();
            String pin = new String(pinField.getPassword()).trim();

            if (cardno.isEmpty() || pin.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Please enter both your card number and PIN.",
                        "Missing Information",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            try (Connn c = new Connn()) {

                if (c.connection == null) {
                    throw new java.sql.SQLException(
                            "Database connection is unavailable."
                    );
                }

                String sql =
                        "SELECT cardno FROM login WHERE cardno = ? AND pin = ?";

                try (PreparedStatement ps =
                             c.connection.prepareStatement(sql)) {

                    ps.setString(1, cardno);
                    ps.setString(2, pin);

                    try (ResultSet rs = ps.executeQuery()) {

                        if (rs.next()) {
                            dispose();
                            new main_Class(pin);
                        } else {
                            JOptionPane.showMessageDialog(
                                    this,
                                    "The card number or PIN is incorrect.",
                                    "Login Failed",
                                    JOptionPane.ERROR_MESSAGE
                            );
                            pinField.setText("");
                            pinField.requestFocusInWindow();
                        }
                    }
                }

            } catch (Exception ex) {
                ex.printStackTrace();

                JOptionPane.showMessageDialog(
                        this,
                        "Unable to sign in. Please check your database "
                                + "connection and try again.",
                        "Connection Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Login::new);
    }
}
