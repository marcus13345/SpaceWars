package MAndApps.apps.spacewars;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


import MAndApps.apps.spacewars.entity.Player;
import MAndApps.apps.spacewars.entity.enemy.NormalEnemy;
import MAndApps.apps.spacewars.tools.Explosion;
import MAndEngine.BasicApp;
import MAndEngine.Engine;
import MAndEngine.ImageCreator;


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

	//
	public static boolean debug = false;
	private static final int ORIGINAL_WIDTH = 1024, ORIGINAL_HEIGHT = 600;
	private static Image background;
	private static ArrayList<Entity> entities = new ArrayList<Entity>();
	private static Player player;


	//
	public static final Font defaultFont = new Font("Ubuntu", Font.BOLD, 10);
	public static final Font moneyFont = new Font("Ubuntu", Font.BOLD, 20);
	public static final Font levelFont = new Font("Ubuntu", Font.BOLD, 40);
	public static final Font pausedFont = new Font("Ubuntu", Font.BOLD, 60);
	private boolean paused;
	private static int WIDTH = ORIGINAL_WIDTH;
	private static int HEIGHT = ORIGINAL_HEIGHT;
	public static int scale;

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
					if (e2.isCollidable()) {

						// because efficiency.
						if(
								//x width2 collide
								(((e1.getX() > e2.getX()) & (e1.getX() < e2.getX() + e2.getWidth())) ||
								
								//x2 width collide
								((e2.getX() > e1.getX()) & (e2.getX() < e1.getX() + e1.getWidth()))) &&
								
								//y height2 collide
								(((e1.getY() > e2.getY()) & (e1.getY() < e2.getY() + e2.getHeight())) ||
										
								//y2 height collide
								((e2.getY() > e1.getY()) & (e2.getY() < e1.getY() + e1.getHeight())))								
								
						) {
						e1.collidedWith(e2);
						e2.collidedWith(e1);
						}
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
				g.drawImage(background, 0, 0, null);

			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

			g.setFont(defaultFont);

			for (int i = 0; i < entities.size(); i++)
				entities.get(i).render(g);


			// render level and xp bar.
			g.setFont(levelFont);
			g.setColor(Color.WHITE);
			g.setFont(defaultFont);
			
			if (paused) {
				g.setFont(pausedFont);
				g.setColor(Color.WHITE);
				g.drawString("Paused", 410, 280);
				g.setFont(defaultFont);
			}

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
		return new Dimension(ORIGINAL_WIDTH, ORIGINAL_HEIGHT);
	}

	@Override
	public void initialize() {
		try {
			player = new Player();
			entities.add(player);
			for(int i = 0; i < 100; i ++)
				entities.add(new NormalEnemy());
			
			Engine.timeScale = 60d / (1000d * 1000d);
			background = ImageCreator.colorNoise(Color.WHITE, .4, .6, WIDTH, HEIGHT );
			
			
		} catch (Exception e) {
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		for (int i = 0; i < entities.size(); i++)
			entities.get(i).keyPressed(e);
		if (e.getKeyCode() == KeyEvent.VK_P || e.getKeyCode() == KeyEvent.VK_SPACE) {
			paused = !paused;
		} else if (e.getKeyCode() == KeyEvent.VK_Q) {
			System.out.println("YOEIRGSODBH");
			debug = !debug;
		}
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

	@Override
	public void updateDimensions(int width, int height) {
		scale = ((int)(Math.sqrt(width*width + height*height)))/((int)(Math.sqrt(WIDTH*WIDTH + HEIGHT*HEIGHT)));
		WIDTH = width;
		HEIGHT = height;
	}
}
