package MAndApps.apps.spacewars.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Random;

import MAndApps.apps.spacewars.SpaceWars;
import MAndApps.apps.spacewars.gun.Gun;
import MAndApps.apps.spacewars.tools.Direction;
import MAndApps.apps.spacewars.Entity;
import static MAndEngine.Utils.rand;

public class Player extends Entity {
	private final static int WIDTH = 16, HEIGHT = 16;
	private static final double ACC = 0.5, MAXSPEED = 5;
	private boolean A = false, S = false, D = false, W = false, alive = true;
	private Gun gun = new Gun(Bullet.BASIC, 25, (int)x, (int)y);
	
	/**
	 * go boom is a callback when it dies because it dies on tick i assume?
	 * TODO fix that so no die on tick.
	 */
	private boolean goBoom = false;

	public Player() {
		x = 512;
		y = 550;
	}

	@Override
	public int tick() {
		if (goBoom) {
			SpaceWars.BOOM(50, 1.2, 50, 50, 50, 30, (int) x, (int) y, 550, true,
					false, 3);
			goBoom = false;
		}
		gun.tick();
		if (alive) {

			if (time != 1)
				time -= 0.05d;
			if (time < 1)
				time = 1;

			if (D && !A)
				dx += ACC;
			else if (A && !D) {
				dx -= ACC;
			} else {
				if ((int) (dx * 100) > 0)
					dx -= ACC;
				else if ((int) (dx * 100) < 0)
					dx += ACC;
				else
					dx = 0;
			}

			if (S && !W)
				dy += ACC;
			else if (W && !S) {
				dy -= ACC;
			} else {
				if ((int) (dy * 100) > 0)
					dy -= ACC;
				else if ((int) (dy * 100) < 0)
					dy += ACC;
				else
					dy = 0;
			}

			if (dx > MAXSPEED)
				while (dx > MAXSPEED)
					dx -= ACC;
			if (dx < 0 - MAXSPEED)
				while (dx < 0 - MAXSPEED)
					dx += ACC;
			if (dy > MAXSPEED)
				while (dy > MAXSPEED)
					dy -= ACC;
			if (dy < 0 - MAXSPEED)
				while (dy < 0 - MAXSPEED)
					dy += ACC;

			y += dy;
			x += dx;

			if (x > SpaceWars.getWIDTH() - WIDTH)
				while (x > SpaceWars.getWIDTH() - WIDTH)
					x--;
			if (x < 0)
				while (x < 0)
					x++;

			if (y > SpaceWars.getHEIGHT() - HEIGHT)
				while (y > SpaceWars.getHEIGHT() - HEIGHT)
					y--;
			if (y < 0)
				while (y < 0)
					y++;

		} else {
			x = -1;
			y = -1;
			if (timer > 4 * 50) {
				alive = true;
				x = 512;
				y = 550;
			}
			timer++;
		}

		return 0;
	}

	private int timer = 0;

	@Override
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		if (alive) {
			int temp;
			try {
				temp = rand(0, (int)time);
			} catch (Exception e) {
				temp = 1;
			}
			if (temp == 0)
				g.fillRect((int) x, (int) y, WIDTH, HEIGHT);
		}
		gun.render(g);
	}

	private double time = 0;

	public boolean getAlive() {
		return alive;
	}

	@Override
	public boolean isCollidable() {
		return true;
	}

	@Override
	public void die() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void collidedWith(Entity e) {
		if(e instanceof Enemy) {
			goBoom = true;
			timer = 0;
			alive = false;
			time = 5;
		}
	}
}
