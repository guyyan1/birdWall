import second.GamePanel;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame gameWindow = new JFrame();
        gameWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        gameWindow.setResizable(false);
        gameWindow.setTitle("2D entity.Bird Game");
        GamePanel gamePanel = new GamePanel();
        gameWindow.add(gamePanel);
        gameWindow.pack();


        gameWindow.setLocationRelativeTo(null);
        gameWindow.setVisible(true);
    }
}