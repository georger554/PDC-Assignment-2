/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlineOrderApp;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author georgerobinson
 */

public class UserTest {
    private Customer customer;

    @Before
    public void setUp() {
        customer = new Customer("tim", "secret", "tim@test.com", "1 example drive", "0211234567", ""); //set new customer
    }

    @Test
    public void checkPassword_correctPassword_returnsTrue() {
        assertTrue(customer.checkPassword("secret")); //check to see if password is true as it should be
    }

    @Test
    public void checkPassword_wrongPassword_returnsFalse() {
        assertFalse(customer.checkPassword("wrong")); //checl to see if password returns false as it should
    }
}
