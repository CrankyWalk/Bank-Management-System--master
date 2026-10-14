
        package bank.management.system;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Signup2 extends JFrame implements ActionListener {

    private JComboBox<String> comboBox;
    private JComboBox<String> comboBox2;
    private JComboBox<String> comboBox3;
    private JComboBox<String> comboBox4;
    private JComboBox<String> comboBox5;

    private JTextField textPan;
    private JTextField textAadhar;

    private JRadioButton r1, r2, e1, e2;
    private JButton next;

    private final String formno;

    private static final Color BACKGROUND = new Color(242, 246, 250);
    private static final Color TEAL = new Color(0, 137, 123);
    private static final Color DARK = new Color(35, 48, 65);
    private static final Color MUTED = new Color(110, 125, 140);
    private static final Color BORDER = new Color(220, 228, 236);

    public Signup2(String formno) {
        super("Banking System - Additional Details");

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
        setSize(850, 790);
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

        JLabel title = new JLabel("Additional Details");
        title.setFont(new Font("SansSerif", Font.BOLD, 25));
        title.setForeground(DARK);

        JLabel subtitle = new JLabel(
                "Application No. " + this.formno + "   |   Step 2 of 3"
        );
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitle.setForeground(MUTED);

        headingText.add(Box.createVerticalStrut(5));
        headingText.add(title);
        headingText.add(Box.createVerticalStrut(8));
        headingText.add(subtitle);

        header.add(headingText, BorderLayout.CENTER);
        root.add(header, BorderLayout.NORTH);

        // Form card
        JPanel card = new JPanel(new BorderLayout(0, 18));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(22, 25, 22, 25)
        ));

        JPanel sectionHeader = new JPanel();
        sectionHeader.setOpaque(false);
        sectionHeader.setLayout(
                new BoxLayout(sectionHeader, BoxLayout.Y_AXIS)
        );

        JLabel sectionTitle = new JLabel("Your Profile");
        sectionTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        sectionTitle.setForeground(DARK);

        JLabel sectionSubtitle = new JLabel(
                "Provide the information below to continue your application."
        );
        sectionSubtitle.setFont(new Font("SansSerif", Font.PLAIN, 12));
        sectionSubtitle.setForeground(MUTED);

        sectionHeader.add(sectionTitle);
        sectionHeader.add(Box.createVerticalStrut(6));
        sectionHeader.add(sectionSubtitle);

        card.add(sectionHeader, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 4, 8, 4);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        comboBox = createComboBox(new String[]{
                "Hindu", "Muslim", "Sikh", "Christian", "Other"
        });
        addFormRow(form, gbc, row++, "Religion", comboBox);

        comboBox2 = createComboBox(new String[]{
                "General", "OBC", "SC", "ST", "Other"
        });
        addFormRow(form, gbc, row++, "Category", comboBox2);

        comboBox3 = createComboBox(new String[]{
                "Not specified",
                "Below 1,50,000",
                "Below 2,50,000",
                "2,50,000 - 5,00,000",
                "5,00,000 - 10,00,000",
                "Above 10,00,000"
        });
        addFormRow(form, gbc, row++, "Income", comboBox3);

        comboBox4 = createComboBox(new String[]{
                "Non-Graduate",
                "Graduate",
                "Post-Graduate",
                "Doctorate",
                "Others"
        });
        addFormRow(form, gbc, row++, "Education", comboBox4);

        comboBox5 = createComboBox(new String[]{
                "Salaried",
                "Self-Employed",
                "Business",
                "Student",
                "Retired",
                "Other"
        });
        addFormRow(form, gbc, row++, "Occupation", comboBox5);

        textPan = createTextField();
        textPan.setToolTipText("Format: ABCDE1234F");
        addFormRow(form, gbc, row++, "PAN Number", textPan);

        textAadhar = createTextField();
        textAadhar.setToolTipText("Enter your 12-digit Aadhaar number");
        addFormRow(form, gbc, row++, "Aadhaar Number", textAadhar);

        // Senior citizen
        r1 = createRadioButton("Yes");
        r2 = createRadioButton("No");

        ButtonGroup seniorGroup = new ButtonGroup();
        seniorGroup.add(r1);
        seniorGroup.add(r2);

        JPanel seniorPanel = createOptionPanel();
        seniorPanel.add(r1);
        seniorPanel.add(Box.createHorizontalStrut(25));
        seniorPanel.add(r2);

        addFormRow(form, gbc, row++, "Senior Citizen", seniorPanel);

        // Existing account
        e1 = createRadioButton("Yes");
        e2 = createRadioButton("No");

        ButtonGroup existingGroup = new ButtonGroup();
        existingGroup.add(e1);
        existingGroup.add(e2);

        JPanel existingPanel = createOptionPanel();
        existingPanel.add(e1);
        existingPanel.add(Box.createHorizontalStrut(25));
        existingPanel.add(e2);

        addFormRow(form, gbc, row++, "Existing Account", existingPanel);

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.weighty = 1;
        form.add(Box.createVerticalGlue(), gbc);

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
                "Complete all fields before continuing."
        );
        footerText.setFont(new Font("SansSerif", Font.PLAIN, 12));
        footerText.setForeground(MUTED);

        next = new JButton("Continue  →");
        next.setFont(new Font("SansSerif", Font.BOLD, 14));
        next.setForeground(Color.WHITE);
        next.setBackground(TEAL);
        next.setFocusPainted(false);
        next.setBorderPainted(false);
        next.setCursor(new Cursor(Cursor.HAND_CURSOR));
        next.setPreferredSize(new Dimension(160, 44));
        next.addActionListener(this);

        footer.add(footerText, BorderLayout.WEST);
        footer.add(next, BorderLayout.EAST);

        root.add(footer, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JComboBox<String> createComboBox(String[] items) {
        JComboBox<String> box = new JComboBox<>(items);
        box.setFont(new Font("SansSerif", Font.PLAIN, 14));
        box.setForeground(DARK);
        box.setBackground(Color.WHITE);
        box.setPreferredSize(new Dimension(300, 38));
        box.setFocusable(false);
        return box;
    }

    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setForeground(DARK);
        field.setBackground(Color.WHITE);
        field.setCaretColor(TEAL);
        field.setPreferredSize(new Dimension(300, 38));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(5, 10, 5, 10)
        ));
        return field;
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

    private JPanel createOptionPanel() {
        JPanel panel = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 0, 0)
        );
        panel.setBackground(Color.WHITE);
        return panel;
    }

    private void addFormRow(
            JPanel form,
            GridBagConstraints gbc,
            int row,
            String labelText,
            Component input
    ) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("SansSerif", Font.BOLD, 13));
        label.setForeground(DARK);

        gbc.gridy = row;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        form.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        form.add(input, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() != next) {
            return;
        }

        String pan = textPan.getText().trim().toUpperCase();
        String aadhar = textAadhar.getText().trim();

        if (!pan.matches("[A-Z]{5}[0-9]{4}[A-Z]")) {
            JOptionPane.showMessageDialog(
                    this,
                    "Enter PAN in the format ABCDE1234F.",
                    "Invalid PAN",
                    JOptionPane.WARNING_MESSAGE
            );
            textPan.requestFocusInWindow();
            return;
        }

        if (!aadhar.matches("[0-9]{12}")) {
            JOptionPane.showMessageDialog(
                    this,
                    "Aadhaar must contain exactly 12 digits.",
                    "Invalid Aadhaar",
                    JOptionPane.WARNING_MESSAGE
            );
            textAadhar.requestFocusInWindow();
            return;
        }

        if (!r1.isSelected() && !r2.isSelected()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select Senior Citizen status.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (!e1.isSelected() && !e2.isSelected()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select Existing Account status.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String religion = (String) comboBox.getSelectedItem();
        String category = (String) comboBox2.getSelectedItem();
        String income = (String) comboBox3.getSelectedItem();
        String education = (String) comboBox4.getSelectedItem();
        String occupation = (String) comboBox5.getSelectedItem();

        String seniorCitizen = r1.isSelected() ? "Yes" : "No";
        String existingAccount = e1.isSelected() ? "Yes" : "No";

        String sql = "INSERT INTO signuptwo "
                + "(formno, religion, category, income, education, "
                + "occupation, pan, aadhar, seniorcitizen, existingaccount) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        next.setEnabled(false);

        try (Connn c = new Connn()) {
            if (c.connection == null) {
                throw new SQLException("Database connection unavailable");
            }

            try (PreparedStatement ps =
                         c.connection.prepareStatement(sql)) {

                ps.setString(1, formno);
                ps.setString(2, religion);
                ps.setString(3, category);
                ps.setString(4, income);
                ps.setString(5, education);
                ps.setString(6, occupation);
                ps.setString(7, pan);
                ps.setString(8, aadhar);
                ps.setString(9, seniorCitizen);
                ps.setString(10, existingAccount);

                int rows = ps.executeUpdate();

                if (rows != 1) {
                    throw new SQLException(
                            "Additional details were not saved."
                    );
                }
            }

            new Signup3(formno);
            dispose();

        } catch (Exception ex) {
            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to continue to Page 3:\n"
                            + ex.getMessage(),
                    "Signup Error",
                    JOptionPane.ERROR_MESSAGE
            );
        } finally {
            next.setEnabled(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(
                () -> new Signup2("TEST1234")
        );
    }
}
