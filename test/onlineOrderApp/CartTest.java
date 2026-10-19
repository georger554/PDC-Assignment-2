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

public class CartTest{ //cart test
    private Cart cart;
    private MenuItem burger;
    private MenuItem coke;
 
    @Before
    public void setUp(){ //runs before every test so each starts with a fresh cart
        cart = new Cart();
        burger = new MenuItem(1, "Burger", "Beef patty", "Main", 12.50); //creates sample items for test 
        coke = new MenuItem(2, "Coke", "330ml can", "Drink", 3.50);
    }
    
    @Test
    public void getTotal_multipleItems_sumsLineTotals(){
        cart.addItem(burger, 2); //at two burgers to get 25
        cart.addItem(coke, 3);  //at 3 cokes to get 10.5
        assertEquals(35.50, cart.getTotal(), 0.001); //confirm that the total rice is 35.5
    }
    @Test
    public void clear_cartWithItems_emptiesCart(){
        cart.addItem(burger, 1);//add burger to cart
        cart.clear(); //clear cart 
        assertEquals(0, cart.getItems().size());//check that clear cart actually works 
    }
    
}