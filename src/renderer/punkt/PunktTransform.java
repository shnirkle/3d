package renderer.punkt;

import java.awt.Point;
import java.awt.Polygon;

import renderer.Anzeige;

public class PunktTransform {

	public static Polygon transformPunkt(Punkt... punkte) {

		return null;
	}

	public static void rotateAxisX(Punkt p, boolean UZ, double grad) {

	} 

	public static void rotateAxisY(Punkt p, boolean UZ, double grad) {

	}

	public static void rotateAxisZ(Punkt p, boolean UZ, double grad) { 

	}

	public static Punkt multMat(Punkt i, Matritze4 m) {
		float x = (float) (i.x * m.projMat[0][0] + i.y * m.projMat[1][0] + i.z * m.projMat[2][0] + m.projMat[3][0]);
		float y = (float) (i.x * m.projMat[0][1] + i.y * m.projMat[1][1] + i.z * m.projMat[2][1] + m.projMat[3][1]);
		float z = (float) (i.x * m.projMat[0][2] + i.y * m.projMat[1][2] + i.z * m.projMat[2][2] + m.projMat[3][2]);
		float w = (float) (i.x * m.projMat[0][3] + i.y * m.projMat[1][3] + i.z * m.projMat[2][3] + m.projMat[3][3]);
		if (w != 0) {
			x /= w;
			y /= w;
			z /= w;
		}

		float[] a = new float[3];
		a[0] = x;
		a[1] = y;
		a[2] = z;
		return new Punkt(a[0], a[1], a[2]);
	}

	public static Punkt multMat2(Punkt i, Matritze4 m) {
		float x = (float) (i.x * m.projMat[0][0] + i.y * m.projMat[1][0] + i.z * m.projMat[2][0] + m.projMat[3][0]);
		float y = (float) (i.x * m.projMat[0][1] + i.y * m.projMat[1][1] + i.z * m.projMat[2][1] + m.projMat[3][1]);
		float z = (float) (i.x * m.projMat[0][2] + i.y * m.projMat[1][2] + i.z * m.projMat[2][2] + m.projMat[3][2]);
		float w = (float) (i.x * m.projMat[0][3] + i.y * m.projMat[1][3] + i.z * m.projMat[2][3] + m.projMat[3][3]);

		float[] a = new float[3];
		a[0] = x;
		a[1] = y;
		a[2] = z;
		return new Punkt(a[0], a[1], a[2]);
	}
}