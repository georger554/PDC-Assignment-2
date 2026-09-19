/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlineOrderApp;

/**
 *
 * @author georgerobinson
 */
public class Admin extends User {
    public Admin(String username, String password, String email){ 
        super(username, password, email); //get username pass and email from user as customer extends user
    }
    
    @Override
    public void showMenuOptions(){ //options for admin. 
        System.out.println("1. Manage Menu");
        System.out.println("2. View Current Orders");
        System.out.println("3. View Stats");
        System.out.println("4. Update Order Status");
        System.out.println("5. Delete Account");
        System.out.println("6. Log Out");
    }
}
