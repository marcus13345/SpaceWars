package MAndApps.apps.spacewars;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Random;
import java.util.Stack;

import javax.imageio.ImageIO;

import MAndApps.apps.spacewars.entity.Enemy;
import MAndApps.apps.spacewars.entity.Player;
import MAndApps.apps.spacewars.shop.Shop;
import MAndApps.apps.spacewars.tools.Explosion;
import MAndEngine.BasicApp;
import MAndEngine.Engine;

/**
 * main basicapp class that takes care of managing the abstract concepts of the game. 
 * like the shop, the player's level and experience, what enemies and explosion particles
 * are laying around.
 * 
 * this is somewhat old architecture and some half finished new architecture can be found
 * in the screensaver branch as i plan to make this both a game and a screen saver with
 * a player AI. as well, some of these concepts will be ported over to mand engine
 * once they are abstracted a little better.
 * 
 * @author mgosselin
 *
 */
public class SpaceWars implements BasicApp {

	private static boolean paused = false, debug = false;
	private static int redPoints = 0, bluePoints = 0, greenPoints = 0, time = 0;
	private static final int WIDTH = 1024, HEIGHT = 600;
	private static Player player = new Player();
	private static Image background;
	private static Random r = new Random();
	private static Stack<Enemy> enemies = new Stack<Enemy>();
	private static Stack<Explosion> explosions = new Stack<Explosion>();
	private static Stack<String> logs = new Stack<String>();
	private static int lvl = 1, xpToNextLVL = getMaxXPForLvl(lvl), xp = 0, expBar = 424;
	private static boolean shopping = false;
	private Shop shop = new Shop();

	//
	public static final Font defaultFont = new Font("Ubuntu", Font.BOLD, 10);
	public static final Font moneyFont = new Font("Ubuntu", Font.BOLD, 20);
	public static final Font levelFont = new Font("Ubuntu", Font.BOLD, 40);
	public static final Font pausedFont = new Font("Ubuntu", Font.BOLD, 60);

	@Override
	public void tick() {
		//addEXP(1);
		if (!paused && !shopping) {
			time++;
			// ticks enemy stack
			for (int i = 0; i < enemies.size(); i++)
				enemies.elementAt(i).tick();
			// tick explosions
			for (int i = 0; i < explosions.size(); i++)
				explosions.elementAt(i).tick();
			// tick player class
			player.tick();
			Rectangle playerRect = player.getBoundingBox();
			if (player.getAlive())
				for (int i = 0; i < enemies.size(); i++)
					if (enemies.elementAt(i).getBoundingBox().intersects(playerRect))
						player.collideWithEnemy(enemies.elementAt(i).getX(), enemies.elementAt(i).getY());
			int i = 0;
			while (i < enemies.size()) {
				if (!enemies.elementAt(i).getAlive()) {
					
					 BOOM( 75, 1.2,
					 enemies.elementAt(i).getColor().getRed()-50,
					 enemies.elementAt(i).getColor().getGreen()-50,
					 enemies.elementAt(i).getColor().getBlue()-50, 50,
					 (int)enemies.elementAt(i).getX(),
					 (int)enemies.elementAt(i).getY(), 550, true, true, 10 );
					 

					addRedPoints(enemies.elementAt(i).getColor().getRed());
					addGreenPoints(enemies.elementAt(i).getColor().getGreen());
					addBluePoints(enemies.elementAt(i).getColor().getBlue());
					log("You gained " + enemies.elementAt(i).getWorth() + " exp.");
					addEXP(enemies.elementAt(i).getWorth());

					enemies.remove(i);

				} else
					i++;
			}

			i = 0;
			while (i < explosions.size()) {
				if (!explosions.elementAt(i).getAlive()) {
					explosions.remove(i);
				} else {
					i++;
				}
			}

			expBar += ((int) (((double) xp / (double) xpToNextLVL) * 424) - expBar) / 10;
		}
		shop.tick();
		// check if there are any new items to log
		int cap = logs.size();
		for (int i = 0; i < cap; i++)
			Engine.log(logs.pop());
	}

	public static void log(String s) {
		logs.push(s);
	}

	public static Stack<Enemy> getEnemies() {
		return enemies;
	}

