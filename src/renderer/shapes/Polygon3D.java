package renderer.shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import renderer.Anzeige;
import renderer.punkt.Matritze4;
import renderer.punkt.Punkt;
import renderer.punkt.PunktTransform;
import renderer.punkt.Vektor;

public class Polygon3D {

	private Color color;
	private Punkt[] punkte;
	private Punkt[] prPunkte;
	Matritze4 m = new Matritze4();
	Matritze4 rotX = new Matritze4();
	Matritze4 rotZ = new Matritze4();

	public Polygon3D(Color color, Punkt... punkte) { // Coole Notation die es erlaubt beliebig groﬂe arrays als eingabe
														// zu haben
		this.color = color;
		this.punkte = new Punkt[punkte.length];
		for (int i = 0; i < this.punkte.length; i++) {
			Punkt p = punkte[i];
			this.punkte[i] = new Punkt(p.x, p.y, p.z);

		}
		init();
	}

	private void init() {
		float near = 0.1f;
		float far = 10000f;
		double fov = 80;
		Punkt cam = new Punkt(0, 0, 0);
		float ar = Anzeige.HEIGHT / Anzeige.WIDTH;
		float invTan = (float) (1 / Math.tan(Math.toRadians(0.5f * fov)));

		this.m.projMat[0][0] = ar * invTan;
		this.m.projMat[1][1] = invTan;
		this.m.projMat[2][2] = far / (far - near);
		this.m.projMat[3][2] = (-far * near) / (far - near);
		this.m.projMat[2][3] = 1.0f;
		this.m.projMat[3][3] = 0.0f;

		// Rotation X
		this.rotX.projMat[0][0] = 1;

		this.rotX.projMat[3][3] = 1;

		// Rotation Z
		this.rotZ.projMat[2][2] = 1;
		this.rotZ.projMat[3][3] = 1;
	}

	public Polygon3D(Punkt a, Punkt b, Punkt c) {

		this.punkte = new Punkt[3];
		this.punkte[0] = a;
		this.punkte[1] = b;
		this.punkte[2] = c;
		init();

	}

	public void render(Graphics g) {
		prPunkte = new Punkt[3];
		System.out.println(punkte[0].x +" " + punkte[0].y +" " + punkte[0].z);
		Vektor a1 = new Vektor(punkte[0].x - punkte[1].x, punkte[0].y - punkte[1].y, punkte[0].z + punkte[1].z);
		System.out.println("a1:" + a1.x +" "+a1.y +" "+a1.z);
		Vektor a2 = new Vektor(punkte[0].x - punkte[2].x, punkte[0].y - punkte[2].y, punkte[0].z + punkte[2].z);
		System.out.println("a2:" + a2.x +" "+a2.y +" "+a2.z);
		Vektor a3 = Vektor.kreuzprodukt(a1, a2);
		System.out.println("a3:" + a3.x +" "+a3.y +" "+a3.z);
		Vektor a4 = new Vektor(punkte[0].x, punkte[0].y, punkte[0].z);
		System.out.println(Math.acos((Vektor.dot(a4, a3)) / (a4.length * a3.length)));

		Polygon projPoly = new Polygon();
		for (int i = 0; i < punkte.length; i++) {
			prPunkte[i] = PunktTransform.multMat(punkte[i], m);
//			System.out.println(punkte[i].x);
//			System.out.println(prPunkte[i].x);
			prPunkte[i].y *= -1;
			prPunkte[i].x += 1.0;
			prPunkte[i].y += 1.0;

			prPunkte[i].x *= 0.5 * Anzeige.WIDTH;
			prPunkte[i].y *= 0.5 * Anzeige.HEIGHT;

			projPoly.addPoint((int) prPunkte[i].x, (int) prPunkte[i].y);

		}

//		g.setColor(Color.BLUE);
//		g.fillPolygon(projPoly);
		g.setColor(Color.WHITE);
		g.drawPolygon(projPoly);
	}

	public void aendern(double x, double y, double z) {

	}

	public void rotate(boolean UZ, double xGrad, double yGrad, double zGrad) {
		if (xGrad != 0) {
			this.rotX.projMat[1][1] = (float) Math.cos(xGrad * 0.5);
			this.rotX.projMat[1][2] = (float) Math.sin(xGrad * 0.5);
			this.rotX.projMat[2][1] = (float) -Math.sin(xGrad * 0.5);
			this.rotX.projMat[2][2] = (float) Math.cos(xGrad * 0.5);
		}
		if (yGrad != 0) {

		}
		if (zGrad != 0) {
			this.rotZ.projMat[0][0] = (float) Math.cos(zGrad);
			this.rotZ.projMat[0][1] = (float) Math.sin(zGrad);
			this.rotZ.projMat[1][0] = (float) -Math.sin(zGrad);
			this.rotZ.projMat[1][1] = (float) Math.cos(zGrad);
		}

		for (int i = 0; i < this.punkte.length; i++) {
			if (xGrad != 0) {
				punkte[i] = PunktTransform.multMat(punkte[i], rotX);
			}
			if (zGrad != 0) {
				punkte[i] = PunktTransform.multMat(punkte[i], rotZ);
			}

		}
	}

	public void setColor(Color color2) {
		color = color2;

	}
}
