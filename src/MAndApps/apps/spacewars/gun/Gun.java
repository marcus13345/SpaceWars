package MAndApps.apps.spacewars.gun;

import MAndApps.apps.spacewars.SpaceWars;
import MAndApps.apps.spacewars.entity.Bullet;
import MAndApps.apps.spacewars.tools.Direction;

public class Gun {
	private int MAX_COOLDOWN;
	private double cooldown = 0;
	public Gun(int bulletType){
		MAX_COOLDOWN = 5;
	}
	
	public void tick(){
		cooldown += MAndEngine.Engine.deltaTime;
		
	}
	
	public void shoot(Direction direction){
		if(cooldown >= MAX_COOLDOWN){
			cooldown = 0;
			SpaceWars.addEntity(Bullet.getNewBullet(Bullet.BASIC, SpaceWars.getPlayer().getCenterX(), SpaceWars.getPlayer().getCenterY(), direction));
		}
	}
}