	@Override
	public void render(Graphics2D g) {

		try {

			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

			g.setFont(defaultFont);
			g.drawImage(background, 0, 0, WIDTH, HEIGHT, null);

			for (int i = 0; i < explosions.size(); i++)
				explosions.elementAt(i).render(g, i);

			player.render(g);
			for (int i = 0; i < enemies.size(); i++)
				enemies.elementAt(i).render(g);

			// render points
			g.setFont(moneyFont);
			g.setColor(Color.RED);
			g.drawString(redPointsToString(), 10, HEIGHT - 10 - (20 * 3));
			g.setColor(Color.GREEN);
			g.drawString(greenPointsToString(), 10, HEIGHT - 10 - (20 * 2));
			g.setColor(Color.BLUE);
			g.drawString(bluePointsToString(), 10, HEIGHT - 10 - (20 * 1));
			g.setFont(defaultFont);

			// render level and xp bar.
			g.setFont(levelFont);
			g.setColor(Color.WHITE);
			g.drawString("" + lvl, 300 - ((("" + lvl).length() - 1) * 20), 40);
			g.drawRect(330, 18, 424, 15);
			g.fillRect(330, 18, expBar, 15);
			g.setFont(defaultFont);

			if (paused) {
				g.setFont(pausedFont);
				g.setColor(Color.WHITE);
				g.drawString("Paused", 410, 280);
				g.setFont(defaultFont);
			}
			shop.render(g, WIDTH);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void resumeApp() {

	}

	@Override
	public void pauseApp() {

	}

	public static Player getPlayer() {
		return player;
	}

	public boolean getDebug() {
		return debug;
	}

	@Override
	public Dimension getResolution() {
		return new Dimension(WIDTH, HEIGHT);
	}

	@Override
	public void initialize() {
		try {

			for(int i = 0; i < 10; i ++)
				enemies.add(Enemy.getNewEnemy(Enemy.NORMAL, 0, 0));
			background = ImageIO.read(new URL("http://wallpaperswiki.org/wallpapers/2012/11/Wallpaper-Abstract-Wallpaper-Background-Texture-Texture-Yellow-Pictures-600x1024.jpg"));
		} catch (Exception e) {
			background = (Image) new BufferedImage(1024, 600, BufferedImage.TRANSLUCENT);
			Graphics g = background.getGraphics();
			g.setColor(Color.BLUE);
			g.fillRect(0, 0, 1024, 600);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		player.keyPressed(e);
		for (int i = 0; i < enemies.size(); i++)
			enemies.elementAt(i).keyPressed(e);
		if (e.getKeyCode() == KeyEvent.VK_P || e.getKeyCode() == KeyEvent.VK_SPACE) {
			paused = !paused;
		} else if (e.getKeyCode() == KeyEvent.VK_E) {
			xp = xpToNextLVL - 1;
		} else if (e.getKeyCode() == KeyEvent.VK_Q) {
			System.out.println("YOEIRGSODBH");
			debug = !debug;
		} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			shopping = !shopping;
			shop.toggleState();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		player.keyReleased(e);
		for (int i = 0; i < enemies.size(); i++)
			enemies.elementAt(i).keyReleased(e);
		shop.keyReleased(e);
	}

	@Override
	public String getTitle() {
		return "Space Wars";
	}

	@Override
	public Color getColor() {
		return new Color(88, 128, 255);
	}

	@Override
	public int getFramerate() {
		return 50;
	}

	@Override
	public boolean getResizable() {
		return false;
	}

	public static void BOOM(double speed, double decay, int r, int g, int b, int variant, int x, int y, int size, boolean singleVariant, boolean bubble, int sizeOfParticles) {
		explosions.push(new Explosion(speed, decay, r, g, b, variant, singleVariant));
		explosions.peek().goBoom(x, y, size, bubble, sizeOfParticles);
	}

	public void addRedPoints(int d) {
		redPoints += d;
	}

	public void addGreenPoints(int d) {
		greenPoints += d;
	}

	public void addBluePoints(int d) {
		bluePoints += d;
	}

	private String redPointsToString() {
		String _return = "$" + redPoints / 100d;
		if (_return.length() == ("" + redPoints).length() + 1 || _return.length() == ("" + redPoints).length() + 3)
			_return += "0";
		return _return;
	}

	private String greenPointsToString() {
		String _return = "$" + greenPoints / 100d;
		if (_return.length() == ("" + greenPoints).length() + 1 || _return.length() == ("" + greenPoints).length() + 3)
			_return += "0";
		return _return;
	}

	private String bluePointsToString() {
		String _return = "$" + bluePoints / 100d;
		if (_return.length() == ("" + bluePoints).length() + 1 || _return.length() == ("" + bluePoints).length() + 3)
			_return += "0";
		return _return;
	}

	private static int getMaxXPForLvl(int lvl) {
		return (int) (Math.pow(lvl, 1.618));
	}

	public void addEXP(int i) {
		xp += i;
		while (xp >= xpToNextLVL) {
			xp -= xpToNextLVL;
			lvl++;
			xpToNextLVL = getMaxXPForLvl(lvl);
			for (int j = 0; j < 424; j += 100) {
				BOOM(75, 1.618, 255, 255, 255, 0, 300 + (j), 20, 200, true, false, 2);
			}
		}
	}

	// i got bored and made logic methods... so?

	public boolean or(boolean a, boolean b) {
		return a || b;
	}

	public boolean not(boolean a) {
		return !a;
	}

	public boolean and(boolean a, boolean b) {
		return not(or(not(a), not(b)));
	}

	public boolean xor(boolean a, boolean b) {
		return not(or(not(or(not(a), not(b))), not(or(a, b))));
		// return !((!((!a)||(!b)))||(!(a||b)));
		// return or(not(or(not(a),b)),not(or(not(b),a)));
		// return ((!(a||!(b)))||(!(b||!(a))));
	}

	@Override
	public boolean visibleInMenu() {
		// TODO Auto-generated method stub
		return true;
	}

	public static int getHEIGHT() {
		return HEIGHT;
	}

	public static int getWIDTH() {
		return WIDTH;
	}

	@Override
	public void resized(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void click() {
		// TODO Auto-generated method stub

		BOOM(75, 1.2, 100, 100, 100, 50, Engine.mouseX, Engine.mouseY, 550, true, true, 10);

	}
}
