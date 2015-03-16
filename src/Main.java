import MAndEngine.Engine;

/**
 * initializes an engine object that will open up spacewars.
 * @author mgosselin
 *
 */
public class Main {
	public static void main(String[] args) {
		
		Engine engine = new Engine(new String[] {"MAndApps.apps.spacewars.SpaceWars"}, false);
		engine.run();
		
	}
}
