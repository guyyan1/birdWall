package entity;

public class Bird {
    private int xPos;
    private int yPos;
    private int forward_pace;
    private boolean hasCrashed;
    private final int gravity = 10;
    private final int jump_pace = 20;
    private final int paceAddition = 3;

    public Bird(){
        // default starting position and starting forward pace
        xPos = 0;
        yPos = 0;
        forward_pace = 7;
        hasCrashed = false;
    }

    private void updateBirdPos(int x, int y){
        xPos = x;
        yPos = y;
    }

    public void jump(){
        updateBirdPos(xPos, yPos - jump_pace);
    }

    public void move(){
        updateBirdPos(xPos + forward_pace, yPos + gravity);
    }

    private void increasePace(){
        forward_pace += paceAddition;
    }

    public int getXPos(){
        return xPos;
    }
    public int getYPos(){
        return yPos;
    }

}
