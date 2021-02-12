package renderer.world;

import renderer.punkt.Vektor;

public class Kamera {
	
	
	public static Vektor vCamera = new Vektor(0,0,0);	
	public Kamera() {
		
	}
	
	public void aendern(double x, double y, double z) {
		this.vCamera.setX(x);
		this.vCamera.setY(y);
		this.vCamera.setZ(z);
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
