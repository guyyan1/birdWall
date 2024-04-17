package entity;

import second.GamePanel;
import second.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Bird extends Entity{
    private boolean hasCrashed;
    private String movingDirection;
    private final int gravity = 30;
    private final int jump_pace = 30;
    private final int paceAddition = 3;
    GamePanel gp;
    KeyHandler kh;
    BufferedImage left ,right, jumpingL, jumpingR;

    public Bird(GamePanel gp, KeyHandler kh){
        // default starting position and starting forward pace
        this.gp = gp;
        this.kh = kh;
        initializeDefaultValues();
        getBirdImage();
    }

    public void initializeDefaultValues(){
        xPos = 0;
        yPos = 0;
        pace = 60;
        this.hasCrashed = false;
        this.movingDirection = "right";
    }

    private void updateBirdPos(int x, int y){
        xPos = x;
        yPos = y;
    }

    public void jump(){
        updateBirdPos(xPos, yPos - jump_pace);
    }

    public void move(){
        updateBirdPos(xPos + pace, yPos + gravity);
    }

    private void increasePace(){
        pace += paceAddition;
    }

    public int getXPos(){
        return xPos;
    }
    public int getYPos(){
        return yPos;
    }

    public void getBirdImage(){
        try{
            right = ImageIO.read(getClass().getClassLoader().getSystemResource("bird/tealBird.png"));
        }catch(IOException e){
            System.out.println("ERROR: Couldn't read image");
        }
    }

    public long update(long lastMovementTime){
        // measure time for regular movement
        double timeElapsed = (double) (System.nanoTime() - lastMovementTime) / 1000000; // convert to milliseconds;
        if(timeElapsed > 250L) {
            if (kh.upPressed) {
                jump();
                kh.upPressed = false;
            } else {
                move();
            }
            return System.nanoTime();
        }
        return lastMovementTime;
    }
    public void draw(Graphics2D g2d){
        BufferedImage image = null;
        switch(movingDirection){
            case "right":
                image = right;
                break;
            case "left":
                image = left;
                break;
            case "right_jump":
                image = jumpingR;
                break;
            case "left_jump":
                image = jumpingL;
                break;
            default:
                break;
        }
        g2d.drawImage(image, xPos, yPos, gp.gameTileSize, gp.gameTileSize, null);
    }
}
