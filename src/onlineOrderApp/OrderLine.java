/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlineOrderApp;

/**
 *
 * @author georgerobinson
 */
public class OrderLine {
    private MenuItem menuItem;
    private int quantity;

    public OrderLine(MenuItem menuItem, int quantity){ //setters for menuItem adn quantity
        this.menuItem = menuItem;
        this.quantity = quantity;
    }

    public MenuItem getMenuItem(){  //getters =
        return menuItem; 
    }
    public int getQuantity(){ 
        return quantity; 
    }
    public void setQuantity(int quantity){ 
        this.quantity = quantity; 
    }

    public double getLineTotal(){
        return menuItem.getPrice() * quantity;
    }

    @Override
    public String toString(){ //print out quantity of item and total price. format the line properly
        return quantity + "x " + menuItem.getName() + " = $" + getLineTotal();
    }
}
