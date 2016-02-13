package MAndApps.apps.spacewars.entity;

import java.awt.Color;

import MAndApps.apps.spacewars.Entity;
import MAndApps.apps.spacewars.SpaceWars;

public abstract class Enemy extends Entity {
	public abstract boolean getAlive();
	public abstract Color getColor();

	public abstract void damage(int damage);

	public abstract boolean isCollidable();
	
	@Override
	public final void collidedWith(Entity e) {
		if(e instanceof Bullet) {
			Bullet b = (Bullet)e;
			damage(b.getDamage());
			SpaceWars.BOOM(1.0, 1.0, 255, 255, 255, 1, (int)x, (int)y, 100, true, false, 4);
		}
	}
}
