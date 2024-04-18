package entity;

import second.GamePanel;
import second.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Bird extends Entity{
    private final int paceAddition = 3;
    private boolean hasCrashed;
    private String movingDirection;
    private int directionDeterminer;
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
        xPos = 1;
        yPos = 1;
        pace = 2;
        spriteCounter = 10;
        this.hasCrashed = false;
        this.movingDirection = "right";
        this.directionDeterminer = 1;
    }

    private void hasReachedWall(){
        if(xPos + gp.gameTileSize >= gp.gameScreenWidth){
            this.movingDirection = "left";
            this.directionDeterminer = -1;
        }
        else if(xPos <= 0){
            this.movingDirection = "right";
            this.directionDeterminer = 1;
        }
    }

    private void updateBirdPos(int x, int y){
        xPos = x;
        yPos = y;
    }

    public void jump(){
        int jump_pace = 60;
        if(yPos - jump_pace >0)
            updateBirdPos(xPos, yPos - jump_pace);
    }

    public void move(){
        int gravity = 1;
        updateBirdPos(xPos + pace * directionDeterminer, yPos + gravity);
    }

    private void increasePace(){
        pace += paceAddition;
    }

//    public int getXPos(){
//        return xPos;
//    }
//    public int getYPos(){
//        return yPos;
//    }

    public void getBirdImage(){
        try{
            right = ImageIO.read(getClass().getClassLoader().getSystemResource("bird/right_b.png"));
            left = ImageIO.read(getClass().getClassLoader().getSystemResource("bird/left_b.png"));
            jumpingL = ImageIO.read(getClass().getClassLoader().getSystemResource("bird/jump_left_b.png"));
            jumpingR = ImageIO.read(getClass().getClassLoader().getSystemResource("bird/jump_right_b.png"));
        }catch(IOException e){
            System.out.println("ERROR: Couldn't read image");
        }
    }

    public void update(long lastMovementTime){
        // measure time for regular movement
//        double timeElapsed = (double) (System.nanoTime() - lastMovementTime) / 1000000; // convert to milliseconds;
//        if(timeElapsed > 250L) {
            if (kh.upPressed) {
                spriteCounter = 0;
                jump();
                if(movingDirection.equals("right")){
                    movingDirection = "right_jump";
                }else{
                    movingDirection = "left_jump";
                }
                kh.upPressed = false;
            } else {
                move();
                if(movingDirection.equals("right_jump")){
                    movingDirection = "right";
                }else if(movingDirection.equals("left_jump")){
                    movingDirection = "left";
                }
            }
            hasReachedWall();
            spriteCounter++;
//            return System.nanoTime();
//        }
//        return lastMovementTime;
    }
    public void draw(Graphics2D g2d){
        BufferedImage image = null;
        switch(movingDirection){
            case "right":
                if(spriteCounter<10){
                    image = jumpingR;
                }
                else {
                    image = right;
                }
                break;
            case "left":
                if(spriteCounter<10){
                    image = jumpingL;
                }
                else {
                    image = left;
                }
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
