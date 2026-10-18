/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlineOrderApp;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author georgerobinson
 */
public class OrderDAO {
    public boolean insertOrder(Order order){
        String orderSql = "INSERT INTO Orders (id, username, status, orderTime, deliveryTime, totalAmount) VALUES (?, ?, ?, ?, ?, ?)"; // sql used to insert the main order
        String itemSql = "INSERT INTO OrderItems (orderId, menuItemId, quantity) VALUES (?, ?, ?)"; //sql used to insert each item

        try (Connection conn = DBManager.getConnection()){
            conn.setAutoCommit(false);
            try (PreparedStatement orderPs = conn.prepareStatement(orderSql);
                PreparedStatement itemPs = conn.prepareStatement(itemSql)) {

                orderPs.setInt(1, order.getId()); //sets the order id
                orderPs.setString(2, order.getCustomer().getUsername()); //sets username of customer
                orderPs.setString(3, order.getStatus().name());
                orderPs.setDate(4, Date.valueOf(order.getOrderTime()));
                orderPs.setNull(5, Types.DATE);
                orderPs.setDouble(6, order.getTotalAmount()); //sets total price of the order
                orderPs.executeUpdate(); //inserts the order into the database

                for (OrderLine line : order.getItems()) { //loops through each item
                    itemPs.setInt(1, order.getId()); //sets id
                    itemPs.setInt(2, line.getMenuItem().getId()); 
                    itemPs.setInt(3, line.getQuantity()); //sets quantity 
                    itemPs.executeUpdate();
                }

                conn.commit(); //commits db changes
                return true;
            } catch (SQLException e) {
                conn.rollback(); //undo order row if anything failed
                System.out.println("Error inserting order: " + e.getMessage());
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
            return false;
        }
    }

    public int getNextOrderId() { //gets next available order id
        String sql = "SELECT MAX(id) FROM Orders"; //gets highest order id currently in db

        try (Connection conn = DBManager.getConnection(); //connects to db
             PreparedStatement ps = conn.prepareStatement(sql); 
             ResultSet rs = ps.executeQuery()) { //runs and stores the result

            rs.next(); //moves to the result row
            return rs.getInt(1) + 1; // gets the highest id and adds one for the next order
        }
        catch (SQLException e) { //Catches any db error
            System.out.println("Error getting next order id: " + e.getMessage()); 
            return 1;
        }
    }

    public boolean updateOrderStatus(int orderId, OrderStatus status, LocalDate deliveryTime){ //updates the status and delivery time
        String sql = "UPDATE Orders SET status = ?, deliveryTime = ? WHERE id = ?";

        try (Connection conn = DBManager.getConnection(); //connects to db
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status.name());

            if (deliveryTime == null){ // if the order has no delivery time
                ps.setNull(2, Types.DATE); //set delivery time to null
            }
            else {
                ps.setDate(2, Date.valueOf(deliveryTime)); //Converts to sql date and sets it
            }

            ps.setInt(3, orderId); 

            return ps.executeUpdate() > 0; //runs the update
        }
        catch (SQLException e){
            System.out.println("Error updating order status: " + e.getMessage()); //prints db error
            return false;
        }
    }

    public List<Order> getAllOrders(List<Customer> customers, List<MenuItem> menuItems){ // gets all orders from the database and rebuilds the objects
        List<Order> orders = new ArrayList<>(); //empty list to store orders
        String orderSql = "SELECT * FROM Orders"; //get all orders
        String itemSql = "SELECT * FROM OrderItems WHERE orderId = ?"; // get all order items

        try (Connection conn = DBManager.getConnection();
             PreparedStatement orderPs = conn.prepareStatement(orderSql); 
             ResultSet orderRs = orderPs.executeQuery()) { //runs and stores orders

            while (orderRs.next()){ //loops through each order
                int orderId = orderRs.getInt("id"); //gets order id
                String username = orderRs.getString("username"); //gets username belonging to the order
                OrderStatus status = OrderStatus.valueOf(orderRs.getString("status")); //converts db status to enum
                LocalDate orderTime = orderRs.getDate("orderTime").toLocalDate(); //converts sql date into a localdate

                Date deliveryDate = orderRs.getDate("deliveryTime"); //gets the delivery date
                LocalDate deliveryTime = null; //starts with no delivery time

                if (deliveryDate != null) { //checks if a delivery date exists
                    deliveryTime = deliveryDate.toLocalDate(); //converts the delivery date into a LocalDate
                }

                double totalAmount = orderRs.getDouble("totalAmount"); //gets total amount of the order

                Customer customer = null; //creates a variable to store customer

                for (Customer c : customers){ //loops through customers
                    if (c.getUsername().equals(username)){ //checks if the customer username matches the order username
                        customer = c; //stores the matching customer
                        break; //stops once customer is found
                    }
                }

                List<OrderLine> orderLines = new ArrayList<>(); //creates empty list

                try (PreparedStatement itemPs = conn.prepareStatement(itemSql)) { 
                    itemPs.setInt(1, orderId); //sets the order id used to find its items

                    try (ResultSet itemRs = itemPs.executeQuery()) { //runs and stores the order items

                        while (itemRs.next()) { //loops through each item
                            int menuItemId = itemRs.getInt("menuItemId"); //gets menu item id
                            int quantity = itemRs.getInt("quantity"); //gets quantity ordered

                            MenuItem menuItem = null;

                            for (MenuItem item : menuItems) { //loops through all menu items
                                if (item.getId() == menuItemId) { //checks if the menu item id matches
                                    menuItem = item; //stores the matching menu item
                                    break;
                                }
                            }

                            orderLines.add(new OrderLine(menuItem, quantity)); //creates order line and adds to order
                        }
                    }
                }

                Order order = new Order(orderId, customer, orderLines, status, orderTime, deliveryTime, totalAmount); //creates an order using db
                orders.add(order); //adds order to the list
            }
        }
        catch (SQLException e){ //catches database error
            System.out.println("Error loading orders: " + e.getMessage());
        }

        return orders; //returns list of orders
    }
}
