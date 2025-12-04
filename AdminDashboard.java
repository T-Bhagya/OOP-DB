import java.awt.*;
import javax.swing.*;

public class AdminDashboard {

    private JFrame dashboardFrame;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public AdminDashboard() {
        // 1. Main Frame Setup
        dashboardFrame = new JFrame("Admin Dashboard - Cinnamon & Chapters");
        dashboardFrame.setSize(700, 700);
        dashboardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dashboardFrame.setLocationRelativeTo(null);

        // 2. Layout Setup
        cardLayout = new CardLayout();
        mainPanel = new Style.BackgroundPanel(); // Use the custom background
        mainPanel.setLayout(cardLayout);

        // 3. Add All Views from External Classes
        mainPanel.add(createDashboardHome(), "DASHBOARD");
        mainPanel.add(BookViews.createBookMenuView(cardLayout, mainPanel), "BOOK_MENU");
        mainPanel.add(BookViews.createBookDetailsView(cardLayout, mainPanel, dashboardFrame), "BOOK_DETAILS");
        mainPanel.add(CustomerView.createView(cardLayout, mainPanel), "CUSTOMER_VIEW");
        mainPanel.add(viewOrders.createOrderView(cardLayout, mainPanel), "ORDER_VIEW");

        dashboardFrame.setContentPane(mainPanel);
        dashboardFrame.setVisible(true);
    }

    private JPanel createDashboardHome() {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(false);

        JPanel card = Style.createCardPanel();

        Style.addHeader(card, "👨‍💼", "Admin Dashboard", "Manage your bookstore");

        // Buttons
        JButton addBtn = new JButton("📚  Manage Books");
        Style.styleDashboardButton(addBtn);
        addBtn.addActionListener(e -> cardLayout.show(mainPanel, "BOOK_MENU"));

        JButton viewOrdersBtn = new JButton("📦  View Orders");
        Style.styleDashboardButton(viewOrdersBtn);
        viewOrdersBtn.addActionListener(e -> cardLayout.show(mainPanel, "ORDER_VIEW"));

        JButton viewCusBtn = new JButton("👥  Customer Details");
        Style.styleDashboardButton(viewCusBtn);
        viewCusBtn.addActionListener(e -> cardLayout.show(mainPanel, "CUSTOMER_VIEW"));

        JButton logoutBtn = new JButton("Log Out");
        logoutBtn.setBorderPainted(false);
        logoutBtn.setContentAreaFilled(false);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setForeground(new Color(220, 38, 38)); 
        logoutBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
        logoutBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        logoutBtn.addActionListener(e -> {
            dashboardFrame.dispose();
            new OpenFrame4(); 
        });

        card.add(addBtn);
        card.add(Box.createVerticalStrut(15));
        card.add(viewOrdersBtn);
        card.add(Box.createVerticalStrut(15));
        card.add(viewCusBtn);
        card.add(Box.createVerticalStrut(25));
        card.add(logoutBtn);

        wrapper.add(card);
        return wrapper;
    }
}