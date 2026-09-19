/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlineOrderApp;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
/**
 *
 * @author georgerobinson
 */
public class OrderFileHandler {
    private static final String FILE_NAME = "order.txt"; //set file_name as order.txt
    
    public void saveOrders(List<Order> orders) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) { //try writing new line to order.txt
            for (Order order : orders) { //for the amount of orders in orders
                StringBuilder sb = new StringBuilder(); //make new string builder
                List<OrderLine> items = order.getItems();
                for (int i = 0; i < items.size(); i++) { //for the amount of items in items
                    OrderLine item = items.get(i); //get the current item 
                    sb.append(item.getMenuItem().getId()).append(":").append(item.getQuantity()); //get item, id, and quantiy 
                    if (i < items.size() - 1) { //if not the final item
                        sb.append(","); //seperate with comma to format file correctly
                    }
                }
                String deliveryTimeStr = (order.getDeliveryTime() == null) ? "NONE" : order.getDeliveryTime().toString(); // if getDeliveryTime is null, set NONE to delivery time. if not null, set the delivery time as getDeliveryTime
                writer.write(order.getId() + "|" + order.getCustomer().getUsername() + "|" + order.getStatus() + "|" + order.getOrderTime() + "|" + deliveryTimeStr + "|" + order.getTotalAmount() + "|" + sb.toString()); //write info and seperate it by |
                writer.newLine();
            }
        } catch (IOException e) { //catch error 
            System.out.println("Error saving orders: " + e.getMessage());//print error and reason for error
        }
    }
    
    public List<Order> loadOrders(List<Customer> customers, List<MenuItem> menuItems) {
        List<Order> orders = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) { //try reading order.txt
            String line;
            while ((line = reader.readLine()) != null) { //while not at end of file
                String[] fields = line.split("\\|", -1); //split the line into different fields using |
                int id = Integer.parseInt(fields[0]); //set id as field 0
                String username = fields[1]; //set username
                OrderStatus status = OrderStatus.valueOf(fields[2]); //set status
                LocalDate orderTime = LocalDate.parse(fields[3]); //set order date
                LocalDate deliveryTime = fields[4].equals("NONE") ? null : LocalDate.parse(fields[4]); //set delivery time as field 4. However if it equals none, then set as null
                double totalAmount = Double.parseDouble(fields[5]); //total amoutn as field 5
                Customer customer = null; //set cust as null
                for (Customer c : customers) { //for the amount of cusotmers
                    if (c.getUsername().equals(username)) { //if the customer username equals current username
                        customer = c; //set the custoemr as the current
                        break;
                    }
                }
                List<OrderLine> items = new ArrayList<>();
                if (!fields[6].isEmpty()) { //if field 6 is not empty =
                    String[] itemPairs = fields[6].split(","); //split the item pairs using comma
                    for (String pair : itemPairs) { //for the amount of pairs in item pairs
                        String[] parts = pair.split(":"); //split parts using colon
                        int menuItemId = Integer.parseInt(parts[0]); // set menu item as the value from parts[0]
                        int quantity = Integer.parseInt(parts[1]); // set quantity as value from parts[1]
                        MenuItem matchingMenuItem = null;
                        for (MenuItem item : menuItems) { //for the amount of items in menu items
                            if (item.getId() == menuItemId) { //of the id equals menu item id
                                matchingMenuItem = item; //set matching menu item as item
                                break;
                            }
                        }
                        if (matchingMenuItem != null) { //if the matchingMenuItem is not null
                            items.add(new OrderLine(matchingMenuItem, quantity)); //add new orderline using matching menu item and quantity
                        }
                    }
                }
                Order order = new Order(id, customer, items, status, orderTime, deliveryTime, totalAmount);
                orders.add(order);
            }
        } catch (IOException e) { //catch error
            System.out.println("Error loading orders: " + e.getMessage()); //print error and reason for error
        }
        return orders;
    }
}