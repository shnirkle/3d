package renderer.steuerung;

public class Eingabe { 
// Verschiedene Eingabem�glochkeiten werden hier zusammengefasst (in unserem Fall nur eine), 
// um ein sp�teres Einf�gen von bspw. einem Controller, einer Maus, ... einfach zu gestalten

	public Tastatur tastatur;

	public Eingabe() {

		this.tastatur = new Tastatur();
	}
}