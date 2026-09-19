/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlineOrderApp;

/**
 *
 * @author georgerobinson
 */
//Generated menuItem with help from Claude
public class MenuItem {
    private int id;
    private String name;
    private String description;
    private String category;
    private double price;

    public MenuItem(int id, String name, String description, String category, double price) { //setters
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
    }

    public int getId(){  //getters
        return id; 
    }
    public String getName(){ 
        return name; 
    }
    public String getDescription(){
        return description;
    }
    public String getCategory(){
        return category;
    }
    public double getPrice(){
        return price; 
    }

    public void setPrice(double price){ //setters
        this.price = price;
    }
    public void setDescription(String description){
        this.description = description;
    }

    @Override
    public String toString() { //when adding a new menu item to the text file, format it properly
        return id + " | " + name + " (" + category + ") - $" + price;
    }
}