
package bank.management.system;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class main_Class extends JFrame implements ActionListener {

    JButton b1, b2, b3, b4, b5, b6, b7;
    String pin;

    private final Color NAVY = new Color(12, 35, 68);
    private final Color BLUE = new Color(32, 110, 235);
    private final Color BG = new Color(246, 249, 253);
    private final Color TEXT = new Color(36, 52, 75);
    private final Color MUTED = new Color(112, 128, 148);
    private final Color BORDER = new Color(226, 232, 240);

    public main_Class(String pin) {
        this.pin = pin;

        setTitle("Bank Management System - Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(850, 600));
        setSize(1100, 720);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ================= SIDEBAR =================
        JPanel sidebar = new JPanel();
        sidebar.setBackground(NAVY);
        sidebar.setPreferredSize(new Dimension(235, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(new EmptyBorder(30, 18, 25, 18));

        JLabel logo = new JLabel("\u25C8  MY BANK");
        logo.setFont(new Font("SansSerif", Font.BOLD, 23));
        logo.setForeground(Color.WHITE);
        logo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel sideSubtitle = new JLabel("BANKING SERVICES");
        sideSubtitle.setFont(new Font("SansSerif", Font.BOLD, 10));
        sideSubtitle.setForeground(new Color(157, 184, 219));
        sideSubtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        sidebar.add(logo);
        sidebar.add(Box.createVerticalStrut(8));
        sidebar.add(sideSubtitle);
        sidebar.add(Box.createVerticalStrut(45));

        JLabel menuHeading = new JLabel("MENU");
        menuHeading.setFont(new Font("SansSerif", Font.BOLD, 11));
        menuHeading.setForeground(new Color(157, 184, 219));
        menuHeading.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(menuHeading);
        sidebar.add(Box.createVerticalStrut(14));

        addSidebarItem(sidebar, "\u25A3  Dashboard", true);
        addSidebarItem(sidebar, "\u2197  Transactions", false);
        addSidebarItem(sidebar, "\u25CE  Account services", false);

        sidebar.add(Box.createVerticalGlue());

        JLabel security = new JLabel(
                "<html><div style='width:165px'>"
                        + "<b>Secure Banking</b><br><br>"
                        + "Keep your PIN private. Never share it with anyone."
                        + "</div></html>"
        );
        security.setFont(new Font("SansSerif", Font.PLAIN, 12));
        security.setForeground(new Color(207, 222, 242));
        security.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(security);

        sidebar.add(Box.createVerticalStrut(25));

        JButton logout = new JButton("Logout");
        styleButton(logout, new Color(34, 58, 92), Color.WHITE);
        logout.setAlignmentX(Component.LEFT_ALIGNMENT);
        logout.setMaximumSize(new Dimension(Integer.MAX_VALUE, 43));
        logout.addActionListener(e -> {
            dispose();
            new Login();
        });
        sidebar.add(logout);

        // ================= MAIN CONTENT =================
        JPanel content = new JPanel(new BorderLayout(0, 24));
        content.setBackground(BG);
        content.setBorder(new EmptyBorder(30, 35, 30, 35));

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JPanel headerText = new JPanel();
        headerText.setOpaque(false);
        headerText.setLayout(new BoxLayout(headerText, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Dashboard");
        title.setFont(new Font("SansSerif", Font.BOLD, 29));
        title.setForeground(TEXT);

        JLabel subtitle = new JLabel(
                "Welcome back! What would you like to do today?"
        );
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subtitle.setForeground(MUTED);

        headerText.add(title);
        headerText.add(Box.createVerticalStrut(7));
        headerText.add(subtitle);

        JLabel status = new JLabel("\u25CF  BANKING SERVICES");
        status.setFont(new Font("SansSerif", Font.BOLD, 10));
        status.setForeground(new Color(21, 128, 91));
        status.setBorder(new EmptyBorder(9, 12, 9, 12));
        status.setOpaque(true);
        status.setBackground(new Color(220, 247, 235));

        header.add(headerText, BorderLayout.WEST);
        header.add(status, BorderLayout.EAST);

        // Account banner
        JPanel banner = new JPanel(new BorderLayout(15, 10));
        banner.setBackground(NAVY);
        banner.setBorder(new EmptyBorder(24, 27, 24, 27));

        JPanel bannerText = new JPanel();
        bannerText.setOpaque(false);
        bannerText.setLayout(new BoxLayout(bannerText, BoxLayout.Y_AXIS));

        JLabel bannerLabel = new JLabel("YOUR BANK ACCOUNT");
        bannerLabel.setFont(new Font("SansSerif", Font.BOLD, 11));
        bannerLabel.setForeground(new Color(181, 207, 240));

        JLabel bannerTitle = new JLabel("Banking made simple.");
        bannerTitle.setFont(new Font("SansSerif", Font.BOLD, 25));
        bannerTitle.setForeground(Color.WHITE);

        JLabel bannerDescription = new JLabel(
                "Manage your transactions securely and easily."
        );
        bannerDescription.setFont(new Font("SansSerif", Font.PLAIN, 13));
        bannerDescription.setForeground(new Color(216, 229, 247));

        bannerText.add(bannerLabel);
        bannerText.add(Box.createVerticalStrut(10));
        bannerText.add(bannerTitle);
        bannerText.add(Box.createVerticalStrut(8));
        bannerText.add(bannerDescription);

        JLabel bannerIcon = new JLabel("\u25C8");
        bannerIcon.setFont(new Font("SansSerif", Font.PLAIN, 60));
        bannerIcon.setForeground(new Color(139, 187, 255));

        banner.add(bannerText, BorderLayout.CENTER);
        banner.add(bannerIcon, BorderLayout.EAST);

        // Transaction section
        JPanel transactions = new JPanel(new BorderLayout(0, 17));
        transactions.setOpaque(false);

        JPanel sectionHeader = new JPanel(new BorderLayout());
        sectionHeader.setOpaque(false);

        JLabel sectionTitle = new JLabel("Quick Actions");
        sectionTitle.setFont(new Font("SansSerif", Font.BOLD, 21));
        sectionTitle.setForeground(TEXT);

        JLabel sectionSubtitle = new JLabel("Choose a banking service");
        sectionSubtitle.setFont(new Font("SansSerif", Font.PLAIN, 12));
        sectionSubtitle.setForeground(MUTED);

        sectionHeader.add(sectionTitle, BorderLayout.NORTH);
        sectionHeader.add(sectionSubtitle, BorderLayout.SOUTH);

        JPanel grid = new JPanel(new GridLayout(3, 2, 16, 14));
        grid.setOpaque(false);

        b1 = createActionButton("01", "Deposit", "Add money to your account");
        b2 = createActionButton("02", "Withdraw Cash", "Withdraw money");
        b3 = createActionButton("03", "Fast Cash", "Quick withdrawal options");
        b4 = createActionButton("04", "Mini Statement", "Review recent transactions");
        b5 = createActionButton("05", "Change PIN", "Update your security PIN");
        b6 = createActionButton("06", "Balance Enquiry", "Check your account balance");

        grid.add(createActionCard(b1, new Color(226, 239, 255)));
        grid.add(createActionCard(b2, new Color(255, 237, 225)));
        grid.add(createActionCard(b3, new Color(227, 246, 239)));
        grid.add(createActionCard(b4, new Color(239, 232, 255)));
        grid.add(createActionCard(b5, new Color(255, 242, 216)));
        grid.add(createActionCard(b6, new Color(224, 244, 248)));

        transactions.add(sectionHeader, BorderLayout.NORTH);
        transactions.add(grid, BorderLayout.CENTER);

        JPanel center = new JPanel(new BorderLayout(0, 23));
        center.setOpaque(false);
        center.add(banner, BorderLayout.NORTH);
        center.add(transactions, BorderLayout.CENTER);

        JLabel footer = new JLabel(
                "Keep your banking credentials safe and secure."
        );
        footer.setFont(new Font("SansSerif", Font.PLAIN, 11));
        footer.setForeground(MUTED);
        footer.setBorder(new EmptyBorder(8, 0, 0, 0));

        JPanel body = new JPanel(new BorderLayout(0, 15));
        body.setOpaque(false);
        body.add(header, BorderLayout.NORTH);
        body.add(center, BorderLayout.CENTER);
        body.add(footer, BorderLayout.SOUTH);

        content.add(body, BorderLayout.CENTER);

        // Keep EXIT as a compatibility alias for the original button.
        b7 = new JButton("EXIT");
        b7.addActionListener(this);

        add(sidebar, BorderLayout.WEST);
        add(content, BorderLayout.CENTER);

        setVisible(true);
    }

    private void addSidebarItem(
            JPanel sidebar, String text, boolean active) {

        JLabel item = new JLabel(text);
        item.setFont(new Font("SansSerif", Font.BOLD, 13));
        item.setForeground(active ? Color.WHITE : new Color(190, 207, 231));
        item.setBorder(new EmptyBorder(13, 12, 13, 8));
        item.setAlignmentX(Component.LEFT_ALIGNMENT);
        item.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));

        if (active) {
            item.setOpaque(true);
            item.setBackground(new Color(34, 67, 111));
        }

        sidebar.add(item);
        sidebar.add(Box.createVerticalStrut(7));
    }

    private JButton createActionButton(
            String number, String title, String description) {

        JButton button = new JButton(
                "<html><div style='text-align:left'>"
                        + "<span style='font-size:10px;color:#718096'>"
                        + number + "</span><br>"
                        + "<span style='font-size:15px'><b>"
                        + title + "</b></span><br>"
                        + "<span style='font-size:10px;color:#718096'>"
                        + description + "</span>"
                        + "</div></html>"
        );

        button.setFont(new Font("SansSerif", Font.PLAIN, 13));
        button.setForeground(TEXT);
        button.setBackground(Color.WHITE);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(10, 15, 10, 10)
        ));
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addActionListener(this);

        return button;
    }

    private JPanel createActionCard(JButton button, Color accent) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(0, 0, 0, 0)
        ));

        JPanel accentBar = new JPanel();
        accentBar.setBackground(accent);
        accentBar.setPreferredSize(new Dimension(6, 1));

        card.add(accentBar, BorderLayout.WEST);
        card.add(button, BorderLayout.CENTER);

        return card;
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
        Object source = e.getSource();

        if (source == b1) {
            setVisible(false);
            new Deposit(pin);

        } else if (source == b2) {
            setVisible(false);
            new Withdrawl(pin);

        } else if (source == b3) {
            setVisible(false);
            new FastCash(pin);

        } else if (source == b4) {
            new mini(pin);

        } else if (source == b5) {
            setVisible(false);
            new Pin(pin);

        } else if (source == b6) {
            setVisible(false);
            new BalanceEnquriy(pin);

        } else if (source == b7) {
            dispose();
            new Login();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Login::new);
    }
}
