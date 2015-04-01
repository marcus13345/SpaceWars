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
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Stack;

import javax.imageio.ImageIO;

import MAndApps.apps.spacewars.entity.Enemy;
import MAndApps.apps.spacewars.entity.Player;
import MAndApps.apps.spacewars.shop.Shop;
import MAndApps.apps.spacewars.tools.Explosion;
import MAndEngine.BasicApp;
import MAndEngine.Engine;

public class SpaceWars implements BasicApp {

	//
	private static boolean paused = false, debug = false;
	private static int time = 0;
	private static int WIDTH = 1024, HEIGHT = 600;
	private static Player player = new Player();
	private static Image background;
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
					
					log("You gained " + enemies.elementAt(i).getWorth() + " exp.");
					addEXP(enemies.elementAt(i).getWorth());

					// enemies.remove(i);

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
			g.drawImage(background, 0, 0, null);

			for (int i = 0; i < explosions.size(); i++)
				explosions.elementAt(i).render(g, i);

			player.render(g);
			for (int i = 0; i < enemies.size(); i++)
				enemies.elementAt(i).render(g);

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
		System.out.println("WIDTHasdf: " + WIDTH);
		System.out.println("HEIGHTsdf: " + HEIGHT);
		return new Dimension(WIDTH = 1024, HEIGHT = 600);
	}

	@Override
	public void initialize() {
		try {
			background = ImageIO.read(new FileInputStream(new File("res/background.png")));
			
		} catch (Exception e) {
			background = (Image) new BufferedImage(1024, 600, BufferedImage.TRANSLUCENT);
			Graphics g = background.getGraphics();
			g.setColor(Color.BLUE);
			g.fillRect(0, 0, 3000, 2000);
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

	@Override
	public void updateDimensions(int width, int height) {
		System.out.println("" + WIDTH);
		System.out.println("" + HEIGHT);
		WIDTH = width;
		HEIGHT = height;
	}
}
