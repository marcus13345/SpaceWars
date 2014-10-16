package MAndApps.apps.spacewars.entity.bullet;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import MAndApps.apps.spacewars.SpaceWars;
import MAndApps.apps.spacewars.entity.Bullet;
import MAndApps.apps.spacewars.tools.Direction;

public class BasicPlayerBullet extends Bullet {
	private final int direction;
	private final int WIDTH, HEIGHT;
	private final static int SPEED = 10;
	private double x, y;
	private boolean alive = true;

	public BasicPlayerBullet(int direction, int x, int y) {
		super(x, y, 1, 1);
		this.x = x;
		this.y = y;
		this.direction = direction;
		if (direction == Direction.UP || direction == Direction.DOWN) {
			WIDTH = 3;
			HEIGHT = 8;
		} else {
			WIDTH = 8;
			HEIGHT = 3;
		}
		updateBoundingBox((int) this.x, (int) this.y, WIDTH, HEIGHT);
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

			updateBoundingBox((int) x, (int) y, WIDTH, HEIGHT);
			Rectangle r = getBoundingBox();
			for (int i = 0; i < SpaceWars.getEnemies().size(); i++) {
				if (r.intersects(SpaceWars.getEnemies().elementAt(i).getBoundingBox())) {
					SpaceWars.getEnemies().elementAt(i).damage(getDamage());
					alive = false;
					i = SpaceWars.getEnemies().size();
				}
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

    public int getWIDTH(){
        return WIDTH;
    }

    public int getHEIGHT(){
        return HEIGHT;
    }

}
