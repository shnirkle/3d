package renderer.shapes;

import java.awt.Color;
import java.awt.Graphics;

public class Objekt {
	private Polygon3D[] polygons;
	private Color color;
	
	public Objekt(Color color, Polygon3D... polygons) {
		this.color = color;
		this.polygons = polygons;
		this.setPolygonColor();
	}
	public Objekt( Polygon3D... polygons) {
		//this.color = Color.WHITE;
		this.polygons = polygons;
		
	}
  
	public void render (Graphics g) {
		for(Polygon3D poly : this.polygons) {
			poly.render(g);
		}
	}
	
	public void aendern(double x, double y, double z) {
		for(Polygon3D p : this.polygons) {
			p.aendern(x, y, z);
		}
	}
	
	public void rotate(double xGrad, double yGrad, double zGrad) {
		for(Polygon3D p : this.polygons) {
			p.rotate(xGrad, yGrad, zGrad);
			
		}
		this.sortieren();
	}
	private void sortieren() {
//		Polygon3D.polygonSortieren(this.polygons);
	}
	private void setPolygonColor() {
		for(Polygon3D poly : this.polygons) {
			
		}
	}
} 
