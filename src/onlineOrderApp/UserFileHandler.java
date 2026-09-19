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
public class UserFileHandler {
    private static final String FILE_NAME = "users.txt"; //set file name for users as users.txt
    
    //Generated SaveUsers with help from claude. all other code in UserFileHandler class was written manually.
    public void saveUsers(List<User> users){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))){ //try writing the following line of text to file name which is users.txt
            for (User user : users) { //for the amount of users in users
                if (user instanceof Customer) { //if current user is an instance of the customer class...
                    Customer c = (Customer) user;
                    writer.write("CUSTOMER|" + c.getUsername() + "|" + c.getPasswordForSave() + "|"
                            + c.getEmail() + "|" + c.getAddress() + "|" + c.getPhoneNumber()); //...write customer information to the users text file
                } else if (user instanceof Admin) { //else if the user is an instance of the admin calss...
                    writer.write("ADMIN|" + user.getUsername() + "|" + user.getPasswordForSave() + "|" + user.getEmail()); //write admin information to the users text file =
                }
                writer.newLine();
            }
        }catch (IOException e) { //catch an error to prevent the prgram from crashing
            System.out.println("Error saving users: " + e.getMessage()); // print error message and reason for error
        }
    }
    
    public List<User> loadUsers() {
        List<User> users = new ArrayList<>(); //create a new array list 

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) { //try reading from file name which is users.txt
            String line;
            while ((line = reader.readLine()) != null) { //while going through each line of code until there is no more lines
                String[] fields = line.split("\\|"); //split each line when | is found. put each segment ito its own field

                if (fields[0].equals("CUSTOMER")) { //if the first field is equal to customer
                    Customer customer = new Customer(fields[1],fields[2],fields[3],fields[4],fields[5],""); //create a new customer with information from fields 1-5
                    users.add(customer);
                } 
                else if (fields[0].equals("ADMIN")) { //else if the first field is equsl to admin
                    Admin admin = new Admin(fields[1],fields[2],fields[3]); // create a new admin with information from fields 1-3
                    users.add(admin);
                }
            }
        } catch (IOException e) { //catch an erorr and print out the reason of error
            System.out.println("Error loading users: " + e.getMessage());
        }

        return users;
    }
}
