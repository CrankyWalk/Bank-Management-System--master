
        package bank.management.system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bank.management.system.service.LegacyBankService;

public class mini extends JFrame implements ActionListener {

    private final String pin;
    private JButton button;
    private JLabel cardNumberLabel;
    private JLabel balanceLabel;
    private DefaultTableModel tableModel;

    private static final Color BACKGROUND = new Color(242, 246, 250);
    private static final Color TEAL = new Color(0, 137, 123);
    private static final Color DARK = new Color(35, 48, 65);
    private static final Color MUTED = new Color(110, 125, 140);

    mini(String pin) {
        this.pin = pin;

        setTitle("Mini Statement");
        setSize(650, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(0, 18));
        mainPanel.setBackground(BACKGROUND);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(22, 25, 22, 25));
        setContentPane(mainPanel);

        // Header
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(BACKGROUND);

        JLabel title = new JLabel("MINI STATEMENT");
        title.setFont(new Font("SansSerif", Font.BOLD, 25));
        title.setForeground(DARK);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel("Your recent account transactions");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subtitle.setForeground(MUTED);
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        header.add(title);
        header.add(Box.createVerticalStrut(5));
        header.add(subtitle);

        mainPanel.add(header, BorderLayout.NORTH);

        // Center content
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(BACKGROUND);

