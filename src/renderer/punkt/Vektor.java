package renderer.punkt;

public class Vektor {

	public double y = 0;
	public double x = 0;
	public double z = 0;
	public double w = 1;
	public double length = 0;
	public double normX = 0;
	public double normY = 0;
	public double normZ = 0;

	public Vektor(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.length = Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2) + Math.pow(this.z, 2));
		this.normX = this.x / length;
		this.normY = this.y / length;
		this.normZ = this.z / length;
	}

	public Vektor() {

	}

	public static Vektor multVektor_Vektor(Vektor vec1, Vektor vec2) {
		return new Vektor(vec1.x * vec2.x, vec1.y * vec2.y, vec1.z * vec2.z);
	}

	public static Vektor kreuzprodukt(Vektor vec1, Vektor vec2) {
		Vektor ans = new Vektor(0, 0, 0);
		ans.setX(vec1.y * vec2.z - vec2.y * vec1.z);
		ans.setY(vec1.z * vec2.x - vec2.z * vec1.x);
		ans.setZ(vec1.x * vec2.y - vec2.x * vec1.y);
		return ans;
	}

	public static double dot(Vektor vec1, Vektor vec2) {
		return (double) vec1.normX * vec2.normX + vec1.normY * vec2.normY + vec1.normZ * vec2.normZ;
	}

	public static double degrees(Vektor vec1, Vektor vec2) {
		double dot = Vektor.dot(vec1, vec2);
		double ans = Math.acos(dot / (vec1.length * vec2.length));
		return Math.toDegrees(ans);
	}

	public void multvec(double scale) {
		this.x *= scale;
		this.y *= scale;
		this.z *= scale;

	}

	private void reCalc() {
		this.length = this.length();
		this.normX = this.x / length;
		this.normY = this.y / length;
		this.normZ = this.z / length;
	}

	public void setX(double x) {
		this.x = x;
		this.reCalc();

	}

	public void setY(double y) {
		this.y = y;
		this.reCalc();

	}

	public void setZ(double z) {
		this.z = z;
		this.reCalc();

	}

	public static Vektor add(Vektor v1, Vektor v2) {
		return new Vektor(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z);
	}

	public static Vektor sub(Vektor v1, Vektor v2) {
		return new Vektor(v2.x - v1.x, v2.y - v1.y, v2.z - v1.z);
	}

	public Punkt toPunkt() {
		return new Punkt(this.x, this.y, this.z);
	}

	private double length() {
		return Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2) + Math.pow(this.z, 2));

	}
}
