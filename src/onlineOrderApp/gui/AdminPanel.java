/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlineOrderApp.gui;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import onlineOrderApp.MenuItem;
import onlineOrderApp.Order;
import onlineOrderApp.OrderStatus;
/**
 *
 * @author georgerobinson
 */
public class AdminPanel extends JPanel{
    private final MainFrame frame; //creates main frame
    private final JLabel welcomeLabel = new JLabel(); //make label for welcome
    private final JButton logoutButton = new JButton("Log Out"); //log out button positioned top right in header
    private final JButton deleteAccountButton = new JButton("Delete Account"); //delete account positioned top right in header 

    //orders -
    private final DefaultTableModel ordersModel = createReadOnlyModel(new String[]{"Order #", "Customer", "Status", "Ordered", "Total"}); //new table to display orders
    private final JTable ordersTable = new JTable(ordersModel); //orders info for table
    private final JComboBox<OrderStatus> statusBox = new JComboBox<>(OrderStatus.values()); //combo box for changing status of order
    private final JButton updateStatusButton = new JButton("Update Status"); //update status button 
    private final JButton refreshOrdersButton = new JButton("Refresh"); //refresh table button

    //menu-
    private final DefaultTableModel menuModel = createReadOnlyModel(new String[]{"ID", "Name", "Description", "Category", "Price"}); //menu tab for table
    private final JTable menuTable = new JTable(menuModel); 
    private final JTextField idField = new JTextField(15);//entries for new item - id, name, field, description, category, price
    private final JTextField nameField = new JTextField(15);
    private final JTextField descriptionField = new JTextField(15);
    private final JTextField categoryField = new JTextField(15);
    private final JTextField priceField = new JTextField(15);
    private final JButton addItemButton = new JButton("Add Item"); //add item button to add to table
    private final JButton removeItemButton = new JButton("Remove Selected Item"); //remove specific selected items

    //stats -
    private final JTextArea statsArea = new JTextArea(); //display stats and refresh button
    private final JButton refreshStatsButton = new JButton("Refresh");

