/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlineOrderApp;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
/**
 *
 * @author georgerobinson
 */
public class Main {
    public static void main(String[] args) {
        AuthService authService = new AuthService();
        MenuService menuService = new MenuService();

        List<Customer> customers = new ArrayList<>();
        for (User user : authService.getUsers()) {
            if (user instanceof Customer) {
                customers.add((Customer) user);
            }
        }

        OrderService orderService = new OrderService(customers, menuService.getAllItems());
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Food Ordering System"); //welcome message for start of program

        boolean running = true; //running to true
        User loggedInUser = null; //user not logged in at this stage

        while (running) { //while running is true
            System.out.println("\n1. Login  2. Register  3. Exit"); //print options
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            if (choice.equals("1")) { //if user chooses first option
                System.out.print("Username: "); //ask for username and password
                String username = scanner.nextLine();
                System.out.print("Password: ");
                String password = scanner.nextLine();

                loggedInUser = authService.login(username, password); //give typed username and password to authService

                if (loggedInUser != null) { //if authService returns success (meaning not null)
                    System.out.println("Login successful. Welcome, " + loggedInUser.getUsername() + "!"); //welcome user with personalised welcome message
                    if (loggedInUser instanceof Customer) { // if the user is a customer
                        customerMenu((Customer) loggedInUser, menuService, orderService, authService, scanner); //show customer screen
                    }
                    else if (loggedInUser instanceof Admin) {
                        adminMenu((Admin) loggedInUser, menuService, orderService, authService, scanner); //else if user is a admin, show admin screen
                    }
                } 
                else {
                    System.out.println("Invalid username or password."); //else they entered wrong info 
                }

            } 
            else if (choice.equals("2")) { //if they chose to make a new account
                System.out.print("Register as (CUSTOMER/ADMIN): "); //ask if they want to register as a custoemr or administrator
                String type = scanner.nextLine().toUpperCase();
                System.out.print("Username: "); //ask for new username and passwor 
                String username = scanner.nextLine();
                System.out.print("Password: ");
                String password = scanner.nextLine();

                System.out.print("Email: "); //get email address
                String email = scanner.nextLine();
                while (!email.contains("@")) {
                    System.out.print("Invalid email, must contain '@'. Try again: "); //must contain an @ 
                    email = scanner.nextLine();
                }

                String address = ""; //set address and phone as empty
                String phoneNumber = "";
                if (type.equals("CUSTOMER")) { //if they sign up as a cust, ask for additional info
                    System.out.print("Address: ");
                    address = scanner.nextLine();

                    System.out.print("Phone number: ");
                    phoneNumber = scanner.nextLine();
                    while (!phoneNumber.matches("[0-9]+")) {
                        System.out.print("Phone number must contain only digits. Try again: "); //must only contain numbers for valid phone number.
                        phoneNumber = scanner.nextLine();
                    }
                }

                boolean success = authService.register(type, username, password, email, address, phoneNumber, ""); //set success if all valid entries
                if (success) {
                    System.out.println("Registration successful! Please log in."); //if success is true then tell user
                } else {
                    System.out.println("Username already taken."); //else they will need to recreate account with valid entries
                }
            }
            else if (choice.equals("3")) { //if they choose to exit program
                break; //break
            }
        }
        
    }
    private static void customerMenu(Customer customer, MenuService menuService, OrderService orderService, AuthService authService, Scanner scanner) {
        Cart cart = new Cart(); // create new cart 
        boolean loggedIn = true; //set logged in as true

        while (loggedIn) { //while the cust is logged in
            customer.showMenuOptions(); //show options
            String choice = scanner.nextLine(); //get what user wants

            if (choice.equals("1")) { // if they want to view/order from menu
                boolean browsing = true; //set browsing ot true
                while (browsing) {
                    for (MenuItem item : menuService.getAllItems()) {
                        System.out.println(item); //print all items
                    }
                    System.out.print("Enter item ID to add to cart (Type 'exit' to stop once done): "); //get user to type id of item they want to add to cart
                    String input = scanner.nextLine();

                    if (input.equalsIgnoreCase("exit")) { //if they type exit then stop loop by setting browsing to false
                        browsing = false;
                    } 
                    else{
                        try { //else try
                            int itemId = Integer.parseInt(input); //setting item id from input
                            MenuItem selected = null; //set selected to null
                            for (MenuItem item : menuService.getAllItems()) { // for each all items
                                if (item.getId() == itemId) {//once found desired item in itemID
                                    selected = item; //set the selected to current item
                                }
                            }
                            if (selected != null){ //if an item id is selected (ie not null)
                                System.out.print("Quantity: ");
                                int quantity = Integer.parseInt(scanner.nextLine());
                                cart.addItem(selected, quantity); //get quantity 
                                System.out.println("Added to cart.");
                            } 
                            else {
                                System.out.println("Item not found."); //else item doesnt exist
                            }
                        }
                        catch (NumberFormatException e) {
                            System.out.println("Please enter a valid item ID or 'exit'."); //catch error and tell user to enter id or exit
                        }
                    }
                }
            }
            else if (choice.equals("2")) { //if user wants to view cart
                if (cart.getItems().isEmpty()) { //if empty tell user
                    System.out.println("Your cart is empty.");
                } 
                else{
                    for (OrderLine line : cart.getItems()) {  //else print all items from cart
                        System.out.println(line);
                    }
                    System.out.println("Total: $" + cart.getTotal()); //print total
                }
            } 
            else if (choice.equals("3")) { //if thhe user wants to pay
                System.out.print("Enter card number (16 numbers): ");
                String cardNumber = scanner.nextLine();
                while (cardNumber.length() != 16) { //if entry is not 16 digits 
                    System.out.print("Card number must be exactly 16 digits. Try again: "); //asl again
                    cardNumber = scanner.nextLine();
                }

                System.out.print("Enter CVC (3 numbers): "); 
                String cvc = scanner.nextLine();
                while (cvc.length() != 3) { //if entry is not 3 didgits
                    System.out.print("CVC must be exactly 3 digits. Try again: "); // ask again
                    cvc = scanner.nextLine();
                }

                System.out.print("Enter expiry year: ");
                int year = 0;
                boolean validYear = false;
                while (!validYear) {
                    try {
                        year = Integer.parseInt(scanner.nextLine());
                        validYear = true;
                    } catch (NumberFormatException e) {
                        System.out.print("Please enter a valid year (numbers only): ");
                    }
                }

                System.out.print("Enter expiry month: ");
                int month = 0;
                boolean validMonth = false;
                while (!validMonth) {
                    try {
                        month = Integer.parseInt(scanner.nextLine());
                        if (month < 1 || month > 12) {
                            System.out.print("Invalid month, must be 1-12. Try again: ");
                        } else {
                            validMonth = true;
                        }
                    } catch (NumberFormatException e) {
                        System.out.print("Please enter a valid month (numbers only): ");
                    }
                }

                LocalDate expiryDate = LocalDate.of(year, month, 1);//set expiry date

                Payment payment = new Payment(cardNumber,cvc,expiryDate,cart.getTotal()); //create new payment using above info

                Order order = orderService.placeOrder(customer, cart, payment); //place order using cust, cart and payment

                if (order != null) { //if oreder exists 
                    System.out.println("Order placed successfully!"); //success and clear cart
                    cart.clear();
                } 
                else {
                    System.out.println("Payment failed."); //else error
                }

            } 
            else if (choice.equals("4")) { //if user wants to see past/current orders
                List<Order> myOrders = orderService.getOrdersByCustomer(customer);
                if (myOrders.isEmpty()) { //if empty, tell user
                    System.out.println("You have no orders yet.");
                } 
                else { //else if there are orders
                    for (Order order : myOrders) { //for orders in orders
                        System.out.println("Order #" + order.getId() + " - Status: " + order.getStatus()); // print each order id and status
                        System.out.println("  Ordered: " + order.getOrderTime()); //print order time
                        if (order.getStatus() == OrderStatus.DELIVERED) {
                            System.out.println("  Delivered: " + order.getDeliveryTime()); //print delivery time if already delivered
                        }
                        else if (order.getStatus() == OrderStatus.READY) {
                            System.out.println("  Ready for pickup/delivery now."); //print line if ready
                        }
                        else {
                            int wait = orderService.getEstimatedWaitMinutes(order);
                            System.out.println("  Estimated wait until ready: " + wait + " minutes"); //else if not ready or delivered, print estimated waiting time
                        }
                        System.out.println("  Total: $" + order.getTotalAmount()); //print total 
                    }
                }
            }
            else if (choice.equals("5")) { //if user wants to clear cart
                if (cart.getItems().isEmpty()){
                    System.out.println("Your cart is already empty.");
                }
                else{
                    cart.clear(); //clear
                    System.out.println("Cart cleared.");
                }
            }
            else if (choice.equals("6")) { //if user wants to delete account
                System.out.print("Are you sure you want to delete your account? (yes/no): ");
                String confirm = scanner.nextLine(); //confirm
                if (confirm.equalsIgnoreCase("yes")) {
                    authService.deleteUser(customer.getUsername());
                    System.out.println("Account deleted."); //if yes delete
                    loggedIn = false; //log out
                }
            } 
            else if (choice.equals("7")) { //if wants to log out 
                loggedIn = false; //set log in state to false
            }
            else {
                System.out.println("Invalid option.");
            }
        }
    }

