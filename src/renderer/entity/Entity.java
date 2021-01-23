package renderer.entity;

import java.awt.Graphics;
import java.util.List;

import renderer.shapes.Würfel;

public class Entity implements iEntity{
	
	private List<Würfel> ws;
	
	public Entity(List <Würfel> ws) {
		this.ws = ws;
	}
	
	@Override
	public void render(Graphics g) {
		for( Würfel w : this.ws) {
			w.render(g);
		}
		
	}

	@Override
	public void rotate(boolean UZ, double xGrad, double yGrad, double zGrad) {
		for( Würfel w : this.ws) {
			w.rotate(UZ, xGrad, yGrad, zGrad);
		}
		
	}

}
