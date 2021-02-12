package renderer.shapes;

import java.awt.Color;

import renderer.Anzeige;
import renderer.Renderer;
import renderer.punkt.Matrix;
import renderer.punkt.Punkt;
import renderer.punkt.Vektor;
import renderer.world.Kamera;

public class Polygon3D {
	private static Vektor licht = new Vektor(0, 0, -1);
	private static Vektor adjustScreen = new Vektor(Anzeige.WIDTH * 0.5, Anzeige.HEIGHT * 0.5, 1);
	private static Vektor viewOff = new Vektor(1.0, 1.0, 0.0);
	private Vektor[] punkte;
	private Vektor[] prPunkte;
	public double avgZ;
	private Vektor[] versPunkte;
	public Color shade;
	Matrix rotX = new Matrix();
	Matrix rotY = new Matrix();
	Matrix rotZ = new Matrix();

	private double xGrad = 0;
	private double yGrad = 0;
	private double zGrad = 0;
	private float xOff = 0.0f;
	private float yOff = 0.0f;
	private float zOff = 3.0f;

	public Polygon3D(Punkt a, Punkt b, Punkt c) {
		this.punkte = new Vektor[3];
		this.punkte[0] = Punkt.toVektor(a);
		this.punkte[1] = Punkt.toVektor(b);
		this.punkte[2] = Punkt.toVektor(c);
	}

	public void calc() {
		Vektor[] xRotiert = Matrix.rotateAxisX(this.punkte, xGrad);
		Vektor[] yRotiert = Matrix.rotateAxisY(xRotiert, yGrad);
		Vektor[] zRotiert = Matrix.rotateAxisZ(yRotiert, zGrad);
		Vektor[] versPunkte = new Vektor[3];
		versPunkte = Matrix.aender(zRotiert, xOff, yOff, zOff);

		Vektor v_0_1 = new Vektor(0, 0, 0);
		v_0_1.setX(versPunkte[1].x - versPunkte[0].x);
		v_0_1.setY(versPunkte[1].y - versPunkte[0].y);
		v_0_1.setZ(versPunkte[1].z - versPunkte[0].z);
		Vektor v_0_2 = new Vektor(0, 0, 0);
		v_0_2.setX(versPunkte[2].x - versPunkte[0].x);
		v_0_2.setY(versPunkte[2].y - versPunkte[0].y);
		v_0_2.setZ(versPunkte[2].z - versPunkte[0].z);

		Vektor normale = Vektor.kreuzprodukt(v_0_1, v_0_2);
		Vektor cam = Kamera.vCamera;
		Vektor camRay = Vektor.sub(versPunkte[0], cam);
		if (Vektor.dot(normale, camRay) > 0.0f)
		{
			
			shade = this.calcShade(normale, versPunkte[0]);

			prPunkte = new Vektor[3];
 
			prPunkte[0] = Matrix.multMat(versPunkte[0], Matrix.projectionMatrix);
			prPunkte[1] = Matrix.multMat(versPunkte[1], Matrix.projectionMatrix);
			prPunkte[2] = Matrix.multMat(versPunkte[2], Matrix.projectionMatrix);

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

	public Vektor[] getPrPunkte() {
		return prPunkte;                                       //🍆👀🤏
	}
	private Color calcShade(Vektor normal, Vektor vers) {
		Vektor lightR = Vektor.sub(vers, licht);
		
		float lae = (float) (Vektor.dot(normal, lightR));
		lae = Math.abs(lae);
		if (lae < 0)
		{
			lae = 0;
		}
		if (lae >= 1)
		{
			lae = 1;
		}

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
