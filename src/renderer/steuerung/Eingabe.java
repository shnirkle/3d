package renderer.steuerung;

public class Eingabe {	// Verschiedene Eingabem�glochkeiten werden hier zusammengefasst, um ein sp�teres Einf�gen von bspw. einem Controller einfach zu gestalten

	public Maus maus;
	public Tastatur tastatur;
	
	public Eingabe() {
		this.maus = new Maus();
		this.tastatur = new Tastatur();
	}
}