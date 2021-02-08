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
	private Vektor licht = new Vektor(0, -1, 0);
	private Color color;
	private Punkt[] punkte, versPunkte, prPunkte, rotPunkteX, rotPunkteXY, rotPunkteXYZ;
	private Punkt cam;
	private float xRot, yRot, zRot;

	Matritze4 m = new Matritze4();
	Matritze4 rotX = new Matritze4();
	Matritze4 rotY = new Matritze4();
	Matritze4 rotZ = new Matritze4();
	private float xOff;
	private float yOff;
	private float zOff = 3;
	
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
		this.cam = new Punkt(0, 0, 0);
		float ar = Anzeige.HEIGHT / Anzeige.WIDTH;
		float invTan = (float) (1 / Math.tan(Math.toRadians(0.5f * fov)));

		this.m.projMat[0][0] = ar * invTan;
		this.m.projMat[1][1] = invTan;
		this.m.projMat[2][2] = far / (far - near);
		this.m.projMat[3][2] = (-far * near) / (far - near);
		this.m.projMat[2][3] = 1.0f;
		this.m.projMat[3][3] = 0.0f;

	}

	public Polygon3D(Punkt a, Punkt b, Punkt c) {

		this.punkte = new Punkt[3];
		this.punkte[0] = a;
		this.punkte[1] = b;
		this.punkte[2] = c;
		init();

	}

	public void render(Graphics g) {
		rotPunkteX = new Punkt[3];
		rotPunkteXY = new Punkt[3];
		rotPunkteXYZ = new Punkt[3];
		prPunkte = new Punkt[3];

		this.rotX.projMat[0][0] = 1;
		this.rotX.projMat[1][1] = (float) Math.cos(xRot);
		this.rotX.projMat[1][2] = (float) Math.sin(xRot);
		this.rotX.projMat[2][1] = (float) -Math.sin(xRot);
		this.rotX.projMat[2][2] = (float) Math.cos(xRot);
		this.rotX.projMat[3][3] = 1;

		this.rotZ.projMat[0][0] = (float) Math.cos(zRot);
		this.rotZ.projMat[0][1] = (float) Math.sin(zRot);
		this.rotZ.projMat[1][0] = (float) -Math.sin(zRot);
		this.rotZ.projMat[1][1] = (float) Math.cos(zRot);
		this.rotZ.projMat[2][2] = 1;
		this.rotZ.projMat[3][3] = 1;

		rotPunkteX[0] = PunktTransform.multMat(punkte[0], rotX);
		rotPunkteX[1] = PunktTransform.multMat(punkte[1], rotX);
		rotPunkteX[2] = PunktTransform.multMat(punkte[2], rotX);
//		rotPunkteXY[0] = PunktTransform.multMat(rotPunkteX[0], rotY);
//		rotPunkteXY[1] = PunktTransform.multMat(rotPunkteX[1], rotY);
//		rotPunkteXY[2] = PunktTransform.multMat(rotPunkteX[2], rotY);
		rotPunkteXYZ[0] = PunktTransform.multMat(rotPunkteX[0], rotZ);
		rotPunkteXYZ[1] = PunktTransform.multMat(rotPunkteX[1], rotZ);
		rotPunkteXYZ[2] = PunktTransform.multMat(rotPunkteX[2], rotZ);


		versPunkte = rotPunkteXYZ;
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

//		System.out.println(normale.length + " / " + normale.x + " / " + normale.y + " / " + normale.z + " / "
//				+ normale.normX + " / " + normale.normY + " / " + normale.normZ);

//		if (Math.round(normale.normZ) <= 0) {
		if(normale.normX * (versPunkte[0].x - this.cam.x)+
		   normale.normY * (versPunkte[0].y - this.cam.y)+
		   normale.normZ * (versPunkte[0].z - this.cam.z) < 0.0f) {
			// Schattierung
			float lae =  (float) (normale.normX * licht.normX + normale.normY * licht.normY + normale.normZ * licht.normZ);
			
			lae = Math.abs(lae);
			if(lae < 0) lae =0;
			if(lae >= 1) lae = 1;
			System.out.println(lae);
			Color shade = new Color(lae, lae, lae);
			
			
			
			// Projektion
			prPunkte[0] = PunktTransform.multMat(versPunkte[0], m);
			prPunkte[1] = PunktTransform.multMat(versPunkte[1], m);
			prPunkte[2] = PunktTransform.multMat(versPunkte[2], m);

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
			// Fl‰che Zeichnen
			Polygon dreieck = new Polygon();
			dreieck.addPoint((int) prPunkte[0].x, (int) prPunkte[0].y);
			dreieck.addPoint((int) prPunkte[1].x, (int) prPunkte[1].y);
			dreieck.addPoint((int) prPunkte[2].x, (int) prPunkte[2].y);
			g.setColor(shade);
//			g.setColor(Color.magenta);
			g.fillPolygon(dreieck);

		}

	}

	public void aendern(double x, double y, double z) {
		zOff = (float) z;
	}

	public void rotate(boolean UZ, double xGrad, double yGrad, double zGrad) {
		this.xRot = (float) xGrad;
		this.yRot = (float) yGrad;
		this.zRot = (float) zGrad;

	}

	public void setColor(Color color2) {
		color = color2;

	}
}
