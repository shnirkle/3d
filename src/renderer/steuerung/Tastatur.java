package renderer.steuerung;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Tastatur implements KeyListener {
	
	private boolean[] tasten = new boolean[66568];
	private boolean links, rechts, oben, unten, vorne, hinten;
	
	public void update() {	//Den boolean-Variablen werden Tasten zugewiesen
		this.links = this.tasten[KeyEvent.VK_LEFT] || this.tasten[KeyEvent.VK_A];
		this.rechts = this.tasten[KeyEvent.VK_RIGHT] || this.tasten[KeyEvent.VK_D];
		this.vorne = this.tasten[KeyEvent.VK_UP] || this.tasten[KeyEvent.VK_W];
		this.hinten = this.tasten[KeyEvent.VK_DOWN] || this.tasten[KeyEvent.VK_S];
		this.oben = this.tasten[KeyEvent.VK_SPACE];
		this.unten = this.tasten[KeyEvent.VK_C] || this.tasten[KeyEvent.VK_CONTROL];
	}
	
	public boolean getLinks() { 
		return this.links;
	}
	
	public boolean getRechts() {
		return this.rechts;
	}
	
	public boolean getVorne() { 
		return this.vorne;
	}
	
	public boolean getHinten() {
		return this.hinten;
	}
	
	public boolean getOben() {
		return this.oben;
	}
	
	public boolean getUnten() {
		return this.unten;
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		tasten[arg0.getKeyCode()] = true;
		System.out.println("pressed  " + arg0.getKeyChar());
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		tasten[arg0.getKeyCode()] = false;
		System.out.println("unpressed");
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
