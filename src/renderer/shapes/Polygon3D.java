package renderer.shapes;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import renderer.Anzeige;
import renderer.Renderer;
import renderer.punkt.Matrix;
import renderer.punkt.Punkt;
import renderer.punkt.Vektor;
import renderer.world.Kamera;

public class Polygon3D {

	private static Vektor licht = new Vektor(1, 0, 0);
	private static Vektor adjustScreen = new Vektor(Anzeige.WIDTH * 0.5f, Anzeige.HEIGHT * 0.5f, 1f);
	private static Vektor viewOff = new Vektor(1.0f, 1.0f, 0.0f);
	private static Vektor ebenenNormale = new Vektor(0.0f, 0.0f, 1.0f);
	private static Vektor ebenenPunkt = new Vektor(0, 0, 0.1f);
	private Vektor[] punkte = new Vektor[3];
	private Vektor[] prPunkte;
	private Vektor[] viewed;

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

	public Polygon3D(Vektor a, Vektor b, Vektor c) {
		this.punkte = new Vektor[3];
		this.punkte[0] = a;
		this.punkte[1] = b;
		this.punkte[2] = c;
	}

	public Polygon3D() {

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
			viewed = new Vektor[3];
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
			clipTriangle(ebenenNormale, ebenenPunkt, viewed);
			//			Renderer.addPoly(this);
		}

	}

	public void clipTriangle(Vektor ebenenNormale, Vektor ebenenPunkt, Vektor[] p) {
//		Vektor.printVektor(p);
//		ebenenNormale.normVec();

		//		Vektor.multvec(viewed[0], -1);
		//		Vektor.multvec(viewed[1], -1);
		//		Vektor.multvec(viewed[2], -1);

		float dist0 = Vektor.dist(p[0], ebenenNormale, ebenenPunkt);
		float dist1 = Vektor.dist(p[1], ebenenNormale, ebenenPunkt);
		float dist2 = Vektor.dist(p[2], ebenenNormale, ebenenPunkt);

		
		
		List<Vektor> insideList = new ArrayList<Vektor>();
		List<Vektor> outsideList = new ArrayList<Vektor>();

		if (dist0 >= 0)
		{
			insideList.add(p[0]);
//			System.out.println(dist0 > 0);
//						System.out.println("dist0 ist inside " + inside);

		} else
		{

			outsideList.add(p[0]);
//			System.out.println(dist0 > 0);

		}
		if (dist1 >= 0)
		{
			insideList.add(p[1]);
//			System.out.println(dist1 > 0);
			//			System.out.println("dist1 ist inside " + inside);

		} else
		{
			outsideList.add(p[1]);
//			System.out.println(dist1 > 0);
		}
		if (dist2 >= 0)
		{
			insideList.add(p[2]);
//			System.out.println(dist2 > 0);
			//			System.out.println("dist2 ist inside " + inside);
		} else
		{
			outsideList.add(p[2]);
//			System.out.println(dist2 > 0);
		}
//		System.out.println(dist0);
//		System.out.println(dist1);
//		System.out.println(dist2);
		int inside = insideList.size();
		int outside = outsideList.size();
//		System.out.println(inside + " , " + insideList.size() + " , " + (3 - outsideList.size()));
		if (inside == 0 && outside == 3)
			return;
		if (inside == 1 && outside == 2)
		{

			Polygon3D temp = new Polygon3D();
			temp.shade = this.shade; // wenn problem mal hier gucken 
			temp.punkte[0] = p[0];
			//			System.out.println('a');
			if(outsideList.get(1) == null) return;
			temp.punkte[1] = Vektor.VektorSchneidetFläche(ebenenNormale, ebenenPunkt, insideList.get(0), outsideList.get(0));
			temp.punkte[2] = Vektor.VektorSchneidetFläche(ebenenNormale, ebenenPunkt, insideList.get(0), outsideList.get(1));

			Renderer.addPoly(temp);
		}
		if (inside == 2 && outside == 1)
		{
			if (insideList.get(0) == null || outsideList.get(0) == null || insideList.get(1) == null)
			{
				return;
			}
			Polygon3D temp1 = new Polygon3D();
			Polygon3D temp2 = new Polygon3D();
			//			System.out.println('b');
			temp1.shade = this.shade;
			temp2.shade = temp1.shade;
			//			if(tempIns[1] == null) System.out.println("sss");

			temp1.punkte[0] = insideList.get(0);
			temp1.punkte[1] = insideList.get(1);
			temp1.punkte[2] = Vektor.VektorSchneidetFläche(ebenenNormale, ebenenPunkt, insideList.get(0), outsideList.get(0));

			temp2.punkte[0] = insideList.get(1);
			temp2.punkte[1] = temp1.punkte[2];
			temp2.punkte[2] = Vektor.VektorSchneidetFläche(ebenenNormale, ebenenPunkt, insideList.get(1), outsideList.get(0));

			Renderer.addPoly(temp1);
			Renderer.addPoly(temp2);

			//.out.println("bbbb");
		}
		if (inside == 3 && outside == 0)
		{
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

	private double getAvgZ(Vektor[] viewed) {
		double p1Z = (viewed[0].z + viewed[1].z + viewed[2].z) / 3;
		return p1Z;
	}

	private double getAvgZ() {
		double p1Z = (this.prPunkte[0].z + this.prPunkte[1].z + this.prPunkte[2].z) / 3;
		return p1Z;
	}

	public Color getShade() {
		return this.shade;
	}
}
