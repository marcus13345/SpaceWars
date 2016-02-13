package MAndApps.apps.spacewars.entity.bullet;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import MAndApps.apps.spacewars.Entity;
import MAndApps.apps.spacewars.SpaceWars;
import MAndApps.apps.spacewars.entity.Bullet;
import MAndApps.apps.spacewars.tools.Direction;

import MAndEngine.Engine;;

public class BasicPlayerBullet extends Bullet {
	private final Direction direction;
	private final static int SPEED = 10;
	private boolean alive = true;
	
	
	public BasicPlayerBullet(Direction direction, int x, int y) {
		super(x, y, 1, 1);
		this.x = x;
		this.y = y;
		this.direction = direction;
		if (direction == Direction.UP || direction == Direction.DOWN) {
			width = 3;
			height = 8;
		} else {
			width = 8;
			height = 3;
		}
	}

	public int tick() {
		if (alive) {
			
			y += direction.getY() * SPEED * Engine.deltaTime;
			x += direction.getX() * SPEED * Engine.deltaTime;
			
			if(y + height < 0) alive = false;
			if(y > SpaceWars.getHEIGHT()) alive = false;
			if(x + width < 0) alive = false;
			if(x > SpaceWars.getWIDTH()) alive = false;
			
		}
		return 0;
		
	}

	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		if (alive)
			g.fillRect((int) x, (int) y, (int)width, (int)height);
	}

	@Override
	public boolean getAlive() {
		return alive;
	}

	@Override
	public int getDamage() {
		return 1;
	}
	
	public boolean isCollidable() {
		return true;
	}

	@Override
	public void die() {
		
	}

	@Override
	public void collidedWith(Entity e) {
		
	}

	@Override
	public int getWIDTH() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getHEIGHT() {
		// TODO Auto-generated method stub
		return 0;
	}

}
