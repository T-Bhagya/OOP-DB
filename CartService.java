import java.sql.*;
import java.time.LocalDate;
import javax.swing.JOptionPane;

public class CartService {

    // Simple ID generator
    private static String generateID(String prefix) {
        return prefix + System.currentTimeMillis() / 1000;
    }

    public static void addToCart(String customerId, String isbn, int quantity) {
        Connection con = null;
        try {
            con = DBConnection.getConnection(); 
            
            // 1. Check if user already has an active cart
            String cartId = null;
            String checkCart = "SELECT Cart_id FROM Cart WHERE Customer_id = ? AND Cart_Status = FALSE";
            PreparedStatement pstCheck = con.prepareStatement(checkCart);
            pstCheck.setString(1, customerId);
            ResultSet rs = pstCheck.executeQuery();

            if (rs.next()) {
                cartId = rs.getString("Cart_id");
            } else {
                // 2. No Cart? Create Delivery + Cart
                String delId = generateID("DEL");
                String insertDel = "INSERT INTO Delivery (Del_id, Del_Status, Del_Date) VALUES (?, FALSE, NULL)";
                PreparedStatement pstDel = con.prepareStatement(insertDel);
                pstDel.setString(1, delId);
                pstDel.executeUpdate();

                cartId = generateID("CRT");
                String insertCart = "INSERT INTO Cart (Cart_id, Cart_Status, No_of_Items, Del_id, Customer_id) VALUES (?, FALSE, 0, ?, ?)";
                PreparedStatement pstCart = con.prepareStatement(insertCart);
                pstCart.setString(1, cartId);
                pstCart.setString(2, delId);
                pstCart.setString(3, customerId);
                pstCart.executeUpdate();
            }

            // 3. Add Item to Order_Table
            String orderId = generateID("ORD");
            String insertOrder = "INSERT INTO Order_Table (Order_id, Cart_id, ISBN, Date_of_order, Qty) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstOrder = con.prepareStatement(insertOrder);
            pstOrder.setString(1, orderId);
            pstOrder.setString(2, cartId);
            pstOrder.setString(3, isbn);
            pstOrder.setDate(4, Date.valueOf(LocalDate.now()));
            pstOrder.setInt(5, quantity);
            pstOrder.executeUpdate();

            // 4. Update Item Count
            String updateCount = "UPDATE Cart SET No_of_Items = No_of_Items + 1 WHERE Cart_id = ?";
            PreparedStatement pstCount = con.prepareStatement(updateCount);
            pstCount.setString(1, cartId);
            pstCount.executeUpdate();

            JOptionPane.showMessageDialog(null, "Added to Cart successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
}