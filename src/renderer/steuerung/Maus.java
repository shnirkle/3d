package renderer.steuerung;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Maus implements MouseListener, MouseMotionListener, MouseWheelListener { // implementiert Mauskontrolle; die Funktionen sind relativ selbsterklärend

	private int mausX = -1;
	private int mausY = -1;
	private int mausT = -1;
	private int scroll = 0;
	
	public int getX() {
		return this.mausX;
	}
	
	public int getY() {
		return this.mausY;
	}

	public KlickTyp getButton() {
		switch(this.mausT) {
		case 1:
			return KlickTyp.Linksklick;
		case 2:
			return KlickTyp.Mittelklick;
		case 3:
			return KlickTyp.Rechtsklick;
		default:
			return KlickTyp.Unbekannt;
		}
	}
	
	public boolean scrollUp() {
		return this.scroll == -1;
	}
	
	public boolean scrollDown() {
		return this.scroll == 1;
	}
	
	public void resetScroll() {
		this.scroll = 0;
	}
	
	public void resetButton() {
		this.mausT = -1;
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		scroll = arg0.getWheelRotation();
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		this.mausX = arg0.getX();
		this.mausY = arg0.getY();
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		this.mausX = arg0.getX();
		this.mausY = arg0.getY();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		this.mausT = arg0.getButton();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		this.mausT = -1;
		
	}

}
