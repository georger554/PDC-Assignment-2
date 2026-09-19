/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlineOrderApp;
import java.util.List;
import java.time.LocalDate;
/**
 *
 * @author georgerobinson
 */
public class Order {
    private int id;
    private Customer customer;
    private List<OrderLine> items;
    private OrderStatus status;
    private LocalDate orderTime;
    private LocalDate deliveryTime;
    private double totalAmount;
    
    public Order(int id, Customer customer, Cart cart){ //setters for orders
        this.id = id;
        this.customer = customer;
        this.items = cart.getItems();
        this.totalAmount = cart.getTotal();
        this.status = OrderStatus.PENDING;
        this.orderTime = LocalDate.now();
        this.deliveryTime = null;
    }
    public Order(int id, Customer customer, List<OrderLine> items, OrderStatus status,LocalDate orderTime, LocalDate deliveryTime, double totalAmount) { //setters for orders
        this.id = id;
        this.customer = customer;
        this.items = items;
        this.status = status;
        this.orderTime = orderTime;
        this.deliveryTime = deliveryTime;
        this.totalAmount = totalAmount;
    }
    
    @Override
    public String toString() {
        return "Order #" + id + " - " + customer.getUsername() + " - " + status + " - $" + totalAmount + " - Ordered: " + orderTime; //format order information into one line
    }
    
    public int getId(){ //getters
        return id;
    }
    public Customer getCustomer(){
        return customer;
    }
    public List<OrderLine> getItems(){
        return items;
    }
    public OrderStatus getStatus(){
        return status;
    }
    public LocalDate getOrderTime(){
        return orderTime;
    }
    public LocalDate getDeliveryTime(){
        return deliveryTime;
    }
    public double getTotalAmount(){
        return totalAmount;
    }
    
    public void updateStatus(OrderStatus newStatus){
        this.status = newStatus;
        if(newStatus == OrderStatus.DELIVERED){
            this.deliveryTime = LocalDate.now(); //once product has been delivered, set the delivery date to now (time when admin changed status to delivered)
        }
    }
}
