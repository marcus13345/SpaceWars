import MAndEngine.Engine;


public class Main {
	public static void main(String[] args) {
		
		Engine engine = new Engine(new String[] {"MAndApps.apps.spacewars.SpaceWars"}, false, true);
		engine.run();
		
	}
}
