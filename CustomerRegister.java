import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class CustomerRegister extends JFrame {

    // CardLayout to switch between Form and Success screens
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);

    // Form Components
    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JLabel errorLabel;

    public CustomerRegister() {
        setTitle("Join Our Community - Cinnamon & Chapters");
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Closes only this window
        setLocationRelativeTo(null);

        // 1. Create the Form Panel
        JPanel formPanel = new BackgroundPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(new EmptyBorder(40, 40, 40, 40));

        // --- Back Button ---
        JButton backButton = new JButton("⬅ Back to Login");
        styleLinkButton(backButton);
        backButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        backButton.addActionListener(e -> {
            dispose(); // Close this window
            new OpenFrame4(); // Re-open login
        });
        formPanel.add(backButton);
        formPanel.add(Box.createVerticalStrut(20));

        // --- Header ---
        JLabel iconLabel = new JLabel("☕ 📖"); // Coffee and Book unicode
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel titleLabel = new JLabel("Join Our Community");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setForeground(new Color(120, 53, 15)); // Amber-900
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subTitleLabel = new JLabel("Create your customer account");
        subTitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subTitleLabel.setForeground(new Color(180, 83, 9)); // Amber-700
        subTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        formPanel.add(iconLabel);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(titleLabel);
        formPanel.add(subTitleLabel);
        formPanel.add(Box.createVerticalStrut(30));

        // --- Input Fields ---
        formPanel.add(createLabel("Username"));
        usernameField = createTextField();
        formPanel.add(usernameField);
        formPanel.add(Box.createVerticalStrut(15));

        formPanel.add(createLabel("Email"));
        emailField = createTextField();
        formPanel.add(emailField);
        formPanel.add(Box.createVerticalStrut(15));

        formPanel.add(createLabel("Password"));
        passwordField = createPasswordField();
        formPanel.add(passwordField);
        formPanel.add(Box.createVerticalStrut(15));

        formPanel.add(createLabel("Confirm Password"));
        confirmPasswordField = createPasswordField();
        formPanel.add(confirmPasswordField);
        formPanel.add(Box.createVerticalStrut(20));

        // --- Error Message Area ---
        errorLabel = new JLabel("");
        errorLabel.setForeground(new Color(220, 38, 38)); // Red-600
        errorLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(errorLabel);
        formPanel.add(Box.createVerticalStrut(10));

        // --- Submit Button ---
        JButton submitButton = new JButton("Create Customer Account");
        stylePrimaryButton(submitButton);
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.addActionListener(this::handleRegister);
        formPanel.add(submitButton);

        // --- Footer Text ---
        formPanel.add(Box.createVerticalStrut(20));
        JLabel footerLabel = new JLabel("<html><center>By creating an account, you'll get access to<br>exclusive book recommendations!</center></html>");
        footerLabel.setFont(new Font("SansSerif", Font.ITALIC, 11));
        footerLabel.setForeground(new Color(146, 64, 14)); // Amber-800
        footerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(footerLabel);

        // 2. Create the Success Panel
        JPanel successPanel = new BackgroundPanel();
        successPanel.setLayout(new GridBagLayout()); // Center everything
        
        JPanel successContent = new JPanel();
        successContent.setOpaque(false);
        successContent.setLayout(new BoxLayout(successContent, BoxLayout.Y_AXIS));

        JLabel checkIcon = new JLabel("✅");
        checkIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 60));
        checkIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel successTitle = new JLabel("Welcome!");
        successTitle.setFont(new Font("SansSerif", Font.BOLD, 26));
        successTitle.setForeground(new Color(120, 53, 15));
        successTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel successMsg = new JLabel("Account created successfully.");
        successMsg.setForeground(new Color(180, 83, 9));
        successMsg.setAlignmentX(Component.CENTER_ALIGNMENT);

        successContent.add(checkIcon);
        successContent.add(Box.createVerticalStrut(20));
        successContent.add(successTitle);
        successContent.add(Box.createVerticalStrut(10));
        successContent.add(successMsg);

        successPanel.add(successContent);

        // Add panels to CardLayout
        mainPanel.add(formPanel, "FORM");
        mainPanel.add(successPanel, "SUCCESS");

        add(mainPanel);
        setVisible(true);
    }

    private void handleRegister(ActionEvent e) {
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPass = new String(confirmPasswordField.getPassword());

        // 1. Validation Logic (Matching the React code)
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPass.isEmpty()) {
            showError("Please fill in all fields");
            return;
        }

        if (!password.equals(confirmPass)) {
            showError("Passwords do not match");
            return;
        }

        if (password.length() < 6) {
            showError("Password must be at least 6 characters");
            return;
        }

        // 2. Database / Storage Logic
        try {
            // Check if user exists (Replacing localStorage check with DB check)
            // Assuming you have a method like DBConnection.checkUserExists(username)
            // For now, we will simulate it:
            boolean userExists = false; // DBConnection.checkUserExists(username);

            if (userExists) {
                showError("Username already exists");
                return;
            }

            // Register User
            // DBConnection.registerCustomer(username, email, password);
            // Since I don't have your DB code, I'm simulating a success:
            System.out.println("Registered: " + username + ", " + email);

            // 3. Success State
            errorLabel.setText("");
            cardLayout.show(mainPanel, "SUCCESS");

            // Redirect after 2 seconds (Timer)
            Timer timer = new Timer(2000, evt -> {
                dispose(); // Close register
                new OpenFrame4(); // Go to login
            });
            timer.setRepeats(false);
            timer.start();

        } catch (Exception ex) {
            ex.printStackTrace();
            showError("Database error occurred");
        }
    }

    private void showError(String message) {
        errorLabel.setText(message);
        // Shake animation effect could go here
    }

    // --- Helper UI Methods ---

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 12));
        label.setForeground(new Color(120, 53, 15)); // Amber-900
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(253, 230, 138)), // Amber-200
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        field.setBackground(new Color(255, 251, 235)); // Amber-50
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        return field;
    }

    private JPasswordField createPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(253, 230, 138)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        field.setBackground(new Color(255, 251, 235));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        return field;
    }

    private void stylePrimaryButton(JButton btn) {
        btn.setBackground(new Color(245, 158, 11)); // Amber-500
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Make it look flat and modern
        btn.setBorderPainted(false);
        btn.setOpaque(true);
    }

    private void styleLinkButton(JButton btn) {
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setForeground(new Color(180, 83, 9)); // Amber-700
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // Custom Panel to draw the gradient background
    class BackgroundPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            int w = getWidth();
            int h = getHeight();
            // Gradient from Amber-50 to Red-50
            Color color1 = new Color(255, 251, 235); // Amber-50
            Color color2 = new Color(254, 242, 242); // Red-50
            GradientPaint gp = new GradientPaint(0, 0, color1, w, h, color2);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, w, h);
        }
    }

    public static void main(String[] args) {
        // For testing implementation
        SwingUtilities.invokeLater(CustomerRegister::new);
    }
}