        package bank.management.system;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Random;

public class Signup extends JFrame implements ActionListener {

    private JRadioButton r1, r2, m1, m2, m3;
    private JButton next;

    private JTextField textName, textFname, textEmail;
    private JTextField textAdd, textcity, textState, textPin;

    private JDateChooser dateChooser;

    private final String first =
            String.valueOf(1000 + new Random().nextInt(9000));

    private static final Color BACKGROUND = new Color(242, 246, 250);
    private static final Color TEAL = new Color(0, 137, 123);
    private static final Color DARK = new Color(35, 48, 65);
    private static final Color MUTED = new Color(110, 125, 140);
    private static final Color BORDER = new Color(220, 228, 236);

    public Signup() {
        super("Banking System - Application Form");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 820);
        setMinimumSize(new Dimension(700, 650));
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(0, 16));
        root.setBackground(BACKGROUND);
        root.setBorder(new EmptyBorder(22, 28, 22, 28));
        setContentPane(root);

        // Header
        JPanel header = new JPanel(new BorderLayout(15, 0));
        header.setOpaque(false);

        java.net.URL imageURL =
                ClassLoader.getSystemResource("icon/bank.png");

        if (imageURL != null) {
            ImageIcon original = new ImageIcon(imageURL);
            Image scaled = original.getImage().getScaledInstance(
                    68, 68, Image.SCALE_SMOOTH
            );
            JLabel logo = new JLabel(new ImageIcon(scaled));
            header.add(logo, BorderLayout.WEST);
        }

        JPanel headingText = new JPanel();
        headingText.setOpaque(false);
        headingText.setLayout(
                new BoxLayout(headingText, BoxLayout.Y_AXIS)
        );

        JLabel title = new JLabel("Open Your Bank Account");
        title.setFont(new Font("SansSerif", Font.BOLD, 25));
        title.setForeground(DARK);

        JLabel subtitle = new JLabel(
                "Application No. " + first + "   |   Step 1 of 3"
        );
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitle.setForeground(MUTED);

        headingText.add(Box.createVerticalStrut(5));
        headingText.add(title);
        headingText.add(Box.createVerticalStrut(8));
        headingText.add(subtitle);

        header.add(headingText, BorderLayout.CENTER);
        root.add(header, BorderLayout.NORTH);

        // Main form card
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

        JLabel sectionTitle = new JLabel("Personal Details");
        sectionTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        sectionTitle.setForeground(DARK);

        JLabel sectionSubtitle = new JLabel(
                "Enter your details as they appear on your official documents."
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
        gbc.insets = new Insets(7, 4, 7, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 0;

        int row = 0;

        textName = createTextField();
        addFormRow(form, gbc, row++, "Full Name", textName);

        textFname = createTextField();
        addFormRow(form, gbc, row++, "Father's Name", textFname);

        r1 = createRadioButton("Male");
        r2 = createRadioButton("Female");

        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(r1);
        genderGroup.add(r2);

        JPanel genderPanel = createOptionPanel();
        genderPanel.add(r1);
        genderPanel.add(Box.createHorizontalStrut(22));
        genderPanel.add(r2);

        addFormRow(form, gbc, row++, "Gender", genderPanel);

        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd-MM-yyyy");
        dateChooser.setFont(new Font("SansSerif", Font.PLAIN, 14));
        dateChooser.setPreferredSize(new Dimension(300, 38));
        dateChooser.setDate(new Date());

        addFormRow(form, gbc, row++, "Date of Birth", dateChooser);

        textEmail = createTextField();
        addFormRow(form, gbc, row++, "Email Address", textEmail);

        m1 = createRadioButton("Married");
        m2 = createRadioButton("Unmarried");
        m3 = createRadioButton("Other");

        ButtonGroup maritalGroup = new ButtonGroup();
        maritalGroup.add(m1);
        maritalGroup.add(m2);
        maritalGroup.add(m3);

        JPanel maritalPanel = createOptionPanel();
        maritalPanel.add(m1);
        maritalPanel.add(Box.createHorizontalStrut(12));
        maritalPanel.add(m2);
        maritalPanel.add(Box.createHorizontalStrut(12));
        maritalPanel.add(m3);

        addFormRow(form, gbc, row++, "Marital Status", maritalPanel);

        textAdd = createTextField();
        addFormRow(form, gbc, row++, "Address", textAdd);

        textcity = createTextField();
        addFormRow(form, gbc, row++, "City", textcity);

        textPin = createTextField();
        addFormRow(form, gbc, row++, "PIN Code", textPin);

        textState = createTextField();
        addFormRow(form, gbc, row++, "State", textState);

        // Allow scrolling on smaller screens
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

        JLabel footerText = new JLabel("All fields are required.");
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
        gbc.fill = GridBagConstraints.HORIZONTAL;

        if (input instanceof JPanel) {
            gbc.fill = GridBagConstraints.HORIZONTAL;
        }

        form.add(input, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() != next) {
            return;
        }

        String name = textName.getText().trim();
        String fname = textFname.getText().trim();
        String email = textEmail.getText().trim();
        String address = textAdd.getText().trim();
        String city = textcity.getText().trim();
        String pincode = textPin.getText().trim();
        String state = textState.getText().trim();

        String gender = r1.isSelected() ? "Male"
                : r2.isSelected() ? "Female" : null;

        String marital = m1.isSelected() ? "Married"
                : m2.isSelected() ? "Unmarried"
                : m3.isSelected() ? "Other" : null;

        Date selectedDate = dateChooser.getDate();

        if (name.isEmpty() || fname.isEmpty()
                || email.isEmpty() || address.isEmpty()
                || city.isEmpty() || pincode.isEmpty()
                || state.isEmpty() || gender == null
                || marital == null || selectedDate == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please complete all fields and select your gender and marital status.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (!email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid email address.",
                    "Invalid Email",
                    JOptionPane.WARNING_MESSAGE
            );
            textEmail.requestFocusInWindow();
            return;
        }

        if (!pincode.matches("[0-9]{6}")) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid 6-digit PIN code.",
                    "Invalid PIN Code",
                    JOptionPane.WARNING_MESSAGE
            );
            textPin.requestFocusInWindow();
            return;
        }

        if (selectedDate.after(new Date())) {
            JOptionPane.showMessageDialog(
                    this,
                    "Date of birth cannot be in the future.",
                    "Invalid Date",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String dob = new SimpleDateFormat("dd-MM-yyyy")
                .format(selectedDate);

        String sql = "INSERT INTO signup "
                + "(formno, name, fname, dob, gender, email, marital, "
                + "address, city, pincode, state) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        next.setEnabled(false);

        try (Connn c = new Connn()) {
            if (c.connection == null) {
                throw new java.sql.SQLException(
                        "Database connection unavailable"
                );
            }

            try (PreparedStatement ps =
                         c.connection.prepareStatement(sql)) {

                ps.setString(1, first);
                ps.setString(2, name);
                ps.setString(3, fname);
                ps.setString(4, dob);
                ps.setString(5, gender);
                ps.setString(6, email);
                ps.setString(7, marital);
                ps.setString(8, address);
                ps.setString(9, city);
                ps.setString(10, pincode);
                ps.setString(11, state);

                ps.executeUpdate();
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Personal details saved successfully.",
                    "Registration",
                    JOptionPane.INFORMATION_MESSAGE
            );

            new Signup2(first);
            dispose();

        } catch (Exception ex) {
            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to save personal details:\n"
                            + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        } finally {
            next.setEnabled(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Signup::new);
    }
}
