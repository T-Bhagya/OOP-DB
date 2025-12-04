import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CartView {

    public static JPanel createView(CardLayout cardLayout, JPanel mainPanel, String customerId) {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(20, 20, 20, 20);

        JPanel card = Style.createCardPanel();
        card.setLayout(new BorderLayout(0, 20));

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        Style.addHeader(headerPanel, "🛒", "Your Cart", "Items ready for checkout");
        card.add(headerPanel, BorderLayout.NORTH);

        // Cart Table
        String[] columns = {"Book Title", "Qty", "Date Added"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setRowHeight(30);
        table.getTableHeader().setBackground(new Color(253, 230, 138));

        JScrollPane scrollPane = new JScrollPane(table);
        card.add(scrollPane, BorderLayout.CENTER);

        // Load Cart Items
        loadCartItems(model, customerId);

        // Navigation
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        navPanel.setOpaque(false);
        JButton backBtn = new JButton("← Back to Dashboard");
        Style.styleSecondaryButton(backBtn);
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "HOME"));
        navPanel.add(backBtn);
        
        card.add(navPanel, BorderLayout.SOUTH);

        wrapper.add(card, gbc);
        return wrapper;
    }

    private static void loadCartItems(DefaultTableModel model, String customerId) {
        String sql = "SELECT b.Title, o.Qty, o.Date_of_order FROM Order_Table o " +
                     "JOIN Book b ON o.ISBN = b.ISBN " +
                     "JOIN Cart c ON o.Cart_id = c.Cart_id " +
                     "WHERE c.Customer_id = ? AND c.Cart_Status = FALSE";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, customerId);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                model.addRow(new Object[]{
                    rs.getString("Title"), rs.getInt("Qty"), rs.getDate("Date_of_order")
                });
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
}