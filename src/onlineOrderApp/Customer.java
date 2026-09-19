/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlineOrderApp;

/**
 *
 * @author georgerobinson
 */
public class Customer extends User {
    private String address;
    private String phoneNumber;
    private String creditCardInfo;
    
    public Customer(String username, String password, String email, String address, String phoneNumber, String creditCardInfo) {
        super(username, password, email); //get username pass and email from user as customer extends user
        
        this.address = address; //setters for user info
        this.phoneNumber = phoneNumber; 
        this.creditCardInfo = creditCardInfo;
    }
    public String getAddress(){ //getters
        return address;
    }
    public String getPhoneNumber(){
        return phoneNumber;
    }
    @Override
    public void showMenuOptions(){ //options for customer
        System.out.println("1. Browse Menu");
        System.out.println("2. View Cart");
        System.out.println("3. Place Order");
        System.out.println("4. View Order History");
        System.out.println("5. Clear Cart");
        System.out.println("6. Delete Account");
        System.out.println("7. Log out");
    }
}
