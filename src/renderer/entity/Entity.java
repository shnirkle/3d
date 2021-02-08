package renderer.entity;

import java.awt.Graphics;
import java.util.List;

import renderer.shapes.Objekt;

public class Entity implements iEntity{
	
	private List<Objekt> ws; 
	
	public Entity(List <Objekt> ws) { 
		this.ws = ws;
	}
	
	@Override
	public void render(Graphics g) {
		for( Objekt w : this.ws) {
			w.render(g);
		}
		
	}
	
	@Override
	public void aendern(double x, double y, double z) {
		for( Objekt w : this.ws) {
			w.aendern(x, y, z);
		}
	}

	@Override
	public void rotate(boolean UZ, double xGrad, double yGrad, double zGrad) {
		for( Objekt w : this.ws) {
			w.rotate(UZ, xGrad, yGrad, zGrad);
		}
		
	}

}