    private static void adminMenu(Admin admin, MenuService menuService, OrderService orderService, AuthService authService, Scanner scanner) {
        boolean loggedIn = true; //set logged in to true

        while (loggedIn) { //while admin is logged in 
            admin.showMenuOptions(); //print options for admin
            String choice = scanner.nextLine(); //get what admin typed

            if (choice.equals("1")){ //if the admin wants to edit menu
                System.out.println("1. Add Item");
                System.out.println("2. Remove Item");
                System.out.print("Choose an option: ");
                String menuChoice = scanner.nextLine(); //ask for add or remove item

                if (menuChoice.equals("1")){ //if they want to add 
                    
                    System.out.print("Enter ID: ");
                    int id = 0;
                    boolean validId = false;
                    while (!validId) {
                        try {
                            id = Integer.parseInt(scanner.nextLine());
                            validId = true;
                        } catch (NumberFormatException e) {
                            System.out.print("Please enter a valid numeric ID: ");
                        }
                    }

                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter description: ");
                    String description = scanner.nextLine();

                    System.out.print("Enter category: ");
                    String category = scanner.nextLine();

                    System.out.print("Enter price: ");
                    double price = 0;
                    boolean validPrice = false;
                    while (!validPrice) {
                        try {
                            price = Double.parseDouble(scanner.nextLine());
                            validPrice = true;
                        } catch (NumberFormatException e) {
                            System.out.print("Please enter a valid price (numbers only): ");
                        }
                    }

                    boolean added = menuService.addItem(id, name, description, category, price);
                    System.out.println(added ? "Menu item added successfully" : "Could not add item (ID may exist already )");

                }
                else if (menuChoice.equals("2")){ //else if they watn to remove
                    System.out.print("Enter ID of item to remove: ");
                    int id = 0;
                    boolean validId = false;
                    while (!validId) {
                        try {
                            id = Integer.parseInt(scanner.nextLine());
                            validId = true;
                        } catch (NumberFormatException e) {
                            System.out.print("Please enter a valid numeric ID: ");
                        }
                    }
                    boolean removed = menuService.removeItem(id);
                    System.out.println(removed ? "Menu item removed successfully." : "could not remove item (not found or its part of an existing order)");

                } 
                else {
                    System.out.println("Invalid option.");
                }

            } 
            else if (choice.equals("2")) { //else if they want to view all orders
                for (Order order : orderService.getAllOrders()) {
                    System.out.println(order); //print orders
                }

            }
            else if (choice.equals("3")){ //if the admin wants to view stats
                System.out.println("Total Revenue: $" + orderService.getTotalRevenue()); //print total revenue 

                System.out.println("Order Count by Status:");

                for (OrderStatus status : OrderStatus.values()) { //print orders by status
                    System.out.println(status + ": " +orderService.getOrderCountByStatus(status));
                }

            }
            else if (choice.equals("4")){ //if the admin wants to change order status of a particular order
                System.out.print("Enter order ID: ");
                try { //try find order
                    int orderId = Integer.parseInt(scanner.nextLine());
                    System.out.print("New status (PENDING/PREPARING/READY/DELIVERED): ");
                    String statusInput = scanner.nextLine().toUpperCase();
                    OrderStatus newStatus = OrderStatus.valueOf(statusInput); //set the new status
                    boolean updated = orderService.updateOrderStatus(orderId, newStatus);
                    System.out.println(updated ? "Order status updated." : "Order not found."); //if updated print success message if not print order not found
                }
                catch (NumberFormatException e){
                    System.out.println("Invalid order ID entered.");
                }
                catch (IllegalArgumentException e){
                    System.out.println("Invalid status entered.");
                }
            }
            else if (choice.equals("5")) { //if admin wants to delete account
                System.out.print("Are you sure you want to delete your account? (yes/no): ");
                String confirm = scanner.nextLine();
                if (confirm.equalsIgnoreCase("yes")) {
                    authService.deleteUser(admin.getUsername()); //delete and then log out
                    System.out.println("Account deleted.");
                    loggedIn = false;
                }
            } 
            else if (choice.equals("6")) { //if the user wants to log out
                loggedIn = false; //set logged in state to false
            }
            
            else {
                System.out.println("Invalid option.");
            }
        }
    }
    
}