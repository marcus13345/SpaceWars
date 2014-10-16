package MAndApps.apps.spacewars.shop;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Shop {
    private final int ON = 17, OFF = 64, BOX_WIDTH = 500, BOX_HEIGHT = 200, BOX_Y_OFFSET = 50, BOX_Y_MULTIPLIER = 250, MAX_ANIMATION_TIME = BOX_WIDTH;
    private int state = OFF, selection = 0;
    private double animationTime = 0;
    public void render(Graphics g, final int WIDTH){
        g.setColor(selection == 0 ? Color.CYAN : Color.BLUE);
        g.fillRect((int)animationTime-BOX_WIDTH, BOX_Y_OFFSET, BOX_WIDTH, BOX_HEIGHT);
        g.setColor(selection == 2 ? Color.CYAN : Color.BLUE);
        g.fillRect((int)animationTime-BOX_WIDTH, BOX_Y_OFFSET+BOX_Y_MULTIPLIER, BOX_WIDTH, BOX_HEIGHT);
        g.setColor(selection == 1 ? Color.CYAN : Color.BLUE);
        g.fillRect(WIDTH - (int)animationTime, BOX_Y_OFFSET, BOX_WIDTH, BOX_HEIGHT);
        g.setColor(selection == 3 ? Color.CYAN : Color.BLUE);
        g.fillRect(WIDTH - (int)animationTime, BOX_Y_OFFSET+BOX_Y_MULTIPLIER, BOX_WIDTH, BOX_HEIGHT);
    }

    public void tick(){
        if(state == ON && !(animationTime > BOX_WIDTH)){
            animationTime+=(double)(MAX_ANIMATION_TIME-animationTime)/6;
        }else if(state == OFF && !(animationTime < 0)){
            animationTime+=(double)(0-animationTime)/6;
        }
        if(animationTime < 0.49d){
            selection = 0;
        }
    }

    public void toggleState(){
        if(state == ON){
            state = OFF;
        }else if(state == OFF){
            state = ON;
        }
    }

    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W){
            selection += 2;
            selection %= 4;
        }else if(e.getKeyCode() == KeyEvent.VK_D){
            selection += 1;
            if(selection == 2) selection = 0;
            else if(selection == 4) selection = 2;
        }else if(e.getKeyCode() == KeyEvent.VK_A){
            selection -= 1;
            if(selection == -1) selection = 1;
            else if(selection == 1) selection = 3;
        }else if(e.getKeyCode() == KeyEvent.VK_S){
            selection -= 2;
            if(selection < 0) selection += 4;
        }
    }
}
