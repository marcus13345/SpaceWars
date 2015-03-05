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
import MAndApps.apps.spacewars.tools.Explosion;
import MAndEngine.BasicApp;

public class SpaceWars implements BasicApp {

	//
	private static boolean debug = false;
	private static final int WIDTH = 1024, HEIGHT = 600;
	private static Image background;
	private static Random r = new Random();
	private static ArrayList<Enemy> entities = new ArrayList<Enemy>();
	private static Player player = new Player();
	private static ArrayList<Explosion> explosions = new ArrayList<Explosion>();

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
		
		// tick explosions
		for (int i = 0; i < explosions.size(); i++)
			explosions.get(i).tick();
		
		// tick player object
		Rectangle playerRect = player.getBoundingBox();
		if (player.getAlive())
			for (int i = 0; i < entities.size(); i++)
				if (entities.get(i).isCollidable() && entities.get(i).getBoundingBox().intersects(playerRect))
					player.collideWithEnemy(entities.get(i).getX(), entities.get(i).getY());
		
		int i = 0;
		while (i < entities.size()) {
			if (!entities.get(i).getAlive()) {
				
				BOOM( 75, 1.2, entities.get(i).getColor().getRed()-50,
				entities.get(i).getColor().getGreen()-50,
				entities.get(i).getColor().getBlue()-50, 50,
				(int)entities.get(i).getX(),
				(int)entities.get(i).getY(), 550, true, true, 10 );
				
				entities.remove(i);

			} else
				i++;
		}

		i = 0;
		while (i < explosions.size()) {
			if (!explosions.get(i).getAlive()) {
				explosions.remove(i);
			} else {
				i++;
			}
		}
	}

	public static ArrayList<Enemy> getEnemies() {
		return entities;
	}

	@Override
	public void render(Graphics2D g) {

		try {

			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);

			g.setFont(defaultFont);
			g.drawImage(background, 0, 0, WIDTH, HEIGHT, null);

			for (int i = 0; i < explosions.size(); i++)
				explosions.get(i).render(g, i);

			player.render(g);
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
		
		explosions.add(explosion);
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
