/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlineOrderApp;
import java.time.LocalDate;
import java.time.YearMonth;
/**
 *
 * @author georgerobinson
 */
public class Payment {
    private String cardNumber;
    private String cvc;
    private LocalDate expiryDate;
    private double amount;
    private boolean success;
    private LocalDate timestamp;
    
    public Payment(String cardNumber, String cvc, LocalDate expiryDate, double amount){ // setters for cardNumber, cvc, expirey date and amount
        this.cardNumber = cardNumber;
        this.cvc = cvc;
        this.expiryDate = expiryDate;
        this.amount = amount;
        this.timestamp = LocalDate.now();
        this.success = processPayment();
    }
    
    private boolean processPayment(){
        if(cardNumber.length() == 16 && cvc.length() == 3 && expiryDate.isAfter(LocalDate.now())){ //if all the card crediantials are valid and correct
            success = true; //set paymnet as a success
        }
        else if (cardNumber == null || cardNumber.isEmpty() || cvc == null || cvc.isEmpty()){ //if any or all of the fields are empty
            success = false; //set payment to fail
         }
        else{
            success = false; //else the user must have entered false or not enough charecters or expired card
        }
        return success;
    }
    public boolean isSuccess(){ 
        return success;
    }
    public double getAmount(){
        return amount;
    }
    public LocalDate getTimestamp(){
        return timestamp;
    }
}
