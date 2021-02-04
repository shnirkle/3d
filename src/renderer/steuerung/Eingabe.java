package renderer.steuerung;

public class Eingabe {	// Verschiedene Eingabemöglochkeiten werden hier zusammengefasst, um ein späteres Einfügen von bspw. einem Controller einfach zu gestalten

	public Maus maus;
	public Tastatur tastatur;
	
	public Eingabe() {
		this.maus = new Maus();
		this.tastatur = new Tastatur();
	}
}