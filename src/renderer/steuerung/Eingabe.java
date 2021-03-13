package renderer.steuerung;

public class Eingabe { 
// Verschiedene Eingabemöglochkeiten werden hier zusammengefasst (in unserem Fall nur eine), 
// um ein späteres Einfügen von bspw. einem Controller, einer Maus, ... einfach zu gestalten

	public Tastatur tastatur;

	public Eingabe() {

		this.tastatur = new Tastatur();
	}
}