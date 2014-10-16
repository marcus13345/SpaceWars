package MAndApps.apps.spacewars;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Random;

import MAndApps.apps.spacewars.tools.BasicTickAndRender;

public abstract class Entity extends BasicTickAndRender{
	private Rectangle boundingBox;
	private Random r = new Random();
	public Entity(int x, int y, int width, int height){
		boundingBox = new Rectangle(x, y, width, height);
	}
	
	public void updateBoundingBox(int x, int y, int width, int height){
		boundingBox.setBounds(x, y, width, height);
	}
	public void keyPressed(KeyEvent e){}
	public void keyReleased(KeyEvent e){}
	public final double getX(){
		return boundingBox.getX();
	}
	public final double getY(){
		return boundingBox.getY();
	}
	public int rand(int i, int j){
		return r.nextInt(j-i) + i;
	}
	public Rectangle getBoundingBox(){
		return boundingBox;
	}
}
