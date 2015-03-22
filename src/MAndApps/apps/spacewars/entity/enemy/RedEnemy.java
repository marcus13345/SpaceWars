package MAndApps.apps.spacewars.entity.enemy;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import MAndApps.apps.spacewars.Entity;
import MAndApps.apps.spacewars.SpaceWars;
import MAndApps.apps.spacewars.entity.Enemy;

public class RedEnemy extends Enemy {
    private int health = 2;
    private final int MAX_HEALTH = health;
    private static final int WIDTH = 16, HEIGHT = 16, PROXIMITY = 200;
    private double x, y, time = 0, desiredX, desiredY, Xmod, Ymod, dx = 0,
            dy = 0;
    private static final double ACC = 0.005, MAXSPEED = 1, DEAD_ACC = 0.5d,
            DEAD_MAXSPEED = 5;
    private Color color;
    private boolean debug = false, alive = true;
    private double healthBar = 1;

    public RedEnemy(int x, int y, int i) {
        super(x, y, WIDTH, HEIGHT);
        this.x = x;
        this.y = y;
        this.color = new Color(255, rand(50, 75), rand(50, 75));

    }

    public int tick() {
        // epic AI
        if((int)healthBar <= 0){
            alive = false;
        }
        if (alive) {
            if (SpaceWars.getPlayer().getAlive()) {
                if (time > 0.4d) {
                    time = 0;
                    Xmod = rand(-PROXIMITY, PROXIMITY);
                    Ymod = rand(-PROXIMITY, PROXIMITY);
                }

                desiredX = SpaceWars.getPlayer().getX() + Xmod;
                desiredY = SpaceWars.getPlayer().getY() + Ymod;

                if ((int) desiredX > (int) x)
                    dx += ACC;
                else if ((int) desiredX < (int) x) {
                    dx -= ACC;
                }
                if ((int) desiredY > (int) y)
                    dy += ACC;
                else if ((int) desiredY < (int) y) {
                    dy -= ACC;
                }

                if (dx > MAXSPEED)
                    while (dx > MAXSPEED)
                        dx -= ACC;
                if (dx < 0 - MAXSPEED)
                    while (dx < 0 - MAXSPEED)
                        dx += ACC;
                if (dy > MAXSPEED)
                    while (dy > MAXSPEED)
                        dy -= ACC;
                if (dy < 0 - MAXSPEED)
                    while (dy < 0 - MAXSPEED)
                        dy += ACC;

            } else {

                if (time > 0.4d) {
                    time = 0;
                    Xmod = rand(0, 1024);
                    Ymod = rand(0, 200);
                }

                desiredX = Xmod;
                desiredY = Ymod;

                if ((int) desiredX > (int) x)
                    dx += DEAD_ACC;
                else if ((int) desiredX < (int) x) {
                    dx -= DEAD_ACC;
                }
                if ((int) desiredY > (int) y)
                    dy += DEAD_ACC;
                else if ((int) desiredY < (int) y) {
                    dy -= DEAD_ACC;
                }

                if (dx > DEAD_MAXSPEED)
                    while (dx > DEAD_MAXSPEED)
                        dx -= DEAD_ACC;
                if (dx < 0 - DEAD_MAXSPEED)
                    while (dx < 0 - DEAD_MAXSPEED)
                        dx += DEAD_ACC;
                if (dy > DEAD_MAXSPEED)
                    while (dy > DEAD_MAXSPEED)
                        dy -= DEAD_ACC;
                if (dy < 0 - DEAD_MAXSPEED)
                    while (dy < 0 - DEAD_MAXSPEED)
                        dy += DEAD_ACC;

            }

            x += dx;
            y += dy;

            if (x > SpaceWars.getWIDTH() - WIDTH)
                while (x > SpaceWars.getWIDTH() - WIDTH)
                    x--;
            if (x < 0)
                while (x < 0)
                    x++;

            if (y > SpaceWars.getHEIGHT() - HEIGHT)
                while (y > SpaceWars.getHEIGHT() - HEIGHT)
                    y--;
            if (y < 0)
                while (y < 0)
                    y++;
        }
        time += 0.01;
        absoluteTime++;
        updateBoundingBox((int) x, (int) y, 16, 16);
        return 0;
    }

    private Random r = new Random();
    private int absoluteTime = 0;

    public void render(Graphics g) {
        g.setColor(color);
        int temp;
        try{
            temp = r.nextInt((int)(0-((double)absoluteTime/20d))+5);
        }catch(Exception e){
            temp = 0;
        }

        if (temp == 0) if (alive) g.fillRect((int) x, (int) y, WIDTH, HEIGHT);
        if (debug) g.drawLine((int) x, (int) y, (int) desiredX, (int) desiredY);

        healthBar += ((((double)health/(double)MAX_HEALTH)*16) - healthBar)/6;
        //healthbar
        g.setColor(Color.RED);
        g.fillRect((int)x, (int)y, WIDTH - 1, 3);
        g.setColor(Color.GREEN);
        g.fillRect((int)x, (int)y, (int)healthBar, 3);
        g.setColor(Color.BLACK);
        g.drawRect((int)x, (int)y, WIDTH - 1, 3);
    }

    @Override
    public void damage(int i) {
        health -= i;
    }

    @Override
    public boolean getAlive() {
        return alive;
    }

    @Override
    public Color getColor() {
        return color;
    }

	@Override
	public boolean isCollidable() {
		return true;
	}

	@Override
	public void die() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void collidedWith(Entity e) {
		// TODO Auto-generated method stub
		
	}
}
