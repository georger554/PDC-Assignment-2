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
    private Map<Integer, MenuItem> menuItems; //map used so each item can be looked up fast by its id
    private MenuFileHandler fileHandler;
    
    public MenuService() {
        fileHandler = new MenuFileHandler();
        menuItems = new HashMap<>(); //Set menuItems as a new hashmap
        for (MenuItem item : fileHandler.loadMenu()) { //for each item loaded from menu.txt
            menuItems.put(item.getId(), item); //Store it in the map using its id as the key
        }
    }
    
    public void addItem(int id, String name, String description, String category, double price) {
        MenuItem item = new MenuItem(id, name, description, category, price); //make a new menu item using id, name, desc, cat, and price
        menuItems.put(id, item); //add to map using id as key
        fileHandler.saveMenu(getAllItems()); 
    }
    
    public boolean removeItem(int id) {
        if (menuItems.containsKey(id)) { //If the map has an item with this id....
            menuItems.remove(id); //remove it from the map
            fileHandler.saveMenu(getAllItems()); //save
            return true;
        }
        return false;
    }
    
    public List<MenuItem> getAllItems() {
        return new ArrayList<>(menuItems.values()); //convert the maps values back into a list so the rest of the program doesnt need to change
    }
}
 