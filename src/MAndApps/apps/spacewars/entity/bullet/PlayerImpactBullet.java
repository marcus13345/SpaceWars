package MAndApps.apps.spacewars.entity.bullet;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import MAndApps.apps.spacewars.Entity;
import MAndApps.apps.spacewars.SpaceWars;
import MAndApps.apps.spacewars.entity.Bullet;
import MAndApps.apps.spacewars.tools.Direction;

public class PlayerImpactBullet extends Bullet {
	private final Direction direction;
	private final int WIDTH, HEIGHT;
	private final static int SPEED = 10;
	private double x, y;
	private boolean alive = true;
	private final int level;

    public int getWIDTH(){
        return WIDTH;
    }

    public int getHEIGHT(){
        return HEIGHT;
    }

	public PlayerImpactBullet(Direction direction2, int x, int y, int level) {
		super(x, y, 1, 1);
		this.x = x;
		this.y = y;
		this.direction = direction2;
		if (direction2 == Direction.UP || direction2 == Direction.DOWN) {
			WIDTH = 3;
			HEIGHT = 8;
		} else {
			WIDTH = 8;
			HEIGHT = 3;
		}
		this.level = level;
	}

	public int tick() {
		if (alive) {
			if (direction == Direction.UP) {
				y -= SPEED;
			} else if (direction == Direction.DOWN) {
				y += SPEED;
			} else if (direction == Direction.LEFT) {
				x -= SPEED;
			} else if (direction == Direction.RIGHT) {
				x += SPEED;
			}
			
			if(x > SpaceWars.getWIDTH() || x < 0 - WIDTH || y > SpaceWars.getHEIGHT() || y < 0 - HEIGHT){
				alive = false;
			}
		}
		return 0;
	}

	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		if (alive)
			g.fillRect((int) x, (int) y, WIDTH, HEIGHT);
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void collidedWith(Entity e) {
		// TODO Auto-generated method stub
		
	}
}
