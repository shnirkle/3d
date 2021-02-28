package renderer.punkt;

public class Vektor {

	public float y = 0;
	public float x = 0;
	public float z = 0;
	public float w = 1;
	public float length = 0;
	public float normX = 0;
	public float normY = 0;
	public float normZ = 0;

	public Vektor(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.length = (float) Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2) + Math.pow(this.z, 2));
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

	public static float dot(Vektor vec1, Vektor vec2) {
		return vec1.normX * vec2.normX + vec1.normY * vec2.normY + vec1.normZ * vec2.normZ;
	}

	public static double degrees(Vektor vec1, Vektor vec2) {
		double dot = Vektor.dot(vec1, vec2);
		double ans = Math.acos(dot / (vec1.length * vec2.length));
		return Math.toDegrees(ans);
	}
	public static void printVektor(Vektor... a) {
		for(Vektor b : a) {
			System.out.println("X:" + b.x + " | Y:" + b.y + " | Z:"+ b.z + " | W:"+ b.w);
		}
	}
	public static Vektor multvec(Vektor mult, float faktor) {
		Vektor res = new Vektor();
		res.setX(mult.x * faktor);
		res.setY(mult.y * faktor);
		res.setZ(mult.z * faktor);
		return res;
	}
	public void multvec(double scale) {
		this.x *= scale;
		this.y *= scale;
		this.z *= scale;

	}
	public void normVec() {
		this.x = this.normX;
		this.y = this.normY;
		this.z = this.normZ;
	}
	private void reCalc() {
		this.length = this.length();
		this.normX = this.x / length;
		this.normY = this.y / length;
		this.normZ = this.z / length;
	}

	public void setX(float x) {
		this.x = x;
		this.reCalc();

	}

	public void setY(float y) {
		this.y = y;
		this.reCalc();

	}

	public void setZ(float z) {
		this.z = z;
		this.reCalc();

	}

	public static Vektor add(Vektor v1, Vektor v2) {
		return new Vektor(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z);
	}

	public static Vektor sub(Vektor v1, Vektor v2) {
		return new Vektor(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z);
	}

	public Punkt toPunkt() {
		return new Punkt(this.x, this.y, this.z);
	}

	private float length() {
		return (float) Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2) + Math.pow(this.z, 2));

	}
}
