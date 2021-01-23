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
		//this.color = Color.WHITE;
		this.polygons = polygons;
		
	}
  
	public void render (Graphics g) {
		for(Polygon3D poly : this.polygons) {
			poly.render(g);
		}
	}
	public void rotate(boolean UZ, double xGrad, double yGrad, double zGrad) {
		for(Polygon3D p : this.polygons) {
			p.rotate(UZ, xGrad, yGrad, zGrad);
			
		}
		this.sortieren();
	}
	private void sortieren() {
		Polygon3D.polygonSortieren(this.polygons);
	}
	private void setPolygonColor() {
		for(Polygon3D poly : this.polygons) {
			poly.setColor(color);
		}
	}
} 
