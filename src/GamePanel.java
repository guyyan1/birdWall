import entity.Bird;

import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    final int originalTileSize = 20; // 20x20 tile
    final int scale = 3;

    final int gameTileSize = originalTileSize * scale; // 60x60 tile
    final int maxGameCol = 20;
    final int maxGameRow = 12;
    final int gameScreenWidth = maxGameCol * gameTileSize; // 60 * 20 = 1200 pixel
    final int gameScreenHeight = maxGameRow * gameTileSize; // 60 * 12 = 720 pixel
    final int FPS = 60;

    Thread gameThread;
    KeyHandler keyH = new KeyHandler();
    Bird gameBird = new Bird();
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
                currentMovementTime = update(currentMovementTime);
                // refresh screen
                repaint();
                delta--;
            }
        }
    }

    public long update(long lastMovementTime) {
        // measure time for regular movement
        double timeElapsed = (double) (System.nanoTime() - lastMovementTime) / 1000000; // convert to milliseconds;
        if(timeElapsed > 250L) {
            if (keyH.upPressed) {
                gameBird.jump();
                keyH.upPressed = false;
            } else {
                gameBird.move();
            }
            return System.nanoTime();
        }
        return lastMovementTime;
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        // g2d is our "brash" on the screen
        g2d.setColor(Color.BLACK);
        g2d.fillRect(gameBird.getXPos(), gameBird.getYPos(), gameTileSize, gameTileSize);
        g2d.dispose();
    }
}
