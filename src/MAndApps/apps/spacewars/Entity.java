package MAndApps.apps.spacewars;

import java.awt.event.KeyEvent;

public abstract class Entity extends BasicTickAndRender{
	
	protected double x, y, dx, dy;
	
	public void keyPressed(KeyEvent e){}
	public void keyReleased(KeyEvent e){}
	public final double getX(){
		return x;
	}
	public final double getY(){
		return y;
	}
	
	public abstract boolean isCollidable();

	public abstract void die();

	public abstract boolean getAlive();
	
	public abstract void collidedWith(Entity e);
}
