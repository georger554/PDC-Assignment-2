/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlineOrderApp;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author georgerobinson
 */
public class DBManager {
    private static final String URL = "jdbc:derby:db/FoodOrderDB;create=true";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    // checks whether a table already exists in the database
    private static boolean tableExists(Connection conn, String tableName) throws SQLException {
        DatabaseMetaData meta = conn.getMetaData();
        ResultSet rs = meta.getTables(null, null, tableName.toUpperCase(), null);
        return rs.next(); // true if the ResultSet has at least one row - thus table foun
    }

    public static void initializeDatabase() {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {

            if (!tableExists(conn, "USERS")) {
                stmt.execute("CREATE TABLE Users (" + "username VARCHAR(50) PRIMARY KEY, " + "password VARCHAR(50) NOT NULL, " + "email VARCHAR(100) NOT NULL, " + "userType VARCHAR(10) NOT NULL, " + "address VARCHAR(200), " + "phoneNumber VARCHAR(20), " + "creditCardInfo VARCHAR(20))");
                System.out.println("Users table created.");
            }
            if (!tableExists(conn, "MENUITEMS")) {
                stmt.execute("CREATE TABLE MenuItems (" + "id INT PRIMARY KEY, " + "name VARCHAR(100) NOT NULL, " + "description VARCHAR(200), " + "category VARCHAR(30), " + "price DOUBLE NOT NULL)");
                System.out.println("MenuItems table created.");
            }
            if (!tableExists(conn, "ORDERS")) {
                stmt.execute("CREATE TABLE Orders (" + "id INT PRIMARY KEY, " + "username VARCHAR(50) NOT NULL, " + "status VARCHAR(20) NOT NULL, " + "orderTime DATE NOT NULL, " + "deliveryTime DATE, " + "totalAmount DOUBLE NOT NULL, " + "FOREIGN KEY (username) REFERENCES Users(username))");
                System.out.println("Orders table created.");
            }
            if (!tableExists(conn, "ORDERITEMS")) {
                stmt.execute("CREATE TABLE OrderItems (" + "orderId INT NOT NULL, " + "menuItemId INT NOT NULL, " + "quantity INT NOT NULL, " + "FOREIGN KEY (orderId) REFERENCES Orders(id), " + "FOREIGN KEY (menuItemId) REFERENCES MenuItems(id))");
                System.out.println("OrderItems table created.");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        initializeDatabase();
    }
}