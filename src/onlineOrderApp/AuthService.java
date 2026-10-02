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
public class AuthService { //logging in/registering/deleting accounts
    private UserDAO userDAO; //priv UserDao

    public AuthService(){ //auth service constructor
        userDAO = new UserDAO();
    }
    public User login(String username, String password){
        List<User> users = userDAO.getAllUsers(); //get all user info and store in list
        for (User user : users){ //for all the users in users
            if (user.getUsername().equals(username) && user.checkPassword(password)){ //if everything matches
                return user; //the user can log in
            }
        }
        return null; //else the password/usernames are incorrect and the user cannot log in
    }

    public boolean register(String type, String username, String password, String email, String address, String phoneNumber, String creditCardInfo) {
        List<User> users = userDAO.getAllUsers(); //get all existing users from db
        for (User user : users) { //for all users in users
            if (user.getUsername().equals(username)){ //if new username equals an existing username
                return false; // false -username already taken
            }
        }

        User newUser;
        if (type.equals("ADMIN")) {
            newUser = new Admin(username, password, email); //create new admin
        } 
        else{
            newUser = new Customer(username, password, email, address, phoneNumber, creditCardInfo); //creaste new customer account
        }

        return userDAO.insertUser(newUser); //send new user to data base
    }

    public boolean deleteUser(String username) {
        return userDAO.deleteUser(username); //recieve username to be deleted and call delete user method
    }

    public List<User> getUsers(){
        return userDAO.getAllUsers(); //return list of all users
    }
}