/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlineOrderApp;

/**
 *
 * @author georgerobinson
 */
public abstract class User {
    private String username; 
    private String password;
    private String email;
    
    public User(String username, String password, String email){ //setters for private strings
        this.username = username;
        this.password = password;
        this.email = email;
    }
    
    public String getUsername(){ // getters for user name, email, password for save
        return username;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getPasswordForSave(){
        return password;
    }
    public boolean checkPassword(String attempt){ //return true or false if set passwords equals attempt sign in
        return this.password.equals(attempt);
    }
    
    public abstract void showMenuOptions(); //abstract method 
}
