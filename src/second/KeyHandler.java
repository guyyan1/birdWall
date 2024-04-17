package second;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed;
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        switch(code){
            case KeyEvent.VK_SPACE, KeyEvent.VK_UP:
                upPressed = true;
                break;
            default: break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        switch(code){
            case KeyEvent.VK_SPACE:
                upPressed = false;
                break;
            case 7:
                System.out.println("fdfsdfsd");
                break;
            default: break;
        }
    }
}
