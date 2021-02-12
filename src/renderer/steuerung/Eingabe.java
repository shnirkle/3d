package renderer.steuerung;

public class Eingabe { // Verschiedene Eingabemöglochkeiten werden hier zusammengefasst, um ein späteres Einfügen von bspw. einem Controller einfach zu gestalten

	public Tastatur tastatur;

	public Eingabe() {

		this.tastatur = new Tastatur();
	}
}