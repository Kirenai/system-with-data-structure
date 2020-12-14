package proyecto;

import javax.swing.*;
import proyecto.view.MainMenuView;

public class MainProyecto {
    
    public static void main(String[] args) {
        
        try {
            // Set System L&F 
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException
                | InstantiationException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        MainMenuView viewMenu = new MainMenuView(); //objeto
        viewMenu.setVisible(true);
        viewMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        viewMenu.setLocationRelativeTo(null);
        viewMenu.setTitle("App proyecto");
    }
}
