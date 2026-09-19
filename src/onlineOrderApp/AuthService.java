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
public class AuthService {
    private List<User> users;
    private UserFileHandler fileHandler;
    
    public List<User> getUsers() {
        return users;
    }
    public AuthService(){
        fileHandler = new UserFileHandler();
        users = fileHandler.loadUsers();
        
    }
    public User login(String username, String password){
        for(int i = 0;i<users.size();i++){ //for the amount of users
            if(users.get(i).getUsername().equals(username)&&users.get(i).checkPassword(password)){ //if the current user equals the saved username and teh entered password equals the saved password (meaning correct credentials) 
                return users.get(i); //return current user 
            }
            
        }
        return null; //otherwise the info is wrong and return null
    }
    public boolean register(String type, String username, String password, String email, String address, String phoneNumber, String creditCardInfo){
        for (User user : users) { //for the amount of users in users
            if (user.getUsername().equals(username)) { //if the username entered already exists in the text file
                return false; //return false - user must enter a different username
            }
        }
        if (type.equals("ADMIN")) { //if admin is chosen
            Admin admin = new Admin(username, password, email); //create new admin account
            users.add(admin);
        } else if (type.equals("CUSTOMER")) { //if cystomer is chosen
            Customer customer = new Customer(username, password, email, address, phoneNumber, ""); //create new customer account
            users.add(customer);
        }
        fileHandler.saveUsers(users); //save file
        return true;
    }
    public boolean deleteUser(String username) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) { //find account in file
                users.remove(i); //delete the info and save
                fileHandler.saveUsers(users);
                return true;
            }
        }
        return false;
    }
}
