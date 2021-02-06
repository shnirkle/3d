package renderer.entity;

import java.awt.Graphics;
import java.util.List;

import renderer.shapes.W�rfel;

public class Entity implements iEntity{
	
	private List<W�rfel> ws;
	
	public Entity(List <W�rfel> ws) {
		this.ws = ws;
	}
	
	@Override
	public void render(Graphics g) {
		for( W�rfel w : this.ws) {
			w.render(g);
		}
		
	}
	
	@Override
	public void aendern(double x, double y, double z) {
		for( W�rfel w : this.ws) {
			w.aendern(x, y, z);
		}
	}

	@Override
	public void rotate(boolean UZ, double xGrad, double yGrad, double zGrad) {
		for( W�rfel w : this.ws) {
			w.rotate(UZ, xGrad, yGrad, zGrad);
		}
		
	}

}
