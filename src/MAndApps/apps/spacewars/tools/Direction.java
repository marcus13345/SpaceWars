package MAndApps.apps.spacewars.tools;

public abstract class Direction {
	public static Direction UP = new Direction() {

		@Override
		public int getX() {
			return 0;
		}

		@Override
		public Direction getOpposite() {
			return DOWN;
		}

		@Override
		public int getY() {
			// TODO Auto-generated method stub
			return -1;
		}
	};

	public static Direction DOWN = new Direction() {

		@Override
		public int getX() {
			return 0;
		}

		@Override
		public Direction getOpposite() {
			return UP;
		}

		@Override
		public int getY() {
			// TODO Auto-generated method stub
			return 1;
		}
	};

	public static Direction LEFT = new Direction() {

		@Override
		public int getX() {
			return -1;
		}

		@Override
		public Direction getOpposite() {
			return RIGHT;
		}

		@Override
		public int getY() {
			// TODO Auto-generated method stub
			return 0;
		}
	};

	public static Direction RIGHT = new Direction() {

		@Override
		public int getX() {
			return 1;
		}

		@Override
		public Direction getOpposite() {
			return LEFT;
		}

		@Override
		public int getY() {
			// TODO Auto-generated method stub
			return 0;
		}
	};

	public abstract int getX();

	public abstract Direction getOpposite();

	public abstract int getY();
}
