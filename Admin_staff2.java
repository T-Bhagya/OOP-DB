import java.awt.event.*;
import javax.swing.*;

public class Admin_staff2 extends User {

    private static long id = 100000000000000L;

    public Admin_staff2() {
        super();
        getFrame().setTitle("Admin Registration - Cinnamon & Chapters");
    }

    public static String idNumber() {
        String currentID = String.valueOf(id);
        id++;
        return currentID;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        try {
            DBConnection.insertForRegister(
                "admin_staff", idNumber(), getFname(), getLname(), 
                getPassword(), getEmail(), getTelno(), getAddress()
            );
        } catch (Exception f) {
            f.printStackTrace();
            JOptionPane.showMessageDialog(getFrame(), "Database Error: " + f.getMessage());
        }
    }
    
    // Entry point for the Dashboard (static wrapper)
    public static void startWindow() {
        new AdminDashboard();
    }
}