/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlineOrderApp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author georgerobinson
 */
public class UserDAO { //data access object class - getting addding a d deleting user data from the database

    public List<User> getAllUsers() { //get all users returns a list containg all user objects
        List<User> users = new ArrayList<>(); //new empty list to store info from database
        String sql = "SELECT * FROM Users"; //selects everything from database

        try (Connection conn = DBManager.getConnection(); //try connecting to database and create a prepared statment and store the results in resultset
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()){ //while another row exists move to next row
                String userType = rs.getString("userType"); //get values from database
                String username = rs.getString("username");
                String password = rs.getString("password");
                String email = rs.getString("email");

                if (userType.equals("CUSTOMER")){ //if its a user
                    String address = rs.getString("address"); //get more info
                    String phoneNumber = rs.getString("phoneNumber");
                    String creditCardInfo = rs.getString("creditCardInfo");
                    users.add(new Customer(username, password, email, address, phoneNumber, creditCardInfo)); //create a new customer and add it to the list
                }
                else if (userType.equals("ADMIN")) { //else if its a admin
                    users.add(new Admin(username, password, email)); //add admin to the list
                }
            }
        }
        catch (SQLException e){ //catch any errors
            System.out.println("Error loading users: " + e.getMessage());
        }
        return users;
    }

    public boolean insertUser(User user){ //insert user inserts a new user object 
        String sql = "INSERT INTO Users (username, password, email, userType, address, phoneNumber, creditCardInfo) VALUES (?, ?, ?, ?, ?, ?, ?)"; //sql insert statment. ? are placeholders for info that will be added

        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getUsername()); //first ? is username
            ps.setString(2, user.getPasswordForSave()); // second question mark is for password
            ps.setString(3, user.getEmail()); //and third is for email

            if (user instanceof Customer){//if the user is a customer
                Customer c = (Customer) user;
                ps.setString(4, "CUSTOMER"); //user type is set to customer
                ps.setString(5, c.getAddress()); // 5th ? is address
                ps.setString(6, c.getPhoneNumber()); //and 6th is pn
            }
            else{
                ps.setString(4, "ADMIN");//else the user must be a admin thus set usertype to admin 
                ps.setNull(5, java.sql.Types.VARCHAR); //set 5 and 6 to null as no further info needed for admin
                ps.setNull(6, java.sql.Types.VARCHAR);
            }
            ps.setNull(7, java.sql.Types.VARCHAR); // creditCardInfo intentionally never stored

            ps.executeUpdate();
            return true;
        }
        catch (SQLException e) {
            System.out.println("error inserting user " + e.getMessage());
            return false;
        }
    }

    public boolean deleteUser(String username){
        String sql = "DELETE FROM Users WHERE username = ?"; //sql delete statment

        try (Connection conn = DBManager.getConnection(); //try connecting to database
             PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setString(1, username); //fist abd only question mark is set to userame
            int rowsAffected = ps.executeUpdate(); //delete the row and return the number of rows affected
            return rowsAffected > 0;
        }
        catch (SQLException e){
            System.out.println("Error deleting user " + e.getMessage());
            return false;
        }
    }
}
