/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlineOrderApp;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
/**
 *
 * @author georgerobinson
 */
public class MenuService {
    private MenuDAO menuDAO;
    
    public MenuService(){ //constructor for menuService
        menuDAO = new MenuDAO();
    }
    public boolean addItem(int id, String name, String description, String category, double price){ //adds a new menu item
        MenuItem item = new MenuItem(id, name, description, category, price); //makes a new menuitem
        return menuDAO.insertMenuItem(item); // sends new item to DAO 
    }
    public boolean removeItem(int id) { //Removes item usig id
        return menuDAO.deleteMenuItem(id);
    }
    public List<MenuItem> getAllItems() { //get all menu items
        return menuDAO.getAllMenuItems(); //get full list of items
    }
}