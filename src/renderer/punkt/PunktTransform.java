package renderer.punkt;

import java.awt.Point;

import renderer.Anzeige;

public class PunktTransform {
	// 900 600
	private static double scale = 1;
	public static Point transformPunkt(Punkt punkt3D) {
		
		
		double x3D = punkt3D.y  * scale;
		double y3D = punkt3D.z * scale;
		double depth = punkt3D.x * scale;
		double[] newVal = scale(x3D, y3D, depth);
		int x2D =  (int) (Anzeige.WIDTH / 2 + newVal[0]);
		int y2D =  (int) (Anzeige.HEIGHT / 2 - newVal[1]);
	
	
		Point punkt2D = new Point(x2D, y2D);
		return punkt2D;
	}

	private static double[] scale(double x3D, double y3D, double depth) {
		double dist = Math.sqrt(x3D * x3D + y3D * y3D);                                     //Math.pow(x3D, x3D) + Math.pow(y3D, y3D)); + Math.pow(depth, depth));
		double theta = Math.atan2(y3D, x3D);
		double depth2 = 15 - depth;
		double localScale = Math.abs(5000/(depth2 + 5000));
		dist *= localScale;
		double[] newVal = new double[2];
		newVal[0] = dist * Math.cos(theta);
		newVal[1] = dist * Math.sin(theta);
		return newVal;
		
		
		
	}









}
