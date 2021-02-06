package renderer.punkt;


// Die Klasse Punkt h�lt drei Werte und wird f�r Punkte sowie als auch Vektoren verwendet.
public class Punkt {

	
	public float x, y, z;
	public float xAb, yAb, zAb; // Ab steht f�r Abweichung, weil es ein Offset beim rotieren gibt
	

	public Punkt( float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
}