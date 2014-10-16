package MAndApps.apps.spacewars.tools;

import java.awt.Graphics;
import java.util.Random;
import java.util.Stack;


public class BulletExplosion {
	private Stack<CollisionParticle> bits = new Stack<CollisionParticle>();
	private Random rand = new Random();
	private final double SPEED, DECAY;
	private final int r, g, b, variant;
	private final boolean singleVariant;
	private final int damage;
	public BulletExplosion(double speed, double decay, int r, int g, int b, int variant, boolean singleVariant, int damage){
		SPEED = speed;
		DECAY = decay;
		this.r = r;
		this.g = g;
		this.b = b;
		this.variant = variant;
		this.singleVariant = singleVariant;
		this.damage = damage;
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

	public void render(Graphics g) {
		for(int i = 0; i < bits.size(); i++)
			bits.elementAt(i).render(g);
		//g.setColor(Color.WHITE);
		//g.drawString("particles: " + bits.size(), 920 - (int)(Math.floor((double)line/20) * 100), 15 + ((line%20)*12));
	}

	public void reset() {
		bits.clear();
	}

	public void goBoom(double x, double y, int size, boolean bubble, int sizeOfParticles) {
		for(int i = 0; i < size; i++){
			bits.push(
					new CollisionParticle(
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
							sizeOfParticles, 
							damage
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
