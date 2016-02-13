package MAndApps.apps.spacewars.entity;

import MAndApps.apps.spacewars.Entity;
import MAndApps.apps.spacewars.entity.bullet.BasicPlayerBullet;
import MAndApps.apps.spacewars.entity.bullet.PlayerImpactBullet;
import MAndApps.apps.spacewars.entity.bullet.PlayerPiercingBullet;
import MAndApps.apps.spacewars.tools.Direction;

public abstract class Bullet extends Entity {
	
	//these are the bullet identities. i think its fairly self explanatory..
	//public static final int NAME_OF_BULLET_TYPE = some_number_not_being_used_as_another_id;
	public static final int BASIC = 0;
	public static final int PLAYER_PIERCE_ONE = 1;
	public static final int PLAYER_PIERCE_TWO = 2;
	public static final int PLAYER_PIERCE_THREE = 3;
	public static final int PLAYER_PIERCE_FOUR = 4;
	public static final int PLAYER_PIERCE_FIVE = 5;
	public static final int PLAYER_PIERCE_SIX = 6; 
	public static final int PLAYER_PIERCE_SEVEN = 7; 
	public static final int PLAYER_PIERCE_EIGHT = 8;
	public static final int PLAYER_PIERCE_NINE = 9;
	public static final int PLAYER_PIERCE_TEN = 10; 
	public static final int PLAYER_IMPACT_ONE = 21;
	public static final int PLAYER_IMPACT_TWO = 22;
	public static final int PLAYER_IMPACT_THREE = 23;
	public static final int PLAYER_IMPACT_FOUR = 24;
	public static final int PLAYER_IMPACT_FIVE = 25;
	public static final int PLAYER_IMPACT_SIX = 26;
	public static final int PLAYER_IMPACT_SEVEN = 27;
	public static final int PLAYER_IMPACT_EIGHT = 28;
	public static final int PLAYER_IMPACT_NINE = 29;
	public static final int PLAYER_IMPACT_TEN = 30;
	public static final int PLAYER_GODMODE = 31;
	public Bullet(int x, int y, int width, int height) {
		super.x = x;
		super.y = y;
		
	}
	
	public static Bullet getNewBullet(int bulletType, int x, int y, Direction direction){
		switch(bulletType){
		//this is the literal bit that goes through and indexes what to do
		//with certain IDs that you defined above..
		//formatting is simple, cases can not go after the default so
		//they must be added in between it and the last "case"
		
		//return new PlayerPiercingBullet(direction, x, y, 2);
		
		//this returns a new pierce bullet at position x(this parameter
		//was passed via the function...) and y(also passed), with a 
		//direction, direction(also passed in..), with a pierce amount of
		//whatever is entered in place of the 2.
		//inside the parenthesis, in the returns, are called
		//"arguments" or "parameters". 
		case BASIC:
			return new BasicPlayerBullet(direction, x, y);	
		case PLAYER_IMPACT_ONE:
			return new PlayerImpactBullet(direction, x, y, 1);
		case PLAYER_IMPACT_TWO:
			return new PlayerImpactBullet(direction, x, y, 2);
		case PLAYER_IMPACT_THREE:
			return new PlayerImpactBullet(direction, x, y, 3);
		case PLAYER_IMPACT_FOUR:
			return new PlayerImpactBullet(direction, x, y, 4);
		case PLAYER_IMPACT_FIVE:
			return new PlayerImpactBullet(direction, x, y, 5);
		case PLAYER_IMPACT_SIX:
			return new PlayerImpactBullet(direction, x, y, 6);
		case PLAYER_IMPACT_SEVEN:
			return new PlayerImpactBullet(direction, x, y, 7);
		case PLAYER_IMPACT_EIGHT:
			return new PlayerImpactBullet(direction, x, y, 8);
		case PLAYER_IMPACT_NINE:
			return new PlayerImpactBullet(direction, x, y, 9);
		case PLAYER_IMPACT_TEN:
			return new PlayerImpactBullet(direction, x, y, 10);
		case PLAYER_GODMODE:
		default://if the bullet type passed in for this function is not one of the above cases..
			return new BasicPlayerBullet(direction, x, y);//return it a basic bullet.
		}
	}
	
	public abstract boolean getAlive();
	public abstract int getDamage();
    public abstract int getWIDTH();
    public abstract int getHEIGHT();
}
