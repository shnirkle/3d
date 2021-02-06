package renderer.world;

public class Kamera {
	
	private double x, y, z;
	//private double roll, pitch, yaw;
	
	public Kamera(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void aendern(double x, double y, double z) {
		
	}
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	
	public double getZ() {
		return this.z;
	}
	
}
