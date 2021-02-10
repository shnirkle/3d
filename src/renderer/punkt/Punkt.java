package renderer.punkt;


// Die Klasse Punkt hält drei Werte und wird für Punkte sowie als auch Vektoren verwendet.
public class Punkt {

	
	public float x, y, z;
	public float xAb, yAb, zAb; // Ab steht für Abweichung, weil es ein Offset beim rotieren gibt
	

	public Punkt( float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}


	public Punkt(double x2, double y2, double z2) {
		this.x = (float) x2;
		this.y = (float) y2;
		this.z = (float) z2;
	}
	public static Vektor toVektor(Punkt p) {
		return new Vektor(p.x,p.y,p.z);
	}


	public static Vektor[] toVektor(Punkt[] pp) {
		Vektor[] vv = new Vektor[3];
		for(int i = 0; i  < 3; i++) {
			vv[i] = Punkt.toVektor(pp[i]);
		}
		
		return vv;
	}
}