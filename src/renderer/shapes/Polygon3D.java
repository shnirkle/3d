package renderer.shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;

import renderer.punkt.Punkt;
import renderer.punkt.PunktTransform;

public class Polygon3D {

	private Color color;
	private Punkt[] punkte;
	
	public Polygon3D(Color color , Punkt... punkte) { //Coole Notation die es erlaubt beliebig groﬂe arrays als eingabe zu haben
		this.color = color;
		this.punkte = new Punkt[punkte.length];
		for(int i = 0; i < this.punkte.length; i++) {
			Punkt p = punkte[i];
			this.punkte[i] = new Punkt(p.y, p.y, p.z);
			System.out.println(this+" | "+ this.punkte[i].x +" / " + + this.punkte[i].y);
		}
	}

	

	public Polygon3D(Punkt... punkte) { //Coole Notation die es erlaubt beliebig groﬂe arrays als eingabe zu haben
		this.color = Color.WHITE;
		this.punkte = new Punkt[punkte.length];
		for(int i = 0; i < this.punkte.length; i++) {
			Punkt p = punkte[i];
			this.punkte[i] = new Punkt(p.y, p.y, p.z);
			
		}
	}
	public void render(Graphics g) {
		
		Polygon poly = new Polygon();
		for(int i = 0;  i< punkte.length; i++) {
			Point p = PunktTransform.transformPunkt(this.punkte[i]);
			poly.addPoint(p.x, p.y);
		}
		g.setColor(this.color);
		g.fillPolygon(poly);
	}


	public void setColor(Color color2) {
		color = color2;
		
	}

}
