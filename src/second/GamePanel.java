package second;

import entity.Bird;

import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    final int originalTileSize = 20; // 20x20 tile
    final int scale = 3;

    public final int gameTileSize = originalTileSize * scale; // 60x60 tile
    final int maxGameCol = 20;
    final int maxGameRow = 12;
    public final int gameScreenWidth = maxGameCol * gameTileSize; // 60 * 20 = 1200 pixel
    public final int gameScreenHeight = maxGameRow * gameTileSize; // 60 * 12 = 720 pixel
    final int FPS = 30;

    Thread gameThread;
    KeyHandler keyH = new KeyHandler();
    Bird gameBird = new Bird(this, keyH);
    public GamePanel() {
        this.setPreferredSize(new Dimension(gameScreenWidth, gameScreenHeight));
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addKeyListener(keyH);
        startGameThread();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        // related for FPS setting:
        double drawInterval = (double) 1000000000 /FPS; // 0.01666666666 seconds(60 frames per second)
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        // related for deciding each movement time
        long currentMovementTime = System.nanoTime();

        // game loop
        while(gameThread != null) {
            currentTime = System.nanoTime();
            delta+=(currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if(delta>=1){
                // ## This loop is responsible for updating characters position and screen
                update(currentMovementTime);
                // refresh screen
                repaint();
                delta--;
            }
        }
    }

    public void update(long lastMovementTime) {
        // measure time for regular movement
        gameBird.update(lastMovementTime);
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        gameBird.draw(g2d);
        g2d.dispose();
    }
}
