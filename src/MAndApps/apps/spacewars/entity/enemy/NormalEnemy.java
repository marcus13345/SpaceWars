package MAndApps.apps.spacewars.entity.enemy;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import MAndApps.apps.spacewars.Entity;
import MAndApps.apps.spacewars.SpaceWars;
import MAndApps.apps.spacewars.entity.Enemy;
import MAndEngine.Engine;

import static MAndEngine.Utils.rand;

public class NormalEnemy extends Enemy {
	private int health = 2;
	private final int MAX_HEALTH = health;
	private static final int PROXIMITY = 200;
	private double time = 10000, desiredX, desiredY, Xmod, Ymod;
	private static final double ACC = 0.005, MAXSPEED = 1, DEAD_ACC = 0.5d, DEAD_MAXSPEED = 5;

	private Color color;
	private boolean alive = true;
	private double healthBar = 1;
	private final double reEvaluateTime;

	public NormalEnemy() {
		this(Math.random());
		
	}
	
	public NormalEnemy(double hyperness) {
		final int LOW = 200, HIGH = 255, color = rand(LOW, HIGH);
		this.color = new Color(color, color, color);
		x = SpaceWars.getWIDTH() / 2 - width / 2;
		y = 100;
		reEvaluateTime = 1 - hyperness;
		width = 16 * SpaceWars.scale;
		height = 16 * SpaceWars.scale;
	}

	@Override
	public int tick() {
		// epic AI
		if ((int) healthBar <= 0) {
			alive = false;
		}
		if (alive) {
			if (!SpaceWars.getPlayer().isRespawning()) {
				if (time > reEvaluateTime) {
					time = 0;
					Xmod = rand(-PROXIMITY * SpaceWars.scale, PROXIMITY * SpaceWars.scale);
					Ymod = rand(-PROXIMITY * SpaceWars.scale, PROXIMITY * SpaceWars.scale);
				}

				desiredX = SpaceWars.getPlayer().getX() + Xmod;
				desiredY = SpaceWars.getPlayer().getY() + Ymod;

				if ((int) desiredX > (int) x)
					dx += ACC * Engine.deltaTime;
				else if ((int) desiredX < (int) x) {
					dx -= ACC * Engine.deltaTime;
				}
				if ((int) desiredY > (int) y)
					dy += ACC * Engine.deltaTime;
				else if ((int) desiredY < (int) y) {
					dy -= ACC * Engine.deltaTime;
				}

				if (dx > MAXSPEED)
					dx = MAXSPEED;
				if (dx < 0 - MAXSPEED)
					dx = 0 - MAXSPEED;
				if (dy > MAXSPEED)
					dy = MAXSPEED;
				if (dy < 0 - MAXSPEED)
					dy = 0 - MAXSPEED;

			} else {

				if (time > reEvaluateTime) {
					time = 0;
					Xmod = rand(0, SpaceWars.getWIDTH());
					Ymod = rand(0, SpaceWars.getHEIGHT() / 3);
				}

				desiredX = Xmod;
				desiredY = Ymod;

				if ((int) desiredX > (int) x)
					dx += DEAD_ACC * Engine.deltaTime;
				else if ((int) desiredX < (int) x) {
					dx -= DEAD_ACC * Engine.deltaTime;
				}
				if ((int) desiredY > (int) y)
					dy += DEAD_ACC * Engine.deltaTime;
				else if ((int) desiredY < (int) y) {
					dy -= DEAD_ACC * Engine.deltaTime;
				}

				if (dx > DEAD_MAXSPEED)
					while (dx > DEAD_MAXSPEED)
						dx -= DEAD_ACC;
				if (dx < 0 - DEAD_MAXSPEED)
					while (dx < 0 - DEAD_MAXSPEED)
						dx += DEAD_ACC;
				if (dy > DEAD_MAXSPEED)
					while (dy > DEAD_MAXSPEED)
						dy -= DEAD_ACC;
				if (dy < 0 - DEAD_MAXSPEED)
					while (dy < 0 - DEAD_MAXSPEED)
						dy += DEAD_ACC;

			}

			x += dx * Engine.deltaTime;
			y += dy * Engine.deltaTime;

			if (x > SpaceWars.getWIDTH() - width)
				while (x > SpaceWars.getWIDTH() - width)
					x--;
			if (x < 0)
				while (x < 0)
					x++;

			if (y > SpaceWars.getHEIGHT() - height)
				while (y > SpaceWars.getHEIGHT() - height)
					y--;
			if (y < 0)
				while (y < 0)
					y++;
		}
		time += 0.01 * Engine.deltaTime;
		absoluteTime += 0.01 * Engine.deltaTime;
		return 0;
	}

	private double absoluteTime = 0;
	
	private Random r = new Random();
	@Override
	public void render(Graphics g) {
		g.setColor(color);
		int temp;

		try{
			temp = r.nextDouble() > absoluteTime / 2 ? 1 : 0;
		}catch(Exception e){
			temp = 0;
		}
		
		if (temp == 0) if (alive) g.fillRect((int) x, (int) y, (int)width, (int)height);
		
		if (SpaceWars.debug) g.drawLine((int)(x + width / 2), (int)(y + height / 2), (int)(desiredX + width / 2), (int)(desiredY + height / 2));
		
		healthBar += ((((double)health/(double)MAX_HEALTH)*(width + 1)) - healthBar)/6;
		//healthbar
		g.setColor(Color.BLACK);
		g.fillRect((int) x, (int) y - 5, (int)width - 1, 3);
		g.setColor(Color.RED);
		g.fillRect((int) x, (int) y - 5, (int)width, 3);
		g.setColor(Color.GREEN);
		g.fillRect((int) x, (int) y - 5, (int) healthBar, 3);
	}

	@Override
	public void damage(int i) {
		health -= i;
	}

	@Override
	public boolean getAlive() {
		return alive;
	}

	@Override
	public Color getColor() {
		return color;
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
