package MAndApps.apps.spacewars.tools;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import MAndApps.apps.spacewars.SpaceWars;


public class CollisionParticle extends Entity {
	private double x, y, life, speed;
	private final int angleDeg, moveLife, renderLife, size;
	private final double DECAY;
	private boolean alive = false, bubble;
	private Random rand = new Random();
	private Color color;
	private final int damage;
	private boolean active = true;

	public CollisionParticle(int angle, double speed, int movelife,
			int renderlife, int x, int y, double speedDecay, int r, int g,
			int b, int variant, boolean singleVariant, boolean bubble,
			int sizeOfParticles, int damage) {
		super(0, 0, 1, 1);
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
			color = new Color(rand(0, 256), rand(0, 256), rand(0, 256));
		}
		this.damage = damage;
	}

	public int tick() {
		if (life < moveLife) {
			x += Math.cos(((double) angleDeg * Math.PI) / 180d) * speed;
			y += Math.sin(((double) angleDeg * Math.PI) / 180d) * speed;
		}
		speed /= DECAY;
		if (life > renderLife)
			alive = false;
		if(life > moveLife){
			active = false;
		}
		if (x > SpaceWars.getWIDTH() || x < 0 - size
				|| y > SpaceWars.getHEIGHT() || y < 0 - size) {
			alive = false;
		}
		life++;
		if(active){
			Rectangle r = getBoundingBox();
			for (int i = 0; i < SpaceWars.getEnemies().size(); i++) {
				if (r.intersects(SpaceWars.getEnemies().elementAt(i).getBoundingBox())) {
					SpaceWars.getEnemies().elementAt(i).damage(damage);
					//active = false;
					i = SpaceWars.getEnemies().size();
				}
			}
		}
		updateBoundingBox((int) x, (int) y, size, size);
		return 0;
	}

	public void render(Graphics g) {
		if (alive) {
			g.setColor(color);
			if (bubble)
				g.drawOval((int) x, (int) y, size, size);
			else
				g.fillRect((int) x, (int) y, size, size);
		}
	}

	public boolean getAlive() {
		return alive;
	}
}
