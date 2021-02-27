package renderer.shapes;

import java.awt.Color;

import renderer.Anzeige;
import renderer.Renderer;
import renderer.punkt.Matrix;
import renderer.punkt.Punkt;
import renderer.punkt.Vektor;
import renderer.world.Kamera;

public class Polygon3D {

	private static Vektor licht = new Vektor(1, 0, 0);
	private static Vektor adjustScreen = new Vektor(Anzeige.WIDTH * 0.5, Anzeige.HEIGHT * 0.5, 1);
	private static Vektor viewOff = new Vektor(1.0, 1.0, 0.0);

	private Vektor[] punkte;
	private Vektor[] prPunkte;

	public double avgZ;
	public Color shade;

	Matrix rotX = new Matrix();
	Matrix rotY = new Matrix();
	Matrix rotZ = new Matrix();

	public Polygon3D(Punkt a, Punkt b, Punkt c) {
		this.punkte = new Vektor[3];
		this.punkte[0] = Punkt.toVektor(a);
		this.punkte[1] = Punkt.toVektor(b);
		this.punkte[2] = Punkt.toVektor(c);
	}

	public void calc(Matrix weltMat) {

		//		Vektor.printVektor(Kamera.vDir);

		//		sichtMatrix.printMat();
		Vektor[] tranPunkte = new Vektor[3];

		tranPunkte[0] = Matrix.multMat(punkte[0], weltMat);
		tranPunkte[1] = Matrix.multMat(punkte[1], weltMat);
		tranPunkte[2] = Matrix.multMat(punkte[2], weltMat);
		//		Vektor.printVektor(tranPunkte);
		Vektor v_0_1 = Vektor.sub(tranPunkte[1], tranPunkte[0]);
		v_0_1.normVec();
		Vektor v_0_2 = Vektor.sub(tranPunkte[2], tranPunkte[0]);
		v_0_2.normVec();
		Vektor normale = Vektor.kreuzprodukt(v_0_1, v_0_2);
		normale.normVec();
		Vektor camRay = Vektor.sub(tranPunkte[0], Kamera.vCamera);
		if (Vektor.dot(normale, camRay) < 0.0f)
		{

			shade = this.calcShade(normale, tranPunkte[0]);
			Vektor[] viewed = new Vektor[3];
			viewed[0] = Matrix.multMat(tranPunkte[0], Kamera.sicht);
			viewed[1] = Matrix.multMat(tranPunkte[1], Kamera.sicht);
			viewed[2] = Matrix.multMat(tranPunkte[2], Kamera.sicht);
			//			Vektor.printVektor(viewed);

			prPunkte = new Vektor[3];

			prPunkte[0] = Matrix.multMat(viewed[0], Matrix.projectionMatrix);
			prPunkte[1] = Matrix.multMat(viewed[1], Matrix.projectionMatrix);
			prPunkte[2] = Matrix.multMat(viewed[2], Matrix.projectionMatrix);

			//			prPunkte[0] = Matrix.multMat(tranPunkte[0], Matrix.projectionMatrix);
			//			prPunkte[1] = Matrix.multMat(tranPunkte[1], Matrix.projectionMatrix);
			//			prPunkte[2] = Matrix.multMat(tranPunkte[2], Matrix.projectionMatrix);

			prPunkte[0].multvec(1 / prPunkte[0].w);
			prPunkte[1].multvec(1 / prPunkte[1].w);
			prPunkte[2].multvec(1 / prPunkte[2].w);

			prPunkte[0] = Vektor.add(prPunkte[0], viewOff);
			prPunkte[1] = Vektor.add(prPunkte[1], viewOff);
			prPunkte[2] = Vektor.add(prPunkte[2], viewOff);

			prPunkte[0] = Vektor.multVektor_Vektor(prPunkte[0], adjustScreen);
			prPunkte[1] = Vektor.multVektor_Vektor(prPunkte[1], adjustScreen);
			prPunkte[2] = Vektor.multVektor_Vektor(prPunkte[2], adjustScreen);

			this.avgZ = this.getAvgZ();
			Renderer.addPoly(this);

		}

	}

	public Vektor[] getPrPunkte() {
		return prPunkte;
	}

	private Color calcShade(Vektor normal, Vektor vers) {
		Vektor lightR = Vektor.sub(licht, vers);

		float lae = (float) (Vektor.dot(normal, lightR));
		lae = Math.abs(lae);
		if (lae < 0)
			lae = 0;

		return new Color(lae, lae, lae);
	}

	private double getAvgZ() {
		double p1Z = (this.prPunkte[0].z + this.prPunkte[1].z + this.prPunkte[2].z) / 3;
		return p1Z;
	}

	public Color getShade() {
		return this.shade;
	}
}
