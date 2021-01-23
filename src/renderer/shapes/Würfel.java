package renderer.shapes;

import java.awt.Color;
import java.awt.Graphics;

public class Würfel {
	private Polygon3D[] polygons;
	private Color color;
	
	public Würfel(Color color, Polygon3D... polygons) {
		this.color = color;
		this.polygons = polygons;
		this.setPolygonColor();
	}
	public Würfel( Polygon3D... polygons) {
		this.color = color.WHITE;
		this.polygons = polygons;
		//this.setPolygonColor();
	}

	public void render (Graphics g) {
		for(Polygon3D poly : this.polygons) {
			poly.render(g);
		}
	}

	private void sortieren() {
		;
	}
	private void setPolygonColor() {
		for(Polygon3D poly : this.polygons) {
			poly.setColor(color);
		}
	}
} 
