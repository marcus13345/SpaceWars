package MAndApps.apps.spacewars.gun;

import java.awt.Graphics;
import java.util.Stack;

import MAndApps.apps.spacewars.entity.Bullet;

public class Gun {
	private Stack<Bullet> bullets = new Stack<Bullet>();
	private int bulletType;
	private int MAX_COOLDOWN;
	private int cooldown = 0, x, y;
	public Gun(int bulletType, int cooldown, int x, int y){
		MAX_COOLDOWN = cooldown;
		this.bulletType = bulletType;
        this.x = x;
        this.y = y;
	}

    public void updatePosition(int x, int y){
        this.x = x;
        this.y = y;
    }
	
	public void render(Graphics g){
		for(int i = 0; i < bullets.size(); i ++)
			bullets.elementAt(i).render(g);
	}
	
	public void tick(){
		cooldown ++;
		//tick bullets
		for(int i = 0; i < bullets.size(); i++)
			bullets.elementAt(i).tick();
		for(int i = 0; i < bullets.size(); i++){
			if(!bullets.elementAt(i).getAlive())
				bullets.remove(i);
			else
				i++;
		}
	}
	
	public void shoot(int direction){
		if(cooldown >= MAX_COOLDOWN){
			cooldown = 0;
			bullets.push(Bullet.getNewBullet(bulletType, x-(Bullet.getNewBullet(bulletType, x, y, direction).getWIDTH()/2), y-(Bullet.getNewBullet(bulletType, x, y, direction).getHEIGHT()/2), direction));
		}
	}
	
	public void switchAmmo(int bulletType){
		this.bulletType = bulletType;
        MAX_COOLDOWN = Bullet.getCooldown(bulletType);
	}

	public void setCooldown(int i) {
		MAX_COOLDOWN = i;
	}
}