    public AdminPanel(MainFrame frame){
        this.frame = frame;
        setLayout(new BorderLayout());
        add(buildHeader(), BorderLayout.NORTH); //header to be anchored at the top of the screen

        JTabbedPane tabs = new JTabbedPane(); //tabbed window to display orders menu and stats table
        tabs.addTab("Orders", buildOrdersTab());
        tabs.addTab("Menu", buildMenuTab());
        tabs.addTab("Stats", buildStatsTab());
        add(tabs, BorderLayout.CENTER);
        wireButtons();
    }
    private static DefaultTableModel createReadOnlyModel(String[] columns){
        return new DefaultTableModel(columns, 0){
            @Override
            public boolean isCellEditable(int row, int column){
                return false; //tables are display only
            }
        };
    }
    private JPanel buildHeader(){
        JPanel header = new JPanel(new BorderLayout());
        header.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        welcomeLabel.setFont(welcomeLabel.getFont().deriveFont(Font.BOLD, 18f)); //format welcome title 
        header.add(welcomeLabel, BorderLayout.WEST);
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.add(logoutButton);
        buttons.add(deleteAccountButton);
        header.add(buttons, BorderLayout.EAST); //position logout and delete buttons to the right of the screen
        return header;
    }
    private JPanel buildOrdersTab(){
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.add(new JScrollPane(ordersTable), BorderLayout.CENTER); //centre scrollable orders table 
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controls.add(new JLabel("Set selected order to:"));
        controls.add(statusBox);
        controls.add(updateStatusButton);
        controls.add(refreshOrdersButton);
        panel.add(controls, BorderLayout.SOUTH); //set buttons and controls to be anchored at the bottom of the frame
        return panel;
    }
    private JPanel buildMenuTab(){
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.add(new JScrollPane(menuTable), BorderLayout.CENTER);

        JPanel form = new JPanel(new GridLayout(0, 2, 5, 5));
        form.setBorder(BorderFactory.createTitledBorder("Add menu item")); //formatted section where user can add items to the db by filling in following info 
        form.add(new JLabel("ID:"));          form.add(idField);
        form.add(new JLabel("Name:"));        form.add(nameField);
        form.add(new JLabel("Description:")); form.add(descriptionField);
        form.add(new JLabel("Category:"));    form.add(categoryField);
        form.add(new JLabel("Price:"));       form.add(priceField);
        JPanel east = new JPanel(new BorderLayout(5, 5));
        east.add(form, BorderLayout.NORTH);
        east.add(addItemButton, BorderLayout.CENTER);
        panel.add(east, BorderLayout.EAST);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottom.add(removeItemButton);
        panel.add(bottom, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel buildStatsTab(){
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        statsArea.setEditable(false); //user cannot change info thats in this scrollable 
        statsArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
        panel.add(new JScrollPane(statsArea), BorderLayout.CENTER);
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottom.add(refreshStatsButton); //refresh button thats set at the bottom of the frame
        panel.add(bottom, BorderLayout.SOUTH);
        return panel;
    }

    private void wireButtons(){ //wireButtons map buttons to specific actions
        logoutButton.addActionListener(e -> logout());
        deleteAccountButton.addActionListener(e -> deleteAccount());
        updateStatusButton.addActionListener(e -> updateStatus());
        refreshOrdersButton.addActionListener(e -> { loadOrders(); loadStats(); });
        addItemButton.addActionListener(e -> addMenuItem());
        removeItemButton.addActionListener(e -> removeMenuItem());
        refreshStatsButton.addActionListener(e -> loadStats());
    }
    public void onShow(){ //called each time an admin logs in
        welcomeLabel.setText("Admin: " + frame.getCurrentUser().getUsername());
        loadOrders();
        loadMenu();
        loadStats();
        
    }
    private void loadOrders(){
        ordersModel.setRowCount(0);
        for (Order order : frame.getOrderService().getAllOrders()){
            String customer = order.getCustomer() == null ? "(unknown)" : order.getCustomer().getUsername();
            ordersModel.addRow(new Object[]{order.getId(), customer, order.getStatus(),
                order.getOrderTime(), String.format("$%.2f", order.getTotalAmount())});
        }
        
    }
    private void loadMenu(){
        menuModel.setRowCount(0);
        for (MenuItem item : frame.getMenuService().getAllItems()){
            menuModel.addRow(new Object[]{item.getId(), item.getName(), item.getDescription(),
                item.getCategory(), String.format("$%.2f", item.getPrice())});
        }
    }
    private void loadStats(){
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Total revenue: $%.2f%n%n", frame.getOrderService().getTotalRevenue()));
        sb.append("Orders by status:\n");
        for (OrderStatus status : OrderStatus.values()){
            sb.append(String.format("  %-10s %d%n", status, frame.getOrderService().getOrderCountByStatus(status)));
        }
        
        statsArea.setText(sb.toString());
        
    }
    private void updateStatus(){
        int row = ordersTable.getSelectedRow();
        if (row == -1){
            JOptionPane.showMessageDialog(this, "Select an order first.", "No order selected", JOptionPane.WARNING_MESSAGE); //if no order is selected, tell user
            return;
        }
        int orderId = (int) ordersModel.getValueAt(ordersTable.convertRowIndexToModel(row), 0);
        OrderStatus newStatus = (OrderStatus) statusBox.getSelectedItem();
        if (frame.getOrderService().updateOrderStatus(orderId, newStatus)){
            loadOrders();
            loadStats();
        }
        else{
            JOptionPane.showMessageDialog(this, "Could not update that order.", "Update failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addMenuItem(){
        String name = nameField.getText().trim();
        String description = descriptionField.getText().trim();
        String category = categoryField.getText().trim();
        
        if (name.isEmpty() || category.isEmpty()){ //attempt to add item but allow exceptions
            showError("Name and category cant be empty.");
            return;
        }
        int id;
        double price;
        try{
            id = Integer.parseInt(idField.getText().trim());
            price = Double.parseDouble(priceField.getText().trim());
        }
        catch (NumberFormatException e) {
            showError("ID must be a whole number and price must be a number.");
            return;
        }
        if (id < 1 || price < 0){
            showError("ID must be at least 1 and price can't be negative.");
            return;
        }
        if (frame.getMenuService().addItem(id, name, description, category, price)){
            idField.setText("");
            nameField.setText("");
            descriptionField.setText("");
            categoryField.setText("");
            priceField.setText("");
            loadMenu();
        }
        else{
            showError("Could not add item. That ID may already exist.");
        }
        
        
    }
    private void removeMenuItem(){
        int row = menuTable.getSelectedRow();
        if (row == -1){
            JOptionPane.showMessageDialog(this, "Select a menu item first.", "No item selected", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int) menuModel.getValueAt(menuTable.convertRowIndexToModel(row), 0);
        if (frame.getMenuService().removeItem(id)){
            loadMenu();
        }
        else{
            showError("Could not remove item. It may be part of an existing order.");
        }
    }
    private void logout(){
        frame.setCurrentUser(null);
        frame.showScreen("login");
    }
    private void deleteAccount(){
        int answer = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete your account?",
                "Delete account", JOptionPane.YES_NO_OPTION);
        if(answer != JOptionPane.YES_OPTION){
            return;
        }
        if (frame.getAuthService().deleteUser(frame.getCurrentUser().getUsername())){
            JOptionPane.showMessageDialog(this, "Account deleted.");
            frame.setCurrentUser(null);
            frame.showScreen("login");
        } 
        else{
            showError("Could not delete account.");
        }
    }
    private void showError(String message){
        JOptionPane.showMessageDialog(this, message, "Something went wrong", JOptionPane.ERROR_MESSAGE);
    }
    
    
}