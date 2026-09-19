/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlineOrderApp;

/**
 *
 * @author georgerobinson
 */
public class Reciept {
    private int orderId;
    private double totalAmount;
    private String paymentStatus;
    private String timestamp;

    public Reciept(int orderId, double totalAmount, String paymentStatus, String timestamp){ //setters for orderId totalAmount paymentStatus and timestamp
        this.orderId = orderId;
        this.totalAmount = totalAmount;
        this.paymentStatus = paymentStatus;
        this.timestamp = timestamp;
    }

    public int getOrderId(){  //getters
        return orderId; 
    }
    public double getTotalAmount(){ 
        return totalAmount; 
    }
    public String getPaymentStatus(){ 
        return paymentStatus;
    }
    public String getTimestamp(){ 
        return timestamp;
    }

    @Override
    public String toString() { //print order number, total price, status, and time of ready/delivery in oone line. 
        return "Receipt [Order NO.#" + orderId + ", Total: $" + totalAmount + ", Status: " + paymentStatus + ", Time: " + timestamp + "]";
    }
}
