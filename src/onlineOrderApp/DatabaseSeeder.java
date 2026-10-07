/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlineOrderApp;
import java.util.List;

/**
 *
 * @author georgerobinson
 */
public class DatabaseSeeder {
    public static void seedMenuIfEmpty(){//adds the default menu items if the menu table is empty
        MenuDAO menuDAO = new MenuDAO();
        List<MenuItem> existing = menuDAO.getAllMenuItems();
        if (!existing.isEmpty()) {
            return; //menu already has items so do null
        }
        menuDAO.insertMenuItem(new MenuItem(1, "Chicken+cranberry Pizza", "Cheesy cranberry sauce with hot chicken on top of homemade base", "Main", 22.0));
        menuDAO.insertMenuItem(new MenuItem(2, "Alfredo Pasta", "Creamy and saucy pasta with bacon", "Main", 24.0));
        menuDAO.insertMenuItem(new MenuItem(3, "Bolognaise", "Spaghetti bolognaise with mince and cheese with a hint of spice", "Main", 20.0));
        menuDAO.insertMenuItem(new MenuItem(4, "Vegetable Curry", "Creamy and spicy curry with roasted veggies", "Main", 21.0));
        menuDAO.insertMenuItem(new MenuItem(5, "Chicken Curry", "Creamy and spicy curry with tasty chicken", "Main", 24.0));
        menuDAO.insertMenuItem(new MenuItem(6, "Veggie Rice Paper Rolls", "Rice paper rolls with vegetables and noodles", "Main", 19.0));
        menuDAO.insertMenuItem(new MenuItem(7, "Meat Rice Paper Rolls", "Rice paper rolls with meat and noodles", "Main", 21.0));
        menuDAO.insertMenuItem(new MenuItem(8, "Meat and Veggie kebabs", "Chicken and vegetables BBQ'd on a kebab stick", "Main", 22.0));
        menuDAO.insertMenuItem(new MenuItem(9, "Hot Chips", "Crispy golden hot chips with sauce", "Side", 11.0));
        menuDAO.insertMenuItem(new MenuItem(10, "Salad", "Fresh vegetable salad with dressing", "Side", 9.0));
        menuDAO.insertMenuItem(new MenuItem(11, "Rice", "Hot steamed rice", "Side", 8.0));
        menuDAO.insertMenuItem(new MenuItem(12, "Chocolate Pudding", "Hot saucy and chocolate pudding", "Dessert", 16.0));
        menuDAO.insertMenuItem(new MenuItem(13, "Apple Pie", "Apple pie served with custard", "Dessert", 15.0));
        menuDAO.insertMenuItem(new MenuItem(14, "Ice Cream Sunday", "Homemade ice cream with sauce", "Dessert", 13.0));
        menuDAO.insertMenuItem(new MenuItem(15, "Coke classic", "Served with ice", "Drink", 8.0));
        menuDAO.insertMenuItem(new MenuItem(16, "Coke no sugar", "Served with ice", "Drink", 8.0));
        menuDAO.insertMenuItem(new MenuItem(17, "Sprite", "Served with ice", "Drink", 8.0));
        menuDAO.insertMenuItem(new MenuItem(18, "Fanta", "Served with ice", "Drink", 8.0));
        menuDAO.insertMenuItem(new MenuItem(19, "L&P", "Served with ice", "Drink", 8.0));
        menuDAO.insertMenuItem(new MenuItem(20, "Water", "Served with ice", "Drink", 8.0));
        menuDAO.insertMenuItem(new MenuItem(21, "Juice", "Served with ice", "Drink", 8.0));
    }
}
