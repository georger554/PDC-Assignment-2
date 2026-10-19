/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlineOrderApp;


import static org.junit.Assert.*;
import java.time.LocalDate;
import org.junit.Test;

/**
 *
 * @author georgerobinson
 */
public class PaymentTest{
    private static final String VALID_CARD = "1234567890123456"; //test 16 digit valid card number 
    private static final String VALID_CVC = "123"; // 3 digit valid test cvc number 

    @Test
    public void constructor_validCardAndFutureExpiry_paymentSucceeds(){
        Payment payment = new Payment(VALID_CARD, VALID_CVC, LocalDate.now().plusYears(2), 25.00); //create a new payment using vlaid number with expirey date of +2 years (valid)
        assertTrue(payment.isSuccess());//checks to see if paymnet went through
        
    }
    @Test
    public void constructor_cardNumberTooShort_paymentFails(){
        Payment payment = new Payment("1234567890123", VALID_CVC, LocalDate.now().plusYears(2), 25.00); //creates payment using unvalid card number (too short)
        assertFalse(payment.isSuccess()); //check to see if paymnet faild as card no too short 
       
        
    }

    @Test
    public void constructor_expiredCard_paymentFails(){
        Payment payment = new Payment(VALID_CARD, VALID_CVC, LocalDate.now().minusYears(1), 25.00); //create paymnet using valid card number but expirey date that is last year
        assertFalse(payment.isSuccess()); //check to see if payment failed due to expired card 
    }
    
}

