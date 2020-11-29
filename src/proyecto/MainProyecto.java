package proyecto;

import javax.swing.*;
import proyecto.vistas.MenuPrincipal;

public class MainProyecto {
    
    public static void main(String[] args) {
        
        try {
            // Set System L&F 
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException
                | InstantiationException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        MenuPrincipal mainMenu = new MenuPrincipal();
        mainMenu.setVisible(true);
        mainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainMenu.setLocationRelativeTo(null);
        mainMenu.setTitle("App proyecto");
    }
}
