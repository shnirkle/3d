package renderer.steuerung;

public class Eingabe { // Verschiedene Eingabem�glochkeiten werden hier zusammengefasst, um ein sp�teres Einf�gen von bspw. einem Controller einfach zu gestalten

	public Tastatur tastatur;

	public Eingabe() {

		this.tastatur = new Tastatur();
	}
}