/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlineOrderApp.gui;
import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import onlineOrderApp.*;

/**
 *
 * @author georgerobinson
 */

// Generated with assistance from Claude
public class MainFrame extends JFrame {
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cards = new JPanel(cardLayout); //holds every screen, only one shows at a time

    private final AuthService authService = new AuthService();
    private final MenuService menuService = new MenuService();
    private final OrderService orderService = new OrderService();
    private User currentUser; //null when nobody is logged in
    
    private CustomerPanel customerPanel;
    // Generated with assistance from Claude
    private void addCentred(JPanel panel, String name) { //wraps a screen so it stays centred when the window resizes
        JPanel wrapper = new JPanel(new java.awt.GridBagLayout());
        wrapper.add(panel);
        cards.add(wrapper, name);
    }

    public MainFrame() {
        setTitle("Food Ordering System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        add(cards);

        addCentred(new LoginPanel(this), "login");
        addCentred(new RegisterPanel(this), "register");

        customerPanel = new CustomerPanel(this);
        cards.add(customerPanel, "customer");

        showScreen("login");
    }

    public void showScreen(String name) {
        cardLayout.show(cards, name);
    }
    public void openCustomerScreen() {
        customerPanel.onShow();
        showScreen("customer");
    }

    public AuthService getAuthService() { return authService; }
    public MenuService getMenuService() { return menuService; }
    public OrderService getOrderService() { return orderService; }
    public User getCurrentUser() { return currentUser; }
    public void setCurrentUser(User user) { this.currentUser = user; }
}