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
}