/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlineOrderApp;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author georgerobinson
 */
public class MenuFileHandler {
    private static final String FILE_NAME = "menu.txt"; //set file name for this doccumnet as menu.txt

    public void saveMenu(List<MenuItem> items) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) { //try creating new line in menu.txt
            for (MenuItem item : items) { 
                writer.write(item.getId() + "|"+ item.getName() + "|"+ item.getDescription() + "|"+ item.getCategory() + "|"+ item.getPrice()); //write new line and use propper formattiing
                writer.newLine();
            }
        } catch (IOException e) { //catch error
            System.out.println("Error saving menu: " + e.getMessage()); //print out error and reason for error
        }
    }

    public List<MenuItem> loadMenu() {
        List<MenuItem> items = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) { //try reading menu.txt
            String line;
            while ((line = reader.readLine()) != null) { //while not at the end of the doccument
                String[] fields = line.split("\\|"); //split the current line into different fields seperating them by |
                int id = Integer.parseInt(fields[0]); //get id from field 0
                double price = Double.parseDouble(fields[4]); //get price from field 4
                MenuItem item = new MenuItem(id, fields[1],fields[2],fields[3],price);
                items.add(item);
            }
        } catch (IOException e) { //catch error
            System.out.println("Error loading menu: " + e.getMessage()); //print error and reason for error
        }
        return items;
    }
}
