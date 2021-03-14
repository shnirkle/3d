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

	//Das ist ein Vektor
	
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
	
	//Debugmethode um Vektoren zu printen
	
	public static void printVektor(Vektor... a) {
		for(Vektor b : a) {
			System.out.println("X:" + b.x + " | Y:" + b.y + " | Z:"+ b.z + " | W:"+ b.w);
		}
	}	
	
	//Mathematik fï¿½r Vektoren
	
	//multipliziere Vektor mit Vektor
	
	public static Vektor multVektor_Vektor(Vektor vec1, Vektor vec2) {
		return new Vektor(vec1.x * vec2.x, vec1.y * vec2.y, vec1.z * vec2.z);
	}
	
	//Kreuzprodukt von zwei Vektoren
	
	public static Vektor kreuzprodukt(Vektor vec1, Vektor vec2) {
		
		Vektor ans = new Vektor(0, 0, 0);
		ans.setX(vec1.y * vec2.z - vec2.y * vec1.z);
		ans.setY(vec1.z * vec2.x - vec2.z * vec1.x);
		ans.setZ(vec1.x * vec2.y - vec2.x * vec1.y);
		return ans;
	}

	//Punktprodukt von zwei Vektoren	

	public static float skalarprodukt(Vektor vec1, Vektor vec2) {
		return vec1.normX * vec2.normX + vec1.normY * vec2.normY + vec1.normZ * vec2.normZ;
	}

	//Schnittwinkel von zwei Vektoren
	
	public static double schnittwinkel(Vektor vec1, Vektor vec2) {
		double punktprodukt = Vektor.skalarprodukt(vec1, vec2);
		double ans = Math.acos(punktprodukt / (vec1.length * vec2.length));
		return Math.toDegrees(ans);
	}
	
	//Multipliziere Vektor mit Faktor für Floats
	
	public static Vektor multVektor_Faktor(Vektor mult, float faktor) {
		Vektor res = new Vektor();
		res.setX(mult.x * faktor);
		res.setY(mult.y * faktor);
		res.setZ(mult.z * faktor);
		return res;
	}
	
	//Multipliziere Vektor mit Faktor für Doubles
	
	public void multVektor_Faktor(double scale) {
		this.x *= scale;
		this.y *= scale;
		this.z *= scale;

	}
	
	//Bilde den Normalenvektor
	
	public void normVektor() {
		this.x = this.normX;
		this.y = this.normY;
		this.z = this.normZ;
	}
	public Vektor normVektor2() {
		return new Vektor(this.normX, this.normY, this.normZ);
	}
	//Mache einen Einheitsvektor daraus
	
	private void einheitsVektor() {
		this.length = this.length();
		this.normX = this.x / length;
		this.normY = this.y / length;
		this.normZ = this.z / length;
	}
	
	//Koordinaten neu setzen
	
	public void setX(float x) {
		this.x = x;
		this.einheitsVektor();

	}

	public void setY(float y) {
		this.y = y;
		this.einheitsVektor();

	}

	public void setZ(float z) {
		this.z = z;
		this.einheitsVektor();

	}
	public static float dist(Vektor v, Vektor ebenenNormale, Vektor ebenenPunkt) {
		
		return (ebenenNormale.x * v.x + ebenenNormale.y * v.y + ebenenNormale.z * v.z - Vektor.skalarprodukt(ebenenNormale, ebenenPunkt));
	}
	public static Vektor VektorSchneidetFlaeche(Vektor ebenenNormale, Vektor ebenenPunkt, Vektor linAnfang, Vektor linEnde) {		
	
		Vektor lin = Vektor.sub(linEnde, linAnfang);
		lin.normVektor();
		float t = (Vektor.skalarprodukt(ebenenPunkt, ebenenNormale) - Vektor.skalarprodukt(ebenenNormale,linAnfang)) / Vektor.skalarprodukt(ebenenNormale, lin);
		System.out.println(t);
		return Vektor.add(linAnfang, Vektor.multVektor_Faktor(lin, t));


//		double t = (planeNormal.dot(planePoint) - planeNormal.dot(linePoint)) / planeNormal.dot(lineDirection.normalize());
//    return linePoint.plus(lineDirection.normalize().scale(t));
	
	}

	//Addieren und Subtrahieren
	
	public static Vektor add(Vektor v1, Vektor v2) {
		return new Vektor(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z);
	}

	public static Vektor sub(Vektor v1, Vektor v2) {
		
		return new Vektor(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z);
	}

	//Mache einen Vektor wieder zu einem Punkt
	
	public Punkt toPunkt() {
		return new Punkt(this.x, this.y, this.z);
	}

	//Laenge eines Vektors
	
	private float length() {
		return (float) Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2) + Math.pow(this.z, 2));

	}
}
