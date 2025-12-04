import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class User implements ActionListener {

    private UserView view;

   
     private String fname;
     private String lname;
     private String password;
     private String email;
     private String address;
     private String telno;
    public User(UserView view) {
        this.view = view;
    }

    public void processRegistration() {
       
        String fNameInput = view.getFirstNameInput();
        String lNameInput = view.getLastNameInput();
        String passInput = view.getPasswordInput();
        String emailInput = view.getEmailInput();
        String telInput = view.getTelInput();
        String addrInput = view.getAddressInput();

        if (fNameInput.isEmpty() || fNameInput.length() > 15) {
            view.showErrorDialog("First Name must be between 1-15 characters.");
            return;
        }

        if (lNameInput.isEmpty() || lNameInput.length() > 30) {
            view.showErrorDialog("Last Name must be between 1-30 characters.");
            return;
        }

        if (passInput.length() <= 8 || passInput.length() > 20) {
            view.showErrorDialog("Password must be between 9-20 characters.");
            return;
        }

        if (emailInput.isEmpty() || emailInput.length() > 50 || !emailInput.contains("@")) {
            view.showErrorDialog("Please enter a valid Email address.");
            return;
        }

        if (telInput.length() != 10 || !telInput.matches("\\d+")) {
            view.showErrorDialog("Telephone must be exactly 10 digits.");
            return;
        }

        if (addrInput.isEmpty() || addrInput.length() > 100) {
            view.showErrorDialog("Address must be between 1-100 characters.");
            return;
        }

        
        this.fname = fNameInput;
        this.lname = lNameInput;
        this.password = passInput;
        this.email = emailInput;
        this.telno = telInput;
        this.address = addrInput;

        view.showSuccessDialog("Registration Successful!");
        
       
         view.closeWindow();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        processRegistration();
    }

    
}      
      