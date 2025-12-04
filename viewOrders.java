import java.awt.*;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class viewOrders {
   public static JPanel createOrderView(CardLayout cardLayout, JPanel mainPanel) {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(false);

        JPanel card = Style.createCardPanel();
        Style.addHeader(card, "📦", "View Orders", "Manage customer orders");

        String[] columns = {"Order ID", "Cart ID", "ISBN", "Date of Order", "Quantity"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(500, 300));
        scrollPane.setMaximumSize(new Dimension(500, 300));

        // Load data from database
        try {
            ResultSet rs = DBConnection.view("order_table");
            while (rs.next()) {
                Object[] row = {
                        rs.getString("Order_id"),
                        rs.getString("Cart_id"),
                        rs.getString("ISBN"),
                        rs.getString("Date_of_order"),
                        rs.getInt("qty")
                };
                model.addRow(row);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(card, "Database Error: " + ex.getMessage());
        }

        card.add(scrollPane);
        card.add(Box.createVerticalStrut(20));

        // Back Button
        JButton backBtn = new JButton("← Back to Dashboard");
        Style.styleSecondaryButton(backBtn);
        backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "DASHBOARD"));

        card.add(backBtn);
        wrapper.add(card);
        return wrapper;
   }
    
}