package renderer.punkt;

public class Punkt {

	
	public double x, y, z;
	public double xAb, yAb, zAb; // Ab steht für Abweichung, weil es ein Offset beim rotieren gibt
	

	public Punkt( double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.xAb = 0;
		this.yAb = 0;
		this.zAb = 0;
	}
	
	public double getAngepasstestX() {
		return this.x + this.xAb;
	}
	
	public double getAngepasstestY() {
		return this.y + this.yAb;
	}
	
	public double getAngepasstestZ() {
		return this.z + this.zAb;
	}
}
 