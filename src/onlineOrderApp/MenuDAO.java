/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlineOrderApp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author georgerobinson
 */

public class MenuDAO{

    public List<MenuItem> getAllMenuItems(){
        List<MenuItem> items = new ArrayList<>(); //menulist  of items
        String sql = "SELECT * FROM MenuItems"; //sql to get all colums/rows from menuitems table 

        try (Connection conn = DBManager.getConnection(); //try to connect to database
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){ //stores result in resultset

            while (rs.next()){ //go through each row from db 
                items.add(new MenuItem(rs.getInt("id"),rs.getString("name"),rs.getString("description"),rs.getString("category"),rs.getDouble("price")));// add items to menuitem list
            }
        }catch (SQLException e){
            System.out.println("Error loading menu items: " + e.getMessage());
        }
        return items;
    }

    public boolean insertMenuItem(MenuItem item) {
        String sql = "INSERT INTO MenuItems (id, name, description, category, price) VALUES (?, ?, ?, ?, ?)"; //sql insertion 

        try (Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setInt(1, item.getId()); //first question mark is for ID
            ps.setString(2, item.getName()); //Second is for name
            ps.setString(3, item.getDescription()); //etc
            ps.setString(4, item.getCategory());
            ps.setDouble(5, item.getPrice());

            ps.executeUpdate(); //execute insertion
            return true;
        }
        catch (SQLException e){ //catch sql exception and print error messages and return falsse
            System.out.println("Error inserting " + e.getMessage());
            return false;
        }
    }

    public boolean deleteMenuItem(int id){
        String sql = "DELETE FROM MenuItems WHERE id = ?";//sql deletion

        try (Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, id); // set ? to deletion of id
            return ps.executeUpdate() > 0; //execute deletion and return true
        }
        catch (SQLException e) {
            System.out.println("Error deleting: " + e.getMessage()); //else if there is an error print error and return false
            return false;
        }
    }
}
