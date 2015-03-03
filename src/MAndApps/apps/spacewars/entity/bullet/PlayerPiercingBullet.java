package MAndApps.apps.spacewars.entity.bullet;

import java.awt.Color;
import java.awt.Graphics;

import MAndApps.apps.spacewars.SpaceWars;
import MAndApps.apps.spacewars.entity.Bullet;
import MAndApps.apps.spacewars.tools.Direction;

public class PlayerPiercingBullet extends Bullet {
	private final int direction, MAX_HITS;
	private final int WIDTH, HEIGHT;
	private final static int SPEED = 10;
	private double x, y, oldX, oldY;
	private boolean alive = true;
	private int hits = 0;
	private final boolean INFINISHOT;
	final boolean lolface = true;

    @Override
	public int getWIDTH(){
        return WIDTH;
    }

    @Override
	public int getHEIGHT(){
        return HEIGHT;
    }

	public PlayerPiercingBullet(int direction, int x, int y, int pierce, boolean b) {
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
		updateBoundingBox((int) this.x, (int) this.y, WIDTH, HEIGHT);
		INFINISHOT = b;
	}
	
	@Override
	public int tick() {
		oldX = x;
		oldY = y;
		if (alive) {
			switch(direction){
			case Direction.UP:
				y -= SPEED;
				break;
			case Direction.DOWN:
				y += SPEED;
				break;
			case Direction.LEFT:
				x -= SPEED;
				break;
			case Direction.RIGHT:
				x += SPEED;
				break;
			}
			updateBoundingBox((int) x, (int) y, WIDTH, HEIGHT);
			for (int i = 0; i < SpaceWars.getEnemies().size() && hits < MAX_HITS; i++) {
				if (SpaceWars.getEnemies().elementAt(i).getBoundingBox().intersectsLine((int)x, (int)y, (int)oldX, (int)oldY)) {
					hits++;
					SpaceWars.getEnemies().elementAt(i).damage(getDamage());
					if(hits >= MAX_HITS){
						alive = false;
						i = SpaceWars.getEnemies().size();
					}
				}
			}
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
}
