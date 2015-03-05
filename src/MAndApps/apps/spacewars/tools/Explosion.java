package MAndApps.apps.spacewars.tools;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import java.util.Stack;

import MAndApps.apps.spacewars.Entity;
import MAndApps.apps.spacewars.Particle;


public class Explosion extends Entity{
	private Stack<Particle> bits = new Stack<Particle>();
	private Random rand = new Random();
	private final double SPEED, DECAY;
	private final int r, g, b, variant;
	private final boolean singleVariant;
	public Explosion(double speed, double decay, int r, int g, int b, int variant, boolean singleVariant){
		super(0, 0, 1, 1);
		SPEED = speed;
		DECAY = decay;
		this.r = r;
		this.g = g;
		this.b = b;
		this.variant = variant;
		this.singleVariant = singleVariant;
	}
	
	public int tick() {
		for(int i = 0; i < bits.size(); i++)
			bits.elementAt(i).tick();
		int i = 0;
		while(i < bits.size()){
			if(!bits.elementAt(i).getAlive()){
				bits.remove(i);
			}else{
				i++;
			}
		}
		return 0;
	}

	public void render(Graphics g, int line) {
		for(int i = 0; i < bits.size(); i++)
			bits.elementAt(i).render(g);
		g.setColor(Color.WHITE);
		g.drawString("particles: " + bits.size(), 920 - (int)(Math.floor((double)line/20) * 100), 15 + ((line%20)*12));
	}

	public void reset() {
		bits.clear();
	}

	public void goBoom(double x, double y, int size, boolean bubble, int sizeOfParticles) {
		for(int i = 0; i < size; i++){
			bits.push(
					new Particle(
							rand.nextInt(360), 
							(rand.nextDouble()*1000)%SPEED, 
							rand.nextInt(100), 
							rand.nextInt(50) + 100, 
							(int)x, 
							(int)y, 
							DECAY, 
							r, 
							g, 
							b, 
							variant, 
							singleVariant,
							bubble, 
							sizeOfParticles
					)
			);
		}
	}
	
	public boolean getAlive(){
		boolean alive = false;
		for(int i = 0; i < bits.size(); i++)
			if(bits.elementAt(i).getAlive())
				alive = true;
		return alive;
	}
	@Override
	public boolean isCollidable() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		
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
