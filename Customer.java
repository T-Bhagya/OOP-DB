
public class Customer extends User {

    private static long idCounter = 200000000000000L;

   
    private UserView view;

    public Customer(UserView view) {
        super(view);
        this.view = view;
    }

    private String generateIdNumber() {
        String currentID = String.valueOf(idCounter);
        idCounter++;
        return currentID;   
    }

    @Override
    public void processRegistration() {
        
        String fName = view.getFirstNameInput();
        String lName = view.getLastNameInput();
        String password = view.getPasswordInput();
        String email = view.getEmailInput();
        String tel = view.getTelInput();
        String address = view.getAddressInput();

       
        if (fName.isEmpty() || lName.isEmpty() || password.length() < 8 || !email.contains("@")) {
            view.showErrorDialog("Please ensure all fields are filled correctly.");
            return;
        }

      
        String newId = generateIdNumber();

        try {
            DBConnection.insertForRegister(
                "Customer",    
                newId,         
                fName, 
                lName, 
                password, 
                email, 
                tel, 
                address
            );

           
            view.showSuccessDialog("Account Created! Your ID is: " + newId);
            
           
            view.closeWindow();
            openCustomerDashboard(newId);

        } catch (Exception ex) {
            ex.printStackTrace();
            view.showErrorDialog("Database Error: " + ex.getMessage());
        }
    }

   
    public void openCustomerDashboard(String customerId) {
       
        new CustomerDashboard(customerId);
    }
}