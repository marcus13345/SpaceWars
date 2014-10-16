package MAndApps.apps.spacewars.tools;

public class Direction {
	private final int direction;
	public static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3;
	public Direction(int i){
		direction = i;
	}
	public int getDirection() {
		return direction;
	}
}
