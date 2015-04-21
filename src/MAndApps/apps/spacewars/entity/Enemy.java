package MAndApps.apps.spacewars.entity;

import java.awt.Color;

import MAndApps.apps.spacewars.Entity;

public abstract class Enemy extends Entity {
	public abstract boolean getAlive();
	public abstract Color getColor();

	public abstract void damage(int damage);

	public abstract boolean isCollidable();
	
	@Override
	public void collidedWith(Entity e) {
		if(e instanceof Bullet) {
			Bullet b = (Bullet)e;
			damage(b.getDamage());
		}
	}
}
