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
public class OrderService {
    private List<Order> orders;
    private OrderFileHandler fileHandler;
    private int nextOrderId;

    public OrderService(List<Customer> customers, List<MenuItem> menuItems) {
        fileHandler = new OrderFileHandler();
        orders = fileHandler.loadOrders(customers, menuItems);
        
        int maxId = 0; //set max id as zero 
        for (int i = 0; i < orders.size(); i++) { // for the amount of orders 
            if (orders.get(i).getId() > maxId) { //if the current order id is greater than the max id
                maxId = orders.get(i).getId(); //set the maxid as current order id
            }
        }
        nextOrderId = maxId + 1; //next order id will be max+1
    }

    public Order placeOrder(Customer customer, Cart cart, Payment payment) { 
        if(payment.isSuccess()){ //if the payment was set to success
            Order order = new Order(nextOrderId, customer, cart); //create a new order using next order id, customer, and cart 
                orders.add(order);//add order to orders
                nextOrderId+=1; //increment next order id
                fileHandler.saveOrders(orders); //save
                return order;
        }
        else{ // if the payment failed
            return null; //return nothing
        }
    }

    public boolean updateOrderStatus(int orderId, OrderStatus newStatus) {
        for (int i = 0; i < orders.size(); i++) { //for the amount of orders 
            if (orders.get(i).getId() == orderId) { //once found desired order
                orders.get(i).updateStatus(newStatus); //set the status
                fileHandler.saveOrders(orders);
                return true;
            }
        }
        return false;
    }

    public List<Order> getOrdersByCustomer(Customer customer) {
        List<Order> result = new ArrayList<>();
        for (Order order : orders) { //for the amount of orders in orders
            if (order.getCustomer().getUsername().equals(customer.getUsername())) { //if the username of cust equals username of order
                result.add(order); //add order
            }
        }
        return result;
    }

    public List<Order> getAllOrders() {
        return orders;
    }

    public double getTotalRevenue() {
        double total = 0;
        for(Order order : orders){ //for the amount of orders
             total += order.getTotalAmount(); //add total amount to overall total
        }
        return total;
    }

    public int getOrderCountByStatus(OrderStatus status) {
        int count = 0; //set count to zero
        for(Order order : orders){ //for the amount of orders
            if (order.getStatus() == status){ //if the order status equals status
                count++; //increment count od orders
            }
        }
        return count;
    }
    
    public int getEstimatedWaitMinutes(Order order) {
        int count = 0; //set count to zero
        for (Order o : orders) { //for the amount of orders 
            if (o.getStatus() == OrderStatus.PENDING || o.getStatus() == OrderStatus.PREPARING) { //if the order has a status of pending or preparing
                count++; //increment count
            }
        }
        return 5 + (count * 5); //each order adds five minutes to wait time. so 5* count and an additional 5 mins for base wait time.
    }
}
