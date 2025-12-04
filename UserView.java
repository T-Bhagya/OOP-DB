import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class UserView extends JFrame {

   
    private User logic;

    
    private JTextField txtfname;
    private JTextField txtlname;
    private JPasswordField txtpassword;
    private JTextField txtemail;
    private JTextField txttelno;
    private JTextField txtaddress;
    private JButton submitBtn;

    public UserView() {
        
        logic = new User(this);

        
        setTitle("Register Form - Cinnamon & Chapters");
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        // 2. Background Panel (Gradient)
        JPanel backgroundPanel = new BackgroundPanel();
        backgroundPanel.setLayout(new GridBagLayout()); // Centers the form card
        setContentPane(backgroundPanel);

        // 3. Form Card (White container)
        JPanel formCard = new JPanel();
        formCard.setLayout(new BoxLayout(formCard, BoxLayout.Y_AXIS));
        formCard.setBackground(new Color(255, 255, 255, 235));
        formCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(253, 230, 138), 1),
            new EmptyBorder(30, 40, 30, 40)
        ));

        // --- Header Section ---
        JLabel titleLabel = new JLabel("Create Account");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setForeground(new Color(120, 53, 15)); // Amber-900
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subTitle = new JLabel("Join us today");
        subTitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subTitle.setForeground(new Color(180, 83, 9));
        subTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        formCard.add(titleLabel);
        formCard.add(subTitle);
        formCard.add(Box.createVerticalStrut(25));

        // --- Input Fields Section ---
        txtfname = addFormField(formCard, "First Name");
        txtlname = addFormField(formCard, "Last Name");
        
        // Password specific handling
        formCard.add(createLabel("Password"));
        txtpassword = new JPasswordField(20);
        styleField(txtpassword);
        formCard.add(txtpassword);
        formCard.add(Box.createVerticalStrut(15));

        txtemail = addFormField(formCard, "Email");
        txttelno = addFormField(formCard, "Telephone Number");
        txtaddress = addFormField(formCard, "Address");

        // --- Buttons Section ---
        formCard.add(Box.createVerticalStrut(20));
        
        submitBtn = new JButton("Submit Registration");
        stylePrimaryButton(submitBtn);
        submitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // CONNECTION: Connect Button to Logic
        submitBtn.addActionListener(e -> logic.processRegistration());
        
        formCard.add(submitBtn);
        formCard.add(Box.createVerticalStrut(10));

        // Add card to background
        backgroundPanel.add(formCard);
    }

    // --- Public Methods for Logic to Access Data ---

    public String getFirstNameInput() { return txtfname.getText().trim(); }
    public String getLastNameInput() { return txtlname.getText().trim(); }
    public String getPasswordInput() { return new String(txtpassword.getPassword()); }
    public String getEmailInput() { return txtemail.getText().trim(); }
    public String getTelInput() { return txttelno.getText().trim(); }
    public String getAddressInput() { return txtaddress.getText().trim(); }

    public void showErrorDialog(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Validation Error", JOptionPane.WARNING_MESSAGE);
    }

    public void showSuccessDialog(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public void closeWindow() {
        this.dispose();
    }

    // --- Internal Styling Helpers ---

    private JTextField addFormField(JPanel panel, String labelText) {
        panel.add(createLabel(labelText));
        JTextField field = new JTextField(20);
        styleField(field);
        panel.add(field);
        panel.add(Box.createVerticalStrut(15));
        return field;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 12));
        label.setForeground(new Color(120, 53, 15));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
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
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setBorder(new EmptyBorder(10, 20, 10, 20));
    }

    // --- Custom Gradient Panel ---
    class BackgroundPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            Color color1 = new Color(255, 251, 235);
            Color color2 = new Color(254, 242, 242);
            GradientPaint gp = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(UserView::new);
    }
}