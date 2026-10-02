/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlineOrderApp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author georgerobinson
 */
public class DBManager {
    private static final String URL = "jdbc:derby:db/FoodOrderDB;create=true";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void main(String[] args) {
        try {
            Connection conn = getConnection();
            System.out.println("Connected to database successfully!");
            conn.close();
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }
}