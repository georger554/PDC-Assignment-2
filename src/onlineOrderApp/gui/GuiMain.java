/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlineOrderApp.gui;
import javax.swing.SwingUtilities;
import onlineOrderApp.DBManager;
import onlineOrderApp.DatabaseSeeder;

/**
 *
 * @author georgerobinson
 */

// Generated with assistance from Claude
public class GuiMain {
    public static void main(String[] args) {
        DBManager.initializeDatabase(); //tables must exist before any screen loads
        DatabaseSeeder.seedMenuIfEmpty();
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true)); //Swing must start on its own thread
    }
}