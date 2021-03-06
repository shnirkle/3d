package renderer.steuerung;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Tastatur implements KeyListener {

	private boolean[] tasten = new boolean[66568];
	private boolean w, a, s, d, links, rechts, oben, unten, strg, leer, ecp;

	//Den boolean-Variablen werden Tasten zugewiesen
	public void update() {
		this.w = this.tasten[KeyEvent.VK_W];
		this.a = this.tasten[KeyEvent.VK_A];
		this.s = this.tasten[KeyEvent.VK_S];
		this.d = this.tasten[KeyEvent.VK_D];
		this.links = this.tasten[KeyEvent.VK_LEFT];
		this.rechts = this.tasten[KeyEvent.VK_RIGHT];
		this.oben = this.tasten[KeyEvent.VK_UP];
		this.unten = this.tasten[KeyEvent.VK_DOWN];
		this.strg = this.tasten[KeyEvent.VK_CONTROL];
		this.leer = this.tasten[KeyEvent.VK_SPACE];
		this.ecp = this.tasten[KeyEvent.VK_ESCAPE];
	}

	//Methoden um herauszufinden, ob eine Taste gedr�ckt wird

	public boolean getW() {
		return this.w;
	}

	public boolean getA() {
		return this.a;
	}

	public boolean getS() {
		return this.s;
	}

	public boolean getD() {
		return this.d;
	}

	public boolean getLinks() {
		return this.links;
	}

	public boolean getRechts() {
		return this.rechts;
	}
	
	public boolean getOben() {
		return this.oben;
	}

	public boolean getUnten() {
		return this.unten;
	}

	public boolean getSTRG() {
		return this.strg;
	}

	public boolean getLeer() {
		return this.leer;
	}

	public boolean getEcp() {
		return this.ecp;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		tasten[arg0.getKeyCode()] = true;
		//		System.out.println("pressed  " + arg0.getKeyChar());
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		tasten[arg0.getKeyCode()] = false;
		//		System.out.println("unpressed");

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
