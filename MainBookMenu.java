import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class MainBookMenu extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel; // The container for sliding views

    // Details Form Fields (Moved to class level to access in listeners)
    private JTextField txtIsbn;
    private JTextField txtTitle;
    private JTextField txtAuthor;
    private JTextField txtCopy;

    public MainBookMenu() {
        setTitle("Book Menu - Cinnamon & Chapters");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 1. Setup Main Panel with CardLayout (and Gradient Background)
        cardLayout = new CardLayout();
        mainPanel = new BackgroundPanel(); 
        mainPanel.setLayout(cardLayout);

        // 2. Create the two views (Cards)
        JPanel menuView = createMenuView();
        JPanel detailsView = createDetailsView();

        // 3. Add them to the card layout
        mainPanel.add(menuView, "MENU");
        mainPanel.add(detailsView, "DETAILS");

        setContentPane(mainPanel);
        setVisible(true);
    }

    // --- View 1: The Main Menu ---
    private JPanel createMenuView() {
        // Wrapper to center the card
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(false);

        // The White Menu Card
        JPanel menuCard = new JPanel();
        menuCard.setLayout(new BoxLayout(menuCard, BoxLayout.Y_AXIS));
        menuCard.setBackground(new Color(255, 255, 255, 230));
        menuCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(253, 230, 138), 1),
            new EmptyBorder(40, 60, 40, 60)
        ));

        // Header
        JLabel iconLabel = new JLabel("📚");
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 50));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("Books Menu");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setForeground(new Color(120, 53, 15));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        menuCard.add(iconLabel);
        menuCard.add(Box.createVerticalStrut(10));
        menuCard.add(titleLabel);
        menuCard.add(Box.createVerticalStrut(30));

        // Button to Switch View
        JButton viewDetailsBtn = new JButton("Update Book Details");
        stylePrimaryButton(viewDetailsBtn);
        viewDetailsBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // ACTION: Switch to Details View
        viewDetailsBtn.addActionListener(e -> {
            cardLayout.show(mainPanel, "DETAILS");
        });

        menuCard.add(viewDetailsBtn);
        wrapper.add(menuCard);
        return wrapper;
    }

    // --- View 2: The Details Form ---
    private JPanel createDetailsView() {
        // Wrapper to center the card
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(false);

        // The White Form Card
        JPanel formCard = new JPanel();
        formCard.setLayout(new BoxLayout(formCard, BoxLayout.Y_AXIS));
        formCard.setBackground(new Color(255, 255, 255, 240));
        formCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(253, 230, 138), 1),
            new EmptyBorder(30, 40, 30, 40)
        ));

        // Header
        JLabel header = new JLabel("Book Information");
        header.setFont(new Font("SansSerif", Font.BOLD, 22));
        header.setForeground(new Color(120, 53, 15));
        header.setAlignmentX(Component.CENTER_ALIGNMENT);
        formCard.add(header);
        formCard.add(Box.createVerticalStrut(25));

        // Fields
        txtIsbn = addFormField(formCard, "ISBN:");
        txtTitle = addFormField(formCard, "Book Title:");
        txtAuthor = addFormField(formCard, "Author:");
        txtCopy = addFormField(formCard, "No. of Copies:");

        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        btnPanel.setBackground(new Color(255, 255, 255, 0));
        btnPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton saveBtn = new JButton("Update");
        stylePrimaryButton(saveBtn);
        
        JButton backBtn = new JButton("Back"); // Goes back to menu
        styleSecondaryButton(backBtn);

        btnPanel.add(saveBtn);
        btnPanel.add(backBtn);

        formCard.add(Box.createVerticalStrut(20));
        formCard.add(btnPanel);

        wrapper.add(formCard);

        // Actions
        backBtn.addActionListener(e -> {
            cardLayout.show(mainPanel, "MENU");
        });

        saveBtn.addActionListener(e -> {
            if(txtTitle.getText().isEmpty() || txtCopy.getText().isEmpty()) {
                 JOptionPane.showMessageDialog(this, "Please fill in required fields.", "Warning", JOptionPane.WARNING_MESSAGE);
                 return;
            }
            
            try {
                int copies = Integer.parseInt(txtCopy.getText());
                DBConnection.addBooks("book", txtIsbn.getText(), copies, txtAuthor.getText(), txtTitle.getText());
                JOptionPane.showMessageDialog(this, "Details for '" + txtTitle.getText() + "' Updated Successfully!");
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "Number of copies must be a valid integer.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        return wrapper;
    }

    // --- Helpers for styling ---
    private JTextField addFormField(JPanel panel, String labelText) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("SansSerif", Font.BOLD, 12));
        label.setForeground(new Color(120, 53, 15));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        
        JTextField field = new JTextField(20);
        styleField(field);
        panel.add(field);
        panel.add(Box.createVerticalStrut(15));
        return field;
    }

    private void styleField(JTextField field) {
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(253, 230, 138)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        field.setBackground(new Color(255, 251, 235));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private void stylePrimaryButton(JButton btn) {
        btn.setBackground(new Color(245, 158, 11));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setPreferredSize(new Dimension(120, 40));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorderPainted(false);
        btn.setOpaque(true);
    }

    private void styleSecondaryButton(JButton btn) {
        btn.setBackground(Color.WHITE);
        btn.setForeground(new Color(120, 53, 15));
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setPreferredSize(new Dimension(100, 40));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createLineBorder(new Color(245, 158, 11), 1));
        btn.setOpaque(true);
    }

    // Custom Gradient Panel Class
    class BackgroundPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            int w = getWidth();
            int h = getHeight();
            Color color1 = new Color(255, 251, 235); 
            Color color2 = new Color(254, 242, 242); 
            GradientPaint gp = new GradientPaint(0, 0, color1, w, h, color2);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, w, h);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainBookMenu::new);
    }
}