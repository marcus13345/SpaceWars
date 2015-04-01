package MAndApps.apps.spacewars.tools;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import java.util.Stack;

import MAndApps.apps.spacewars.entity.Particle;


public class Explosion {
	private Stack<Particle> bits = new Stack<Particle>();
	private Random rand = new Random();
	private final double SPEED, DECAY;
	private final int r, g, b, variant;
	private final boolean singleVariant;
	public Explosion(double speed, double decay, int r, int g, int b, int variant, boolean singleVariant){
		SPEED = speed;
		DECAY = decay;
		this.r = r;
		this.g = g;
		this.b = b;
		this.variant = variant;
		this.singleVariant = singleVariant;
	}
	public void tick() {
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

}
