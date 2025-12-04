import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class OpenFrame4 extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel; // The container for swapping views

    // --- Login Components ---
    private JComboBox<String> roleBox;
    private JTextField logusertxt;
    private JPasswordField logpasstxt;

    // --- Registration Components (From User.java) ---
    private JTextField txtfname;
    private JTextField txtlname;
    private JPasswordField txtpassword;
    private JTextField txtemail;
    private JTextField txttelno;
    private JTextField txtaddress;

    public OpenFrame4() {
        setTitle("Cinnamon & Chapters - System");
        setSize(700, 700); // Increased height to fit the registration form
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen

        // 1. Setup Main Panel with CardLayout
        cardLayout = new CardLayout();
        mainPanel = new BackgroundPanel(); // Uses the gradient background
        mainPanel.setLayout(cardLayout);

        // 2. Create the Views (Cards)
        JPanel loginView = createLoginView();
        JPanel registerChoiceView = createRegisterChoiceView();
        JPanel registerFormView = createRegisterFormView(); // New View for User.java form

        // 3. Add Views to Layout
        mainPanel.add(loginView, "LOGIN");
        mainPanel.add(registerChoiceView, "REGISTER_CHOICE");
        mainPanel.add(registerFormView, "REGISTER_FORM");

        setContentPane(mainPanel);
        setVisible(true);
    }

    
    // VIEW 1: THE LOGIN FORM
  
    private JPanel createLoginView() {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(false);

        JPanel loginCard = new JPanel();
        loginCard.setLayout(new BoxLayout(loginCard, BoxLayout.Y_AXIS));
        loginCard.setBackground(new Color(255, 255, 255, 230));
        loginCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(253, 230, 138), 1),
            new EmptyBorder(40, 50, 40, 50)
        ));

        // Header
        JLabel iconLabel = new JLabel("☕");
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("Welcome Back");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setForeground(new Color(120, 53, 15));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Please log in to continue");
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(180, 83, 9));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginCard.add(iconLabel);
        loginCard.add(Box.createVerticalStrut(10));
        loginCard.add(titleLabel);
        loginCard.add(subtitleLabel);
        loginCard.add(Box.createVerticalStrut(30));

        // Fields
        loginCard.add(createLabel("Select Role"));
        String[] roles = {"Admin", "Customer"};
        roleBox = new JComboBox<>(roles);
        styleComboBox(roleBox);
        loginCard.add(roleBox);
        loginCard.add(Box.createVerticalStrut(15));

        loginCard.add(createLabel("Username"));
        logusertxt = createTextField();
        loginCard.add(logusertxt);
        loginCard.add(Box.createVerticalStrut(15));

        loginCard.add(createLabel("Password"));
        logpasstxt = createPasswordField();
        loginCard.add(logpasstxt);
        loginCard.add(Box.createVerticalStrut(25));

        // Buttons
        JButton logbtn = new JButton("Log In");
        stylePrimaryButton(logbtn);
        logbtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        logbtn.addActionListener(e -> handleLogin());
        loginCard.add(logbtn);

        loginCard.add(Box.createVerticalStrut(15));

        JLabel orLabel = new JLabel("- OR -");
        orLabel.setForeground(Color.GRAY);
        orLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginCard.add(orLabel);
        loginCard.add(Box.createVerticalStrut(15));

        JButton regbtn = new JButton("Create New Account");
        styleSecondaryButton(regbtn);
        regbtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        regbtn.addActionListener(e -> cardLayout.show(mainPanel, "REGISTER_CHOICE"));
        loginCard.add(regbtn);

        wrapper.add(loginCard);
        return wrapper;
    }

   
    // VIEW 2: THE REGISTER CHOICE SCREEN

    private JPanel createRegisterChoiceView() {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(false);

        JPanel choiceCard = new JPanel();
        choiceCard.setLayout(new BoxLayout(choiceCard, BoxLayout.Y_AXIS));
        choiceCard.setBackground(new Color(255, 255, 255, 240));
        choiceCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(253, 230, 138), 1),
            new EmptyBorder(40, 60, 40, 60)
        ));

        JLabel iconLabel = new JLabel("📝");
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel("Create Account");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(new Color(120, 53, 15));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        choiceCard.add(iconLabel);
        choiceCard.add(Box.createVerticalStrut(10));
        choiceCard.add(title);
        choiceCard.add(Box.createVerticalStrut(30));

        // Buttons
        JButton adminBtn = new JButton("Admin Registration");
        styleBigButton(adminBtn);
        adminBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        // ACTION: Go to the merged Register Form
        adminBtn.addActionListener(e -> cardLayout.show(mainPanel, "REGISTER_FORM"));

        JButton custBtn = new JButton("Customer Registration");
        styleBigButton(custBtn);
        custBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        // For now, we can wire both to the same form, or keep Customer separate
        // wiring to same form for this example:
        custBtn.addActionListener(e -> {
             // If you have a separate Customer class, do: new Customer(); dispose();
             // For now, going to the same form:
             cardLayout.show(mainPanel, "REGISTER_FORM");
        });

        choiceCard.add(adminBtn);
        choiceCard.add(Box.createVerticalStrut(15));
        choiceCard.add(custBtn);
        choiceCard.add(Box.createVerticalStrut(25));

        JButton backBtn = new JButton("← Back to Login");
        styleLinkButton(backBtn);
        backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "LOGIN"));
        choiceCard.add(backBtn);

        wrapper.add(choiceCard);
        return wrapper;
    }

  
    // VIEW 3: THE REGISTRATION FORM (Merged User.java)
    
    private JPanel createRegisterFormView() {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(false);

        JPanel formCard = new JPanel();
        formCard.setLayout(new BoxLayout(formCard, BoxLayout.Y_AXIS));
        formCard.setBackground(new Color(255, 255, 255, 235));
        formCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(253, 230, 138), 1),
            new EmptyBorder(30, 40, 30, 40)
        ));

        // Header
        JLabel titleLabel = new JLabel("Registration Details");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setForeground(new Color(120, 53, 15));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formCard.add(titleLabel);
        formCard.add(Box.createVerticalStrut(20));

        // Fields
        txtfname = addFormField(formCard, "First Name");
        txtlname = addFormField(formCard, "Last Name");
        
        formCard.add(createLabel("Password"));
        txtpassword = createPasswordField();
        formCard.add(txtpassword);
        formCard.add(Box.createVerticalStrut(15));

        txtemail = addFormField(formCard, "Email");
        txttelno = addFormField(formCard, "Telephone Number");
        txtaddress = addFormField(formCard, "Address");

        formCard.add(Box.createVerticalStrut(20));
        
        // Submit Button
        JButton submitBtn = new JButton("Submit Registration");
        stylePrimaryButton(submitBtn);
        submitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitBtn.addActionListener(e -> validateAndSubmitRegister());
        formCard.add(submitBtn);

        formCard.add(Box.createVerticalStrut(10));

        // Back Button
        JButton backBtn = new JButton("Cancel");
        styleSecondaryButton(backBtn);
        backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "REGISTER_CHOICE"));
        formCard.add(backBtn);

        wrapper.add(formCard);
        return wrapper;
    }

    // --- Logic from User.java ---
    private void validateAndSubmitRegister() {
        String fNameInput = txtfname.getText().trim();
        String lNameInput = txtlname.getText().trim();
        String passInput = new String(txtpassword.getPassword());
        String emailInput = txtemail.getText().trim();
        String telInput = txttelno.getText().trim();
        String addrInput = txtaddress.getText().trim();

        // Validation
        if (fNameInput.isEmpty() || fNameInput.length() > 15) {
            showError("First Name must be between 1-15 characters."); return;
        }
        if (lNameInput.isEmpty() || lNameInput.length() > 30) {
            showError("Last Name must be between 1-30 characters."); return;
        }
        if (passInput.length() <= 8 || passInput.length() > 20) {
            showError("Password must be between 9-20 characters."); return;
        }
        if (emailInput.isEmpty() || emailInput.length() > 50 || !emailInput.contains("@")) {
            showError("Please enter a valid Email address."); return;
        }
        if (telInput.length() != 10 || !telInput.matches("\\d+")) {
            showError("Telephone must be exactly 10 digits."); return;
        }
        if (addrInput.isEmpty() || addrInput.length() > 100) {
            showError("Address must be between 1-100 characters."); return;
        }

        // Success
        JOptionPane.showMessageDialog(this, "Registration Successful! Please Log In.", "Success", JOptionPane.INFORMATION_MESSAGE);
        // Go back to login screen after success
        cardLayout.show(mainPanel, "LOGIN");
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Validation Error", JOptionPane.WARNING_MESSAGE);
    }

    private void handleLogin() {
        String username = logusertxt.getText();
        String enteredPass = new String(logpasstxt.getPassword());
        String selectedRole = (String) roleBox.getSelectedItem();
        String dbPassword = "";

        try {
            if ("Admin".equals(selectedRole)) {
                dbPassword = DBConnection.loginCheck("Admin_staff", username);
            } else {
                dbPassword = DBConnection.loginCheck("Customer", username);
            }

            if (dbPassword != null && dbPassword.equals(enteredPass)) {
                dispose(); // Close login
                if ("Admin".equals(selectedRole)) {
                    Admin_staff2.startWindow();
                } else {
                    // Open the full Customer dashboard using the logged-in ID
                    Customer.openDashboard(username);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Credentials", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }

    // --- UI Helpers ---
    private JTextField addFormField(JPanel panel, String labelText) {
        panel.add(createLabel(labelText));
        JTextField field = createTextField();
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

    private JTextField createTextField() {
        JTextField field = new JTextField(20);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(253, 230, 138)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        field.setBackground(new Color(255, 251, 235));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        return field;
    }

    private JPasswordField createPasswordField() {
        JPasswordField field = new JPasswordField(20);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(253, 230, 138)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        field.setBackground(new Color(255, 251, 235));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        return field;
    }

    private void styleComboBox(JComboBox<String> box) {
        box.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        box.setBackground(new Color(255, 251, 235));
        box.setAlignmentX(Component.LEFT_ALIGNMENT);
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
    }

    private void styleSecondaryButton(JButton btn) {
        btn.setBackground(Color.WHITE);
        btn.setForeground(new Color(245, 158, 11));
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createLineBorder(new Color(245, 158, 11), 1));
        btn.setOpaque(true);
    }

    private void styleBigButton(JButton btn) {
        btn.setBackground(new Color(255, 251, 235));
        btn.setForeground(new Color(120, 53, 15));
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 16));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createLineBorder(new Color(245, 158, 11), 2));
        btn.setOpaque(true);
    }

    private void styleLinkButton(JButton btn) {
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setForeground(new Color(180, 83, 9));
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // Gradient Background
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
        SwingUtilities.invokeLater(OpenFrame4::new);
    }
}