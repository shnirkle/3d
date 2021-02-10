package renderer.shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import renderer.Anzeige;
import renderer.punkt.Matrix;
import renderer.punkt.Punkt;
import renderer.punkt.Vektor;

public class Polygon3D {
	private Vektor licht = new Vektor(0, 0, -1);
	private Vektor cam = new Vektor(0, 0, 0);
	private Vektor[] punkte;
	private Vektor[] prPunkte;
	Vektor[] versPunkte = new Vektor[3];

	Matrix rotX = new Matrix();
	Matrix rotY = new Matrix();
	Matrix rotZ = new Matrix();

	private double xGrad = 0;
	private double yGrad = 0;
	private double zGrad = 0;
	private float xOff;
	private float yOff = 0;
	private float zOff = 3;

	public Polygon3D(Punkt a, Punkt b, Punkt c) {

		this.punkte = new Vektor[3];
		this.punkte[0] = Punkt.toVektor(a);
		this.punkte[1] = Punkt.toVektor(b);
		this.punkte[2] = Punkt.toVektor(c);

	}

	public void render(Graphics g) {
		
		Vektor[] xRotiert = Matrix.rotateAxisX(this.punkte, xGrad);
		Vektor[] yRotiert = Matrix.rotateAxisY(xRotiert, yGrad);
		Vektor[] zRotiert = Matrix.rotateAxisZ(yRotiert, zGrad);
		
		versPunkte = zRotiert;
		versPunkte[0].z += zOff;
		versPunkte[1].z += zOff;
		versPunkte[2].z += zOff;
		versPunkte[0].x += xOff;
		versPunkte[1].x += xOff;
		versPunkte[2].x += xOff;
		versPunkte[0].y += yOff;
		versPunkte[1].y += yOff;
		versPunkte[2].y += yOff;

		Vektor v_0_1 = new Vektor(0, 0, 0);
		v_0_1.setX(versPunkte[1].x - versPunkte[0].x);
		v_0_1.setY(versPunkte[1].y - versPunkte[0].y);
		v_0_1.setZ(versPunkte[1].z - versPunkte[0].z);
		Vektor v_0_2 = new Vektor(0, 0, 0);
		v_0_2.setX(versPunkte[2].x - versPunkte[0].x);
		v_0_2.setY(versPunkte[2].y - versPunkte[0].y);
		v_0_2.setZ(versPunkte[2].z - versPunkte[0].z);

		Vektor normale = Vektor.kreuzprodukt(v_0_1, v_0_2);

		if (normale.normX * (versPunkte[0].x - this.cam.x) + normale.normY * (versPunkte[0].y - this.cam.y) + normale.normZ * (versPunkte[0].z - this.cam.z) < 0.0f) {

			float lae = (float) (normale.normX * licht.normX + normale.normY * licht.normY + normale.normZ * licht.normZ);

			lae = Math.abs(lae);
			if (lae < 0) {
				lae = 0;
			}
			if (lae >= 1) { 
				lae = 1;
			}
			lae *= Math.PI;
			lae = (float) Math.sin(lae / 2);
			lae += 0.3f;
			lae /= 2;
			Color shade = new Color(lae, lae, lae);
			prPunkte = new Vektor[3];

			// Projektion
			prPunkte[0] = Matrix.multMat(versPunkte[0], Matrix.projectionMatrix);
			prPunkte[1] = Matrix.multMat(versPunkte[1], Matrix.projectionMatrix);
			prPunkte[2] = Matrix.multMat(versPunkte[2], Matrix.projectionMatrix);
			
			prPunkte[0].multvec(1/prPunkte[0].w);
			prPunkte[1].multvec(1/prPunkte[1].w);
			prPunkte[2].multvec(1/prPunkte[2].w);
			
			prPunkte[0].x += 1.0;
			prPunkte[0].y += 1.0;
			prPunkte[1].x += 1.0;
			prPunkte[1].y += 1.0;
			prPunkte[2].x += 1.0;
			prPunkte[2].y += 1.0;

			prPunkte[0].x *= 0.5f * Anzeige.WIDTH;
			prPunkte[0].y *= 0.5f * Anzeige.HEIGHT;
			prPunkte[1].x *= 0.5f * Anzeige.WIDTH;
			prPunkte[1].y *= 0.5f * Anzeige.HEIGHT;
			prPunkte[2].x *= 0.5f * Anzeige.WIDTH;
			prPunkte[2].y *= 0.5f * Anzeige.HEIGHT;

			Polygon dreieck = new Polygon();
			dreieck.addPoint((int) prPunkte[0].x, (int) prPunkte[0].y);
			dreieck.addPoint((int) prPunkte[1].x, (int) prPunkte[1].y);
			dreieck.addPoint((int) prPunkte[2].x, (int) prPunkte[2].y);
			g.setColor(shade);

			g.fillPolygon(dreieck);
			g.setColor(Color.BLACK);
			g.drawPolygon(dreieck);

		}

	}

	public void aendern(double x, double y, double z) {
		xOff = (float) x;
		yOff = (float) y;
		zOff = (float) z;
	}

	public void rotate(double xGrad, double yGrad, double zGrad) {
		this.xGrad = xGrad;
		this.yGrad = yGrad;
		this.zGrad = zGrad;
	}
}
