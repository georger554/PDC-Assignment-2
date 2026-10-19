/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlineOrderApp;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


/**
 *
 * @author georgerobinson
 */
public class OrderTest{
    
    private Customer customer; //vars to create orders
    private Cart cart;

    @Before
    public void setUp(){ 
        customer = new Customer("tim", "pw", "t@x.com", "1 Street", "0211234567", "");//create new cust with this info 
        cart = new Cart(); //new cart for the cusotmer 
        
        cart.addItem(new MenuItem(1, "Burger", "description", "Main", 12.50), 2);  //add two burgers to cart
        cart.addItem(new MenuItem(2, "Coke", "description", "Drink", 3.50), 1);     //add coke to cart 
    }
    @Test
    public void constructor_newOrderFromCart_startsPendingWithNoDeliveryTime(){
        Order order = new Order(1, customer, cart); //create new order
        assertEquals(OrderStatus.PENDING, order.getStatus()); //check that the orginal status is pending
        assertNull(order.getDeliveryTime()); //check delivery time
        
    }

    @Test
    public void constructor_cartClearedAfterwards_orderKeepsItsItems() {
        Order order = new Order(1, customer, cart); //create order
        cart.clear();  //clear the cart after creating order
        assertEquals(2, order.getItems().size());//check to see if the cart is actually empty after clearing
    }

    @Test
    public void updateStatus_toDelivered_setsDeliveryTime(){
        Order order = new Order(1, customer, cart);
        order.updateStatus(OrderStatus.DELIVERED);
        assertEquals(OrderStatus.DELIVERED, order.getStatus());
        assertNotNull(order.getDeliveryTime());
        
    }
}



