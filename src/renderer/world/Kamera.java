package renderer.world;

import renderer.punkt.Vektor;

public class Kamera {
	
	
	Vektor vCamera = new Vektor(0,0,0);	
	public Kamera() {
		
	}
	
	public void aendern(double x, double y, double z) {
		this.vCamera.setX(x);
		this.vCamera.setY(y);
		this.vCamera.setZ(z);
	}
	
	public double getX() {
		return this.vCamera.x;
	}
	
	public double getY() {
		return this.vCamera.y;
	}
	
	public double getZ() {
		return this.vCamera.z;
	}
	
}
