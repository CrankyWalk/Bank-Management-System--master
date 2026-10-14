
        package bank.management.system;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Signup3 extends JFrame implements ActionListener {

    private JRadioButton r1, r2, r3, r4;
    private JCheckBox c1, c2, c3, c4, c5, c6, declaration;
    private JButton s, c;

    private final String formno;

    private static final Color BACKGROUND = new Color(242, 246, 250);
    private static final Color TEAL = new Color(0, 137, 123);
    private static final Color DARK = new Color(35, 48, 65);
    private static final Color MUTED = new Color(110, 125, 140);
    private static final Color BORDER = new Color(220, 228, 236);

    private static final SecureRandom RANDOM = new SecureRandom();

    Signup3(String formno) {
        super("Banking System - Account Details");

        if (formno == null || formno.trim().isEmpty()) {
            JOptionPane.showMessageDialog(
                    null,
                    "Form number is missing. Please restart signup.",
                    "Signup Error",
                    JOptionPane.ERROR_MESSAGE
            );

            throw new IllegalArgumentException(
                    "Form number cannot be empty."
            );
        }

        this.formno = formno.trim();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(850, 800);
        setMinimumSize(new Dimension(700, 650));
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(0, 16));
        root.setBackground(BACKGROUND);
        root.setBorder(new EmptyBorder(22, 28, 22, 28));
        setContentPane(root);

        // Header
        JPanel header = new JPanel(new BorderLayout(15, 0));
        header.setOpaque(false);

        java.net.URL logoURL =
                ClassLoader.getSystemResource("icon/bank.png");

        if (logoURL != null) {
            ImageIcon original = new ImageIcon(logoURL);
            Image scaled = original.getImage().getScaledInstance(
                    68, 68, Image.SCALE_SMOOTH
            );

            header.add(new JLabel(new ImageIcon(scaled)),
                    BorderLayout.WEST);
        }

        JPanel headingText = new JPanel();
        headingText.setOpaque(false);
        headingText.setLayout(
                new BoxLayout(headingText, BoxLayout.Y_AXIS)
        );

        JLabel title = new JLabel("Create Your Account");
        title.setFont(new Font("SansSerif", Font.BOLD, 25));
        title.setForeground(DARK);

        JLabel subtitle = new JLabel(
                "Application No. " + this.formno + "   |   Step 3 of 3"
        );
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitle.setForeground(MUTED);

        headingText.add(Box.createVerticalStrut(5));
        headingText.add(title);
        headingText.add(Box.createVerticalStrut(8));
        headingText.add(subtitle);

        header.add(headingText, BorderLayout.CENTER);
        root.add(header, BorderLayout.NORTH);

        // Main card
        JPanel card = new JPanel(new BorderLayout(0, 18));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(22, 25, 22, 25)
        ));

        JPanel cardHeader = new JPanel();
        cardHeader.setOpaque(false);
        cardHeader.setLayout(
                new BoxLayout(cardHeader, BoxLayout.Y_AXIS)
        );

        JLabel sectionTitle = new JLabel("Account Preferences");
        sectionTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        sectionTitle.setForeground(DARK);

        JLabel sectionSubtitle = new JLabel(
                "Choose your account type and the services you need."
        );
        sectionSubtitle.setFont(new Font("SansSerif", Font.PLAIN, 12));
        sectionSubtitle.setForeground(MUTED);

        cardHeader.add(sectionTitle);
        cardHeader.add(Box.createVerticalStrut(6));
        cardHeader.add(sectionSubtitle);

        card.add(cardHeader, BorderLayout.NORTH);

        JPanel form = new JPanel();
        form.setBackground(Color.WHITE);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));

        // Account type
        addSectionTitle(form, "1. Select Account Type");

        JPanel accountGrid = new JPanel(new GridLayout(2, 2, 12, 10));
        accountGrid.setBackground(Color.WHITE);
        accountGrid.setMaximumSize(new Dimension(Integer.MAX_VALUE, 92));

        r1 = createRadioButton("Savings Account");
        r2 = createRadioButton("Fixed Deposit Account");
        r3 = createRadioButton("Current Account");
        r4 = createRadioButton("Recurring Deposit Account");

        ButtonGroup accountGroup = new ButtonGroup();
        accountGroup.add(r1);
        accountGroup.add(r2);
        accountGroup.add(r3);
        accountGroup.add(r4);

        accountGrid.add(r1);
        accountGrid.add(r2);
        accountGrid.add(r3);
        accountGrid.add(r4);

        form.add(accountGrid);
        form.add(Box.createVerticalStrut(20));

        // Card information
        addSectionTitle(form, "2. Card & PIN Information");

        JPanel infoPanel = new JPanel(new GridLayout(2, 1, 0, 8));
        infoPanel.setBackground(new Color(245, 249, 250));
        infoPanel.setBorder(new EmptyBorder(14, 16, 14, 16));
        infoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        JLabel cardInfo = new JLabel(
                "Card Number:  ••••  ••••  ••••  XXXX"
        );
        cardInfo.setFont(new Font("SansSerif", Font.BOLD, 14));
        cardInfo.setForeground(DARK);

        JLabel pinInfo = new JLabel(
                "ATM PIN:  ••••  (4-digit secret PIN)"
        );
        pinInfo.setFont(new Font("SansSerif", Font.BOLD, 14));
        pinInfo.setForeground(DARK);

        infoPanel.add(cardInfo);
        infoPanel.add(pinInfo);

        form.add(infoPanel);

        JLabel securityNote = new JLabel(
                "Your actual card number and PIN will be generated after submission."
        );
        securityNote.setFont(new Font("SansSerif", Font.PLAIN, 11));
        securityNote.setForeground(MUTED);
        securityNote.setBorder(new EmptyBorder(7, 0, 0, 0));
        securityNote.setAlignmentX(Component.LEFT_ALIGNMENT);

        form.add(securityNote);
        form.add(Box.createVerticalStrut(20));

        // Services
        addSectionTitle(form, "3. Services Required");

        JPanel servicesGrid = new JPanel(new GridLayout(3, 2, 12, 8));
        servicesGrid.setBackground(Color.WHITE);
        servicesGrid.setMaximumSize(new Dimension(Integer.MAX_VALUE, 105));

        c1 = createCheckBox("ATM Card");
        c2 = createCheckBox("Internet Banking");
        c3 = createCheckBox("Mobile Banking");
        c4 = createCheckBox("Email Alerts");
        c5 = createCheckBox("Cheque Book");
        c6 = createCheckBox("E-Statement");

        servicesGrid.add(c1);
        servicesGrid.add(c2);
        servicesGrid.add(c3);
        servicesGrid.add(c4);
        servicesGrid.add(c5);
        servicesGrid.add(c6);

        form.add(servicesGrid);
        form.add(Box.createVerticalStrut(18));

        // Declaration
        declaration = new JCheckBox(
                "I confirm that the information provided is correct."
        );
        declaration.setFont(new Font("SansSerif", Font.PLAIN, 12));
        declaration.setForeground(DARK);
        declaration.setBackground(Color.WHITE);
        declaration.setFocusPainted(false);
        declaration.setAlignmentX(Component.LEFT_ALIGNMENT);

        form.add(declaration);
        form.add(Box.createVerticalStrut(8));

        JScrollPane scrollPane = new JScrollPane(form);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        card.add(scrollPane, BorderLayout.CENTER);
        root.add(card, BorderLayout.CENTER);

        // Footer
        JPanel footer = new JPanel(new BorderLayout());
        footer.setOpaque(false);

        JLabel footerText = new JLabel(
                "Review your selections before submitting."
        );
        footerText.setFont(new Font("SansSerif", Font.PLAIN, 12));
        footerText.setForeground(MUTED);

        JPanel buttons = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 10, 0)
        );
        buttons.setOpaque(false);

        c = new JButton("Cancel");
        styleButton(c, new Color(230, 235, 240), DARK);
        c.setPreferredSize(new Dimension(105, 42));
        c.addActionListener(this);

        s = new JButton("Create Account");
        styleButton(s, TEAL, Color.WHITE);
        s.setPreferredSize(new Dimension(165, 42));
        s.addActionListener(this);

        buttons.add(c);
        buttons.add(s);

        footer.add(footerText, BorderLayout.WEST);
        footer.add(buttons, BorderLayout.EAST);

        root.add(footer, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void addSectionTitle(JPanel parent, String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        label.setForeground(TEAL);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        parent.add(label);
        parent.add(Box.createVerticalStrut(10));
    }

    private JRadioButton createRadioButton(String text) {
        JRadioButton radio = new JRadioButton(text);
        radio.setFont(new Font("SansSerif", Font.PLAIN, 13));
        radio.setForeground(DARK);
        radio.setBackground(Color.WHITE);
        radio.setFocusPainted(false);
        radio.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return radio;
    }

    private JCheckBox createCheckBox(String text) {
        JCheckBox checkBox = new JCheckBox(text);
        checkBox.setFont(new Font("SansSerif", Font.PLAIN, 13));
        checkBox.setForeground(DARK);
        checkBox.setBackground(Color.WHITE);
        checkBox.setFocusPainted(false);
        checkBox.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return checkBox;
    }

    private void styleButton(
            JButton button,
            Color background,
            Color foreground
    ) {
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setBackground(background);
        button.setForeground(foreground);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private String generateCardNumber() {
        // Generate a 16-digit card number.
        StringBuilder number = new StringBuilder("4");

        for (int i = 1; i < 16; i++) {
            number.append(RANDOM.nextInt(10));
        }

        return number.toString();
    }

    private String generatePin() {
        // Generate a 4-digit PIN, including possible leading zeros.
        return String.format(
                "%04d",
                RANDOM.nextInt(10000)
        );
    }

    private String getAccountType() {
        if (r1.isSelected()) {
            return "Saving Account";
        } else if (r2.isSelected()) {
            return "Fixed Deposit Account";
        } else if (r3.isSelected()) {
            return "Current Account";
        } else if (r4.isSelected()) {
            return "Recurring Deposit Account";
        }

        return null;
    }

    private String getSelectedServices() {
        StringBuilder services = new StringBuilder();

        if (c1.isSelected()) services.append("ATM CARD, ");
        if (c2.isSelected()) services.append("Internet Banking, ");
        if (c3.isSelected()) services.append("Mobile Banking, ");
        if (c4.isSelected()) services.append("EMAIL Alerts, ");
        if (c5.isSelected()) services.append("Cheque Book, ");
        if (c6.isSelected()) services.append("E-Statement, ");

        if (services.length() >= 2) {
            services.setLength(services.length() - 2);
        }

        return services.toString();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == c) {
            dispose();
            return;
        }

        if (e.getSource() != s) {
            return;
        }

        String accountType = getAccountType();

        if (accountType == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select an account type.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (!declaration.isSelected()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please confirm the declaration before continuing.",
                    "Confirmation Required",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String services = getSelectedServices();

        int confirmation = JOptionPane.showConfirmDialog(
                this,
                "Create a " + accountType + "?\n"
                        + "You can review your selections before proceeding.",
                "Confirm Account Creation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (confirmation != JOptionPane.YES_OPTION) {
            return;
        }

        // Generate credentials only after the user confirms.
        String cardno = generateCardNumber();
        String pin = generatePin();


        String signupSql =
                "INSERT INTO signupthree "
                        + "(formno, account_type, cardno, pin, services) "
                        + "VALUES (?, ?, ?, ?, ?)";

        String loginSql =
                "INSERT INTO login (formno, cardno, pin) "
                        + "VALUES (?, ?, ?)";

        s.setEnabled(false);
        c.setEnabled(false);

        try (Connn conn = new Connn()) {
            if (conn.connection == null) {
                throw new SQLException(
                        "Database connection unavailable."
                );
            }

            conn.connection.setAutoCommit(false);

            try {
                try (PreparedStatement ps =
                             conn.connection.prepareStatement(signupSql)) {

                    ps.setString(1, formno);
                    ps.setString(2, accountType);
                    ps.setString(3, cardno);
                    ps.setString(4, pin);
                    ps.setString(5, services);

                    if (ps.executeUpdate() != 1) {
                        throw new SQLException(
                                "Account details were not saved."
                        );
                    }
                }

                try (PreparedStatement ps =
                             conn.connection.prepareStatement(loginSql)) {

                    ps.setString(1, formno);
                    ps.setString(2, cardno);
                    ps.setString(3, pin);

                    if (ps.executeUpdate() != 1) {
                        throw new SQLException(
                                "Login details were not saved."
                        );
                    }
                }

                conn.connection.commit();

            } catch (Exception ex) {
                try {
                    conn.connection.rollback();
                } catch (SQLException rollbackError) {
                    ex.addSuppressed(rollbackError);
                }

                throw ex;
            } finally {
                try {
                    conn.connection.setAutoCommit(true);
                } catch (SQLException resetError) {
                    resetError.printStackTrace();
                }
            }

            // Show the generated credentials after both inserts succeed.
            JTextArea credentials = new JTextArea(
                    "Your account has been created successfully!\n\n"
                            + "Card Number: " + cardno + "\n"
                            + "ATM PIN: " + pin + "\n\n"
                            + "Please note these details and keep them private.\n"
                            + "Never share your PIN with anyone."
            );

            credentials.setFont(new Font("Monospaced", Font.PLAIN, 13));
            credentials.setEditable(false);
            credentials.setOpaque(false);
            credentials.setLineWrap(true);
            credentials.setWrapStyleWord(true);
            credentials.setCaretPosition(0);

            JOptionPane.showMessageDialog(
                    this,
                    credentials,
                    "Account Created",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

            // Preserve your original next-screen behavior.
            new Deposit(pin);

        } catch (Exception ex) {
            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to create your account:\n"
                            + ex.getMessage(),
                    "Account Creation Error",
                    JOptionPane.ERROR_MESSAGE
            );

        } finally {
            if (isDisplayable()) {
                s.setEnabled(true);
                c.setEnabled(true);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(
                () -> new Signup3("TEST1234")
        );
    }
}
