package renderer.shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import renderer.punkt.Punkt;
import renderer.punkt.PunktTransform;

public class Polygon3D {

	private Color color;
	private Punkt[] punkte;

	public Polygon3D(Color color, Punkt... punkte) { // Coole Notation die es erlaubt beliebig groﬂe arrays als eingabe
														// zu haben
		this.color = color;
		this.punkte = new Punkt[punkte.length];
		for (int i = 0; i < this.punkte.length; i++) {
			Punkt p = punkte[i];
			this.punkte[i] = new Punkt(p.x, p.y, p.z);

		}
	}
	public double averageD() {
		double sum = 0;
		for(Punkt pp : this.punkte) {
			sum += pp.x;
		}
		return sum / this.punkte.length;
	}
	public Polygon3D(Punkt... punkte) { // Coole Notation die es erlaubt beliebig groﬂe arrays als eingabe zu haben
		this.color = Color.WHITE;
		this.punkte = new Punkt[punkte.length];
		for (int i = 0; i < this.punkte.length; i++) {
			Punkt p = punkte[i];
			this.punkte[i] = new Punkt(p.x, p.y, p.z);

		}
	}

	public void render(Graphics g) {

		Polygon poly = new Polygon();
		for (int i = 0; i < this.punkte.length; i++) {
			Point p = PunktTransform.transformPunkt(this.punkte[i]);
//			System.out.println(punkte[i].x +" / " + punkte[i].y + " / "  + punkte[i].z + " | " + p.x + " / " + p.y);
			poly.addPoint(p.x, p.y);

		}
//		System.out.println(poly.xpoints[0] + "  |  " + poly.ypoints[0]);
		g.setColor(this.color);
		g.fillPolygon(poly);
		// g.drawPolygon(poly);
	}

	public void rotate(boolean UZ, double xGrad, double yGrad, double zGrad) {
		for (Punkt p : punkte) {
			PunktTransform.rotateAxisX(p, UZ, xGrad);
			PunktTransform.rotateAxisY(p, UZ, yGrad);
			PunktTransform.rotateAxisZ(p, UZ, zGrad);
		}
	}

	public void setColor(Color color2) {
		color = color2;

	}

	public static Polygon3D[] polygonSortieren(Polygon3D[] p) {
		List<Polygon3D> polygonsList = new ArrayList<Polygon3D>();

		for (Polygon3D poly : p) {
			polygonsList.add(poly);
		}
		Collections.sort(polygonsList, new Comparator<Polygon3D>() {
			@Override
			public int compare(Polygon3D p1, Polygon3D p2) {
				return p2.averageD() -p1.averageD() < 0 ? 1 : -1;			}
		});
		
		for(int i = 0; i < p.length; i++) {
			p[i] = polygonsList.get(i);
		}
		return p;
	}
}
