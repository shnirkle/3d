package renderer.punkt;

public class Vektor {


	public double y,x,z;

	public double length;
	double normX, normY, normZ;
	public Vektor(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.length = Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2) + Math.pow(this.z, 2));
		this.normX = this.x / length;
		this.normY = this.y / length;
		this.normZ = this.z / length;
	}
	public static Vektor kreuzprodukt(Vektor vec1, Vektor vec2) {
		return new Vektor(vec1.y * vec2.z - vec2.y * vec1.z, vec1.z * vec2.x - vec2.z * vec1.x,
				vec1.x * vec2.y - vec2.x * vec1.y);
	}
	public static double dot(Vektor vec1, Vektor vec2) {
		return (double) vec1.x * vec2.x + vec1.y*vec2.y + vec1.z * vec2.z;
	}
	public void multvec(double scale) {
		this.x *= scale;
		this.y *= scale;
		this.z *= scale;
		
	}
}
