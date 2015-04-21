package MAndApps.apps.spacewars.entity.bullet;

import java.awt.Color;
import java.awt.Graphics;

import MAndApps.apps.spacewars.Entity;
import MAndApps.apps.spacewars.SpaceWars;
import MAndApps.apps.spacewars.entity.Bullet;
import MAndApps.apps.spacewars.tools.Direction;

import MAndEngine.Engine;

public class PlayerPiercingBullet extends Bullet {
	private final Direction direction;
	private final int MAX_HITS;
	private final int WIDTH, HEIGHT;
	private final static double SPEED = .01;
	private double x, y, oldX, oldY;
	private boolean alive = true;
	private int hits = 0;
	private final boolean INFINISHOT;
	final boolean lolface = true;

    public int getWIDTH(){
        return WIDTH;
    }

    public int getHEIGHT(){
        return HEIGHT;
    }

	public PlayerPiercingBullet(Direction direction, int x, int y, int pierce) {
		super(x, y, 1, 1);
		this.x = x;
		this.y = y;
		MAX_HITS = pierce;
		this.direction = direction;
		if (direction == Direction.UP || direction == Direction.DOWN) {
			WIDTH = 3;
			HEIGHT = 8;
		} else {
			WIDTH = 8;
			HEIGHT = 3;
		}
		INFINISHOT = pierce == -1;
	}
	
	public int tick() {
		oldX = x;
		oldY = y;
		if (alive) {
			y += SPEED * direction.getY() * Engine.deltaTime;
			x += SPEED * direction.getX() * Engine.deltaTime;
			
			if(x > SpaceWars.getWIDTH() || x < 0 - WIDTH || y > SpaceWars.getHEIGHT() || y < 0 - HEIGHT){
				alive = false;
			}
		}
		return 0;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.CYAN);
		if (alive)
			g.fillRect((int) x, (int) y, WIDTH, HEIGHT);
	}

	@Override
	public boolean getAlive() {
		return alive;
	}

	@Override
	public int getDamage(){
		return 2;
	}
	
	public boolean isCollidable() {
		return true;
	}

	@Override
	public void die() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void collidedWith(Entity e) {
		// TODO Auto-generated method stub
		
	}
}
