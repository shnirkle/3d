package renderer.world;

import renderer.punkt.Matrix;
import renderer.punkt.Vektor;

public class Kamera {
	
	
	public static Vektor vCamera = new Vektor(0,0,0);	
	public static Vektor vLook = new Vektor(0,0,0);
	public static double yGrad = 0;
	public static Matrix sicht;
	public static Vektor vDir = new Vektor(0,0,1);
	public static Vektor vUp = new Vektor(0,1,0);
	public static void up(double u) {
		vCamera.setY(vCamera.y + u);
	}
	public static void rot(double ang) {
		yGrad += ang;
		
		
		
	}
	public static void forw(double u) {
		Vektor vforw = Vektor.multvec(vLook, 0.5);
		vCamera = Vektor.add(vCamera, vforw);
//		vCamera.setZ(vCamera.normZ + u);
	}
	public static void backw(double u) {
		Vektor vbackw = Vektor.multvec(vLook, 0.5);
		vCamera = Vektor.sub(vCamera, vbackw);
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
	public static void updateCam() {
	
		Vektor vTarget = new Vektor(0,0,1);
		vUp = new Vektor(0,1,0);
		Matrix yRot = Matrix.rotateAxisY(yGrad);
		vLook = Matrix.multMat(vTarget, yRot);
		vDir = Vektor.add(vCamera, vLook);
		Matrix camMatrix = Matrix.richteKameraMatrix(vDir, vUp, vCamera);
		
		sicht = Matrix.matrixInvertierung(camMatrix);
		yRot.printMat();
	}
}
