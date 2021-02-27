package renderer.world;

import renderer.punkt.Matrix;
import renderer.punkt.Vektor;

public class Kamera {
	
	
	public static Vektor vCamera = new Vektor(0,0,0);	
	public static Vektor vDir = new Vektor(0,0,1);
	public static Vektor vUp = new Vektor(0,1,0);
	public static double yGrad = 0;
	


	public static void rot(double ang) {
		yGrad += ang;
		Matrix yRot = Matrix.rotateAxisY(yGrad);
		vDir = Matrix.multMat(vDir, yRot);
		vUp = Matrix.multMat(vUp, yRot);
	}
	public static void forw(double u) {
		vCamera = Vektor.add(vCamera, Vektor.multvec(vDir, u));
//		vCamera.setZ(vCamera.normZ + u);
	}
	public static void backw(double u) {
		vCamera = Vektor.sub(vCamera, Vektor.multvec(vDir, u));
//		vCamera.setZ(vCamera.normZ + u);
	}
	
	public static double getX() {
		return Kamera.vCamera.x;
	}
	
	public static double getY() {
		return Kamera.vCamera.y;
	}
	
	public static double getZ() {
		return Kamera.vCamera.z;
	}
	
}
