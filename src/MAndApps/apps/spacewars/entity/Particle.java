package MAndApps.apps.spacewars.entity;

import static MAndEngine.Utils.rand;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import MAndApps.apps.spacewars.Entity;
import MAndApps.apps.spacewars.SpaceWars;
import MAndEngine.Engine;


public class Particle extends Entity{
	private double x, y, life, speed;
	private final double angleDeg, moveLife, renderLife;
	private final int size;
	private final double DECAY;
	private boolean alive = false, bubble;
	private Random rand = new Random();
	private Color color;

	public Particle(int angle, double speed, int movelife, int renderlife,
			int x, int y, double speedDecay, int r, int g, int b, int variant,
			boolean singleVariant, boolean bubble, int sizeOfParticles) {
		renderLife = renderlife;
		this.speed = speed;
		angleDeg = angle;
		this.x = x;
		moveLife = movelife;
		this.y = y;
		alive = true;
		size = rand.nextInt(sizeOfParticles);
		DECAY = speedDecay;
		int i = 0;
		this.bubble = bubble;
		if (!bubble) {
			if (!singleVariant) {
				while (i < 10) {
					try {
						color = new Color(r + rand.nextInt(variant * 2)
								- variant, g + rand.nextInt(variant * 2)
								- variant, b + rand.nextInt(variant * 2)
								- variant);
						i = 10;
					} catch (IllegalArgumentException e) {
						i++;
						if (i >= 10) {
							color = new Color(r, g, b);
						}
					}
				}
			} else {
				while (i < 10) {
					try {
						int tempVar = rand.nextInt(variant * 2) - variant;
						color = new Color(r + tempVar, g + tempVar, b + tempVar);
						i = 10;
					} catch (IllegalArgumentException e) {
						i++;
						if (i >= 10) {
							color = new Color(r, g, b);
						}
					}
				}
			}
		} else {
			color = new Color(rand(0, 255), rand(0, 255), rand(0, 255));
		}
	}

	private static final double PHI = 1.618033988749894848204586;

	public int tick() {
		if (life < moveLife) {
			x += Math.cos(((double) angleDeg * Math.PI) / 180d) * speed;
			y += Math.sin(((double) angleDeg * Math.PI) / 180d) * speed;
		}
		speed /= PHI;
		if (life > renderLife)
			alive = false;

		if (x > SpaceWars.getWIDTH() || x < 0 - size
				|| y > SpaceWars.getHEIGHT() || y < 0 - size) {
			alive = false;
		}

		life+= Engine.deltaTime / 2d;
        return 0;
	}

	public void render(Graphics g) {
		if (alive) {
			g.setColor(color);
			if(bubble) g.drawOval((int) x, (int) y, size, size);
			else g.fillRect((int) x, (int) y, size, size);
		}
	}

	public boolean getAlive() {
		return alive;
	}

	@Override
	public boolean isCollidable() {
		// TODO Auto-generated method stub
		return false;
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
