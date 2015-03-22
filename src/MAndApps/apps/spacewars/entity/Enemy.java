package MAndApps.apps.spacewars.entity;

import java.awt.Color;

import MAndApps.apps.spacewars.entity.enemy.BlueEnemy;
import MAndApps.apps.spacewars.entity.enemy.GreenEnemy;
import MAndApps.apps.spacewars.entity.enemy.NormalEnemy;
import MAndApps.apps.spacewars.entity.enemy.RedEnemy;
import MAndApps.apps.spacewars.Entity;
import MAndApps.apps.spacewars.SpaceWars;

public abstract class Enemy extends Entity {
	public static final int NORMAL = 0;
	public static final int
		RED_TIER_ONE = 1, 
		RED_TIER_TWO = 2, 
		RED_TIER_THREE = 3, 
		RED_TIER_FOUR = 4, 
		RED_TIER_FIVE = 5;
	public static final int
		BLUE_TIER_ONE = 6, 
		BLUE_TIER_TWO = 7, 
		BLUE_TIER_THREE = 8, 
		BLUE_TIER_FOUR = 9, 
		BLUE_TIER_FIVE = 10;
	public static final int
		GREEN_TIER_ONE = 11, 
		GREEN_TIER_TWO = 12, 
		GREEN_TIER_THREE = 13, 
		GREEN_TIER_FOUR = 14, 
		GREEN_TIER_FIVE = 15;
	
	public Enemy(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	
	public final static Enemy getNewEnemy(int type, int x, int y){
		switch(type){
		case NORMAL:
			return new NormalEnemy(x, y);
		case BLUE_TIER_ONE:
			return new BlueEnemy(x, y, 2);
        case RED_TIER_ONE:
            return new RedEnemy(x, y, 2);
        case GREEN_TIER_ONE:
            return new GreenEnemy(x, y, 2);
		default:
			return new NormalEnemy(x, y);
		}
	}
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
