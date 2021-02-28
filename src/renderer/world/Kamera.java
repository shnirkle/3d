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
	public static void up(float u) {
		vCamera.setY(vCamera.y + u);
	}
	public static void rot(double ang) {
		yGrad += ang;
		
		
		
	}
	public static void forw(float u) {
		
		Vektor vforw = Vektor.multvec(vLook, u);
		vCamera = Vektor.add(vCamera, vforw);
//		vCamera.setZ(vCamera.normZ + u);
	}
	public static void backw(float u) {
		Vektor vbackw = Vektor.multvec(vLook, u);
		vCamera = Vektor.sub(vCamera, vbackw);
//		vCamera.setZ(vCamera.normZ + u);
	}
	
	public static float getX() {
		return Kamera.vCamera.x;
	}
	
	public static float getY() {
		return Kamera.vCamera.y;
	}
	
	public static float getZ() {
		return Kamera.vCamera.z;
	}
	public static void updateCam() {
		Vektor.printVektor(vLook);
		Vektor.printVektor(vCamera);
		Vektor vTarget = new Vektor(0,0,1);
		vUp = new Vektor(0,1,0);
		Matrix yRot = Matrix.rotateAxisY(yGrad);
		vLook = Matrix.multMat(vTarget, yRot);
		vDir = Vektor.add(vCamera, vLook);
		Matrix camMatrix = Matrix.richteKameraMatrix(vDir, vUp, vCamera); //M
		
		sicht = Matrix.matrixInvertierung(camMatrix);
		sicht.printMat();
	}
}
