/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlineOrderApp;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author georgerobinson
 */
public class Cart {
    private List<OrderLine> items;
    
    public Cart(){
        items = new ArrayList<>(); //set items as new array list
    }
    public void addItem(MenuItem menuItem, int quantity){ //additem func add a new item adding the menu item and quantity
        items.add(new OrderLine(menuItem, quantity));
    }
    public void removeItem(OrderLine line){ //remove item removes line 
        items.remove(line);
    }
    public double getTotal(){
        double total = 0;
        for(OrderLine line : items){ //for each item, add the price to the total. once cycled through all lines, total is caluclated
            total += line.getLineTotal();
        }
        return total;
    }
    public List<OrderLine> getItems(){ 
        return items;
    }
    public void clear(){
        items.clear();
    }
}