        // Account details card
        JPanel accountCard = new JPanel();
        accountCard.setLayout(new BoxLayout(accountCard, BoxLayout.Y_AXIS));
        accountCard.setBackground(Color.WHITE);
        accountCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 232, 239)),
                BorderFactory.createEmptyBorder(17, 20, 17, 20)
        ));
        accountCard.setAlignmentX(Component.LEFT_ALIGNMENT);
        accountCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 105));

        JLabel accountHeading = new JLabel("ACCOUNT DETAILS");
        accountHeading.setFont(new Font("SansSerif", Font.BOLD, 12));
        accountHeading.setForeground(TEAL);

        cardNumberLabel = new JLabel("Card Number: Loading...");
        cardNumberLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        cardNumberLabel.setForeground(DARK);

        accountCard.add(accountHeading);
        accountCard.add(Box.createVerticalStrut(12));
        accountCard.add(cardNumberLabel);

        content.add(accountCard);
        content.add(Box.createVerticalStrut(15));

        // Balance card
        JPanel balanceCard = new JPanel(new BorderLayout());
        balanceCard.setBackground(TEAL);
        balanceCard.setBorder(BorderFactory.createEmptyBorder(17, 20, 17, 20));
        balanceCard.setAlignmentX(Component.LEFT_ALIGNMENT);
        balanceCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 95));

        JPanel balanceText = new JPanel();
        balanceText.setLayout(new BoxLayout(balanceText, BoxLayout.Y_AXIS));
        balanceText.setOpaque(false);

        JLabel balanceHeading = new JLabel("AVAILABLE BALANCE");
        balanceHeading.setFont(new Font("SansSerif", Font.BOLD, 12));
        balanceHeading.setForeground(new Color(220, 250, 245));

        balanceLabel = new JLabel("Rs. --");
        balanceLabel.setFont(new Font("SansSerif", Font.BOLD, 25));
        balanceLabel.setForeground(Color.WHITE);

        balanceText.add(balanceHeading);
        balanceText.add(Box.createVerticalStrut(7));
        balanceText.add(balanceLabel);

        balanceCard.add(balanceText, BorderLayout.CENTER);

        content.add(balanceCard);
        content.add(Box.createVerticalStrut(18));

        // Transactions heading
        JLabel transactionsHeading = new JLabel("TRANSACTION HISTORY");
        transactionsHeading.setFont(new Font("SansSerif", Font.BOLD, 13));
        transactionsHeading.setForeground(DARK);
        transactionsHeading.setAlignmentX(Component.LEFT_ALIGNMENT);

        content.add(transactionsHeading);
        content.add(Box.createVerticalStrut(10));

        // Transactions table
        String[] columns = {"Date", "Transaction Type", "Amount (Rs.)"};

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(tableModel);
        table.setFont(new Font("SansSerif", Font.PLAIN, 13));
        table.setForeground(DARK);
        table.setBackground(Color.WHITE);
        table.setRowHeight(32);
        table.setShowVerticalLines(false);
        table.setGridColor(new Color(232, 237, 242));
        table.setSelectionBackground(new Color(220, 243, 239));
        table.setSelectionForeground(DARK);
        table.setFillsViewportHeight(true);

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(new Font("SansSerif", Font.BOLD, 12));
        tableHeader.setBackground(new Color(230, 241, 240));
        tableHeader.setForeground(DARK);
        tableHeader.setPreferredSize(new Dimension(100, 36));
        tableHeader.setReorderingAllowed(false);

        table.getColumnModel().getColumn(0).setPreferredWidth(145);
        table.getColumnModel().getColumn(1).setPreferredWidth(180);
        table.getColumnModel().getColumn(2).setPreferredWidth(125);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(
                new Color(225, 232, 239)
        ));
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollPane.setPreferredSize(new Dimension(580, 250));
        scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 250));

        content.add(scrollPane);

        mainPanel.add(content, BorderLayout.CENTER);

        // Footer
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        footer.setBackground(BACKGROUND);

        button = new JButton("Exit");
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(TEAL);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(115, 40));
        button.addActionListener(this);

        footer.add(button);
        mainPanel.add(footer, BorderLayout.SOUTH);

        loadCardNumber();
        loadTransactions();
        loadBalance();

        setVisible(true);
    }

    private void loadCardNumber() {
        String sql = "SELECT cardno FROM login WHERE pin = ?";

        try (Connn c = new Connn()) {
            if (c.connection == null) {
                throw new java.sql.SQLException("Database connection unavailable");
            }

            try (PreparedStatement ps = c.connection.prepareStatement(sql)) {
                ps.setString(1, pin);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String card = rs.getString("cardno");

                        if (card != null && card.length() >= 16) {
                            cardNumberLabel.setText(
                                    "Card Number:  "
                                            + card.substring(0, 4)
                                            + "XXXXXXXX"
                                            + card.substring(12)
                            );
                        } else {
                            cardNumberLabel.setText(
                                    "Card Number:  "
                                            + (card == null ? "Unavailable" : card)
                            );
                        }
                    } else {
                        cardNumberLabel.setText("Card Number: Not found");
                    }
                }
            }
        } catch (Exception e) {
            cardNumberLabel.setText("Card Number: Unavailable");
            e.printStackTrace();
        }
    }

    private void loadTransactions() {
        String sql =
                "SELECT date, type, amount FROM bank WHERE pin = ? ORDER BY date DESC";

        try (Connn c = new Connn()) {
            if (c.connection == null) {
                throw new java.sql.SQLException("Database connection unavailable");
            }

            try (PreparedStatement ps = c.connection.prepareStatement(sql)) {
                ps.setString(1, pin);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Object date = rs.getObject("date");
                        String type = rs.getString("type");
                        BigDecimal amount = rs.getBigDecimal("amount");

                        tableModel.addRow(new Object[]{
                                date == null ? "" : date.toString(),
                                type == null ? "" : type,
                                amount == null
                                        ? "0.00"
                                        : amount.setScale(2, RoundingMode.HALF_UP)
                                        .toPlainString()
                        });
                    }
                }
            }

            if (tableModel.getRowCount() == 0) {
                tableModel.addRow(new Object[]{
                        "—", "No transactions yet", "—"
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Could not load transaction history.\n" + e.getMessage(),
                    "Mini Statement",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void loadBalance() {
        try {
            BigDecimal balance = new LegacyBankService().getBalance(pin);

            balanceLabel.setText(
                    "Rs. " + balance.setScale(2, RoundingMode.HALF_UP).toPlainString()
            );

        } catch (Exception e) {
            balanceLabel.setText("Unavailable");

            JOptionPane.showMessageDialog(
                    this,
                    "Could not load your balance.\n" + e.getMessage(),
                    "Balance Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new mini(""));
    }
}
