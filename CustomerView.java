import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CustomerView {

    public static JPanel createView(CardLayout cardLayout, JPanel mainPanel) {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(false);

        JPanel card = Style.createCardPanel();
        card.setPreferredSize(new Dimension(550, 500)); // Wider for table

        Style.addHeader(card, "👥", "Customer Details", "View registered customers");

        // Search Bar
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(new Color(255, 255, 255, 0));
        searchPanel.setMaximumSize(new Dimension(500, 40));
        
        JTextField searchField = new JTextField(20);
        Style.styleField(searchField);
        JButton searchBtn = new JButton("Search");
        Style.stylePrimaryButton(searchBtn);
        searchBtn.setPreferredSize(new Dimension(80, 35));
        
        searchPanel.add(new JLabel("Search: "));
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);
        
        card.add(searchPanel);
        card.add(Box.createVerticalStrut(10));

        // Table
        String[] columns = {"ID", "Name", "Email", "Phone"};
        Object[][] data = {
        };
        
        DefaultTableModel model = new DefaultTableModel(data, columns);
        JTable table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setRowHeight(25);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(500, 200));
        scrollPane.setMaximumSize(new Dimension(500, 200));
        
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