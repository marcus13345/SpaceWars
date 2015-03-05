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
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import MAndApps.apps.spacewars.entity.Enemy;
import MAndApps.apps.spacewars.entity.Player;
import MAndApps.apps.spacewars.entity.enemy.NormalEnemy;
import MAndApps.apps.spacewars.entity.enemy.RedEnemy;
import MAndApps.apps.spacewars.tools.Explosion;
import MAndEngine.BasicApp;

public class SpaceWars implements BasicApp {

	//
	private static boolean debug = false;
	private static final int WIDTH = 1024, HEIGHT = 600;
	private static Image background;
	private static ArrayList<Entity> entities = new ArrayList<Entity>();
	private static Player player = new Player();

	//
	public static final Font defaultFont = new Font("Ubuntu", Font.BOLD, 10);
	public static final Font moneyFont = new Font("Ubuntu", Font.BOLD, 20);
	public static final Font levelFont = new Font("Ubuntu", Font.BOLD, 40);
	public static final Font pausedFont = new Font("Ubuntu", Font.BOLD, 60);

	@Override
	public void tick() {

		// ticks enemies
		for (int i = 0; i < entities.size(); i++)
			entities.get(i).tick();

		// check dem collisions yo
		for (int i = 0; i < entities.size(); i++) {
			Entity e1 = entities.get(i);
			if (e1.isCollidable()) {
				for (int j = i + 1; j < entities.size(); j++) {
					Entity e2 = entities.get(j);
					if(e2.isCollidable()) {
						
						//because efficiency.
						e1.collidedWith(e2);
						e2.collidedWith(e1);
						
					}
				}
			}

		}

		// cleanup method.
		for (int i = 0; i < entities.size();) {
			Entity entity = entities.get(i);
			if (!entity.getAlive()) {
				entity.die();
				entities.remove(i);
			} else
				i++;
		}
	}

	public static ArrayList<Entity> getEnemies() {
		return entities;
	}

	@Override
	public void render(Graphics2D g) {

		try {

			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);

			g.setFont(defaultFont);
			g.drawImage(background, 0, 0, WIDTH, HEIGHT, null);
			
			for (int i = 0; i < entities.size(); i++)
				entities.get(i).render(g);

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
			background = ImageIO.read(new URL("http://wallpaperswiki.org/wallpapers/2012/11/Wallpaper-Abstract-Wallpaper-Background-Texture-Texture-Yellow-Pictures-600x1024.jpg"));
			entities.add(player);
			entities.add(new NormalEnemy(0, 0));
			entities.add(new NormalEnemy(0, 0));
			entities.add(new NormalEnemy(0, 0));
		} catch (Exception e) {
			background = (Image) new BufferedImage(1024, 600, BufferedImage.TRANSLUCENT);
			Graphics g = background.getGraphics();
			g.setColor(Color.BLUE);
			g.fillRect(0, 0, 1024, 600);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {
		player.keyReleased(e);
		for (int i = 0; i < entities.size(); i++)
			entities.get(i).keyReleased(e);
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

		Explosion explosion = new Explosion(speed, decay, r, g, b, variant, singleVariant);

		entities.add((Entity) explosion);
		explosion.goBoom(x, y, size, bubble, sizeOfParticles);

	}

	@Override
	public boolean visibleInMenu() {
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

	}

	@Override
	public void click() {

	}
}
