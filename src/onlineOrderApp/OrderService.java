/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlineOrderApp;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author georgerobinson
 */
public class OrderService{
    private OrderDAO orderDAO; 
    private UserDAO userDAO; 
    private MenuDAO menuDAO;

    public OrderService(){ //constructor
        orderDAO = new OrderDAO(); 
        userDAO = new UserDAO(); 
        menuDAO = new MenuDAO();
    }

    public List<Order> getAllOrders() { //gets all orders from the database
        List<Customer> customers = new ArrayList<>(); //list to store customers

        for (User user : userDAO.getAllUsers()) { //loops through all users from db
            if (user instanceof Customer) { //if user is a customer
                customers.add((Customer) user); //makes the user a Customer and adds it to list
            }
        }

        return orderDAO.getAllOrders(customers, menuDAO.getAllMenuItems()); //gets all orders using the customers and menu items
    }

    public Order placeOrder(Customer customer, Cart cart, Payment payment) { //creates and saves a new order
        if (!payment.isSuccess()) { //payment already checked itself when it was created
            return null; //returns null if the payment was unsuccessful
        }

        int id = orderDAO.getNextOrderId(); // gets the next available order id
        Order order = new Order(id, customer, cart); //creates a new order

        if (orderDAO.insertOrder(order)) { //tries to save the order to the db
            return order; //returns the order if it was successful
        }

        return null; //returns null if not
    }

    public boolean updateOrderStatus(int orderId, OrderStatus newStatus) { //updates status of order
        Order order = null; 

        for (Order currentOrder : getAllOrders()) { //loops through all orders
            if (currentOrder.getId() == orderId) { //checks if the order id matches
                order = currentOrder; //stores the matching order
                break; 
            }
        }

        if (order == null) { //chec if no matching order was found
            return false; //returns false
        }

        order.updateStatus(newStatus); //updates the order status and handles the delivery time rule

        return orderDAO.updateOrderStatus(orderId, order.getStatus(), order.getDeliveryTime()); //saves the updated status and delivery time to the db
    }

    public List<Order> getOrdersByCustomer(Customer customer) { //gets all orders belonging to a customer
        List<Order> customerOrders = new ArrayList<>(); //creates a list to store the customers orders

        for (Order order : getAllOrders()) { //loops through all orders
            if (order.getCustomer().getUsername().equals(customer.getUsername())) { //checks if the order belongs to the customer
                customerOrders.add(order); //adds the order to the list
            }
        }

        return customerOrders; //returns the customers orders
    }

    public double getTotalRevenue() { //calculates the total from all orders
        double total = 0; //starts the total revenue at 0

        for (Order order : getAllOrders()) { //loops through all orders
            total += order.getTotalAmount(); //adds each orders total amount to the revenue
        }

        return total; //returns the total revenue
    }

    public int getOrderCountByStatus(OrderStatus status) { //counts how many orders have a specific status
        int count = 0; //starts the order count at 0

        for (Order order : getAllOrders()) { //loops through all orders
            if (order.getStatus() == status) { //checks if the orders status matches the given status
                count++; //increments the ocount
            }
        }

        return count; //returns the number of orders with the given status
    }

    public int getEstimatedWaitMinutes() { //estimates the wait for an order that is not ready yet
        int ahead = getOrderCountByStatus(OrderStatus.PENDING) + getOrderCountByStatus(OrderStatus.PREPARING); //orders still being made
        return 5 + ahead * 5; //5 minutes base + 5 for each order still in progress
    }
}