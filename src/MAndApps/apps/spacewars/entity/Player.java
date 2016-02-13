package MAndApps.apps.spacewars.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import MAndApps.apps.spacewars.SpaceWars;
import MAndApps.apps.spacewars.gun.Gun;
import MAndApps.apps.spacewars.tools.Direction;
import MAndApps.apps.spacewars.Entity;
import static MAndEngine.Utils.rand;
import MAndEngine.Engine;

public class Player extends Entity {
	private static final double ACC = 0.5, MAXSPEED = 5;
	private boolean alive = false;
	private Gun gun = new Gun(Bullet.BASIC);
	
	/**
	 * go boom is a callback when it dies because it dies on tick i assume? TODO
	 * fix that so no die on tick.
	 */
	private boolean goBoom = false;

	public Player() {
		reset();
		width = 16 * SpaceWars.scale;
		height = 16 * SpaceWars.scale;
	}

	private void reset() {
		x = SpaceWars.getWIDTH() / 2 - width / 2;
		y = SpaceWars.getHEIGHT() - (50 * SpaceWars.scale);
		dy = -10;
		dx = 0;
		timer = 0;
		time = 5;
	}

	@Override
	public int tick() {
		double ACC = Player.ACC * Engine.deltaTime / 2d;

		if (goBoom) {
			SpaceWars.BOOM(50, 1.2, 50, 50, 50, 30, (int)(x + width/2), (int)(y + height/2), 550,
					true, false, 3);
			goBoom = false;
		}
		gun.tick();
		if (alive) {
			
			//hold up, before we dive into physics...
			
			if(Engine.keys[KeyEvent.VK_I]) gun.shoot(Direction.UP);
			else if(Engine.keys[KeyEvent.VK_J]) gun.shoot(Direction.LEFT);
			else if(Engine.keys[KeyEvent.VK_K]) gun.shoot(Direction.DOWN);
			else if(Engine.keys[KeyEvent.VK_L]) gun.shoot(Direction.RIGHT);
			
			
			if (time != 1)
				time -= 0.05d * Engine.deltaTime;
			if (time < 1)
				time = 1;

			if (x > SpaceWars.getWIDTH() - width) {
				dx -= ACC;
			} else if (x < 0) {
				dx += ACC;
			} else if ((Engine.keys[KeyEvent.VK_D] && !Engine.keys[KeyEvent.VK_A]))
				dx += ACC;
			else if ((Engine.keys[KeyEvent.VK_A] && !Engine.keys[KeyEvent.VK_D])) {
				dx -= ACC;
			} else {
				if ((int) (dx * 100) > 0)
					dx -= ACC;
				else if ((int) (dx * 100) < 0)
					dx += ACC;
				else
					dx = 0;
			}

			if (y > SpaceWars.getHEIGHT() - height) {
				dy -= ACC;
			} else if (y < 0) {
				dy += ACC;
			} else if (Engine.keys[KeyEvent.VK_S]
					&& !Engine.keys[KeyEvent.VK_W])
				dy += ACC;
			else if (Engine.keys[KeyEvent.VK_W] && !Engine.keys[KeyEvent.VK_S]) {
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
				dx -= ACC;
			if (dx < 0 - MAXSPEED)
				dx += ACC;
			if (dy > MAXSPEED)
				dy -= ACC;
			if (dy < 0 - MAXSPEED)
				dy += ACC;

			y += dy * Engine.deltaTime;
			x += dx * Engine.deltaTime;

		} else {
			if (timer > 4 * 50)
				alive = true;
			else
				timer += 0.75d * Engine.deltaTime;
		}

		return 0;
	}

	private double timer = 0;

	@Override
	public void render(Graphics g) {

		g.setColor(Color.BLACK);

		if (alive) {
			int temp = rand(1, (int) time);

			if (temp == 1)
				g.fillRect((int) x, (int) y, (int) width, (int) height);
		}
	}

	private double time = 0;

	public boolean getAlive() {
		return true;
	}

	public boolean isRespawning() {
		return !alive;
	}

	@Override
	public void die() {
		// TODO Auto-generated method stub

	}

	@Override
	public void collidedWith(Entity e) {

		if (alive && e instanceof Enemy) {
			alive = false;
			goBoom = true;
			timer = 0;
			time = 5;
			reset();
		}
	}

	@Override
	public boolean isCollidable() {
		// TODO Auto-generated method stub
		return true;
	}

	public int getCenterY() {
		return (int)(y + height / 2d);
	}
	
	public int getCenterX() {
		return (int)(x + width / 2d);
	}
}
