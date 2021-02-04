package renderer.entity;

import java.awt.Color;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import renderer.entity.builder.BasicEntityBuilder;
import renderer.punkt.Punkt;
import renderer.punkt.PunktTransform;
import renderer.steuerung.*;
import renderer.world.Kamera;

public class EntityManager {
	private List<iEntity> entities;
	private Maus maus;
	private Tastatur tastatur;
	private Kamera kamera;
	
	public EntityManager() {
		this.entities = new ArrayList<iEntity>();
		this.kamera = new Kamera(0, 0, 0);
	}

	public void init(Eingabe eingabe) {
	
	this.maus = eingabe.maus;
	this.tastatur = eingabe.tastatur;
		
//		this.entities.add(BasicEntityBuilder.createWürfel(50, 0, 0, 0));
	this.entities.add(BasicEntityBuilder.createSphere(Color.BLUE, new Punkt(0,0,0), 100));
//	this.rotate(true, 0, 0, 0);

		this.entities.add(BasicEntityBuilder.createWürfel(50, 150, 0, 0));
	this.entities.add(BasicEntityBuilder.createAsteroid(100, 0, 0, 0, 0.8));
	this.rotate(true, 0, 0, 0);

	}
	
	int startX, startY;
	double mausges = 2.5; //ges steht für Geschwindigkeit
	double tastges = 10; //tastges steht für Tastaturgeschwindigkeit
	
	public void update() {
		this.rotate(true, 0, 0, 0);	
		
	try {	
	//Mauskontrolle
		
		//System.out.println(this.maus.getX() + "," + this.maus.getY()); //Mauskoordinaten anzeigen
		//System.out.println(this.maus.getButton()); //Maustaste anzeigen
			
		
			int x = this.maus.getX();
			int y = this.maus.getY();
			if (this.maus.getButton() == KlickTyp.Linksklick) { // Höhe - Breite - Rotierung
				int xDif = x - startX;
				int yDif = y - startY;
				
				this.rotate(true, 0, -yDif/mausges, -xDif/mausges);
			}
			else if (this.maus.getButton() == KlickTyp.Rechtsklick) { //Tiefe - Rotierung
				int xDif = x - startX;
			
				this.rotate(true, -xDif/mausges, 0, 0);
			}
			
			else if (this.maus.getButton() == KlickTyp.Mittelklick) { //Zoom zurücksetzen

				PunktTransform.standardzoom();
			}
			
			if (this.maus.scrollUp()) {
				PunktTransform.reinzoomen();
			} else if (this.maus.scrollDown()) {
				PunktTransform.rauszoomen();
			}
			
			this.maus.resetScroll();
			
			startX = x;
			startY = y;
			
		
			
			if (this.tastatur.getLinks()) {	// nach links gehen
				this.kamera.aendern(0, -tastges, 0);
				for(iEntity entity : this.entities) {
					entity.aendern(0, tastges, 0);
				}
			}
			
			if (this.tastatur.getRechts()) {	// nach rechts gehen
				this.kamera.aendern(0, tastges, 0);
				for(iEntity entity : this.entities) {
					entity.aendern(0, -tastges, 0);
				}
			}
			
			if (this.tastatur.getVorne()) {	// nach vorne gehen
				this.kamera.aendern(-tastges, 0, 0);
				for(iEntity entity : this.entities) {
					entity.aendern(tastges, 0, 0);
				}
			}
			
			if (this.tastatur.getHinten()) { // nach hinten gehen
				this.kamera.aendern(tastges, 0, 0);
				for(iEntity entity : this.entities) {
					entity.aendern(-tastges, 0, 0);
				}
			}
			
			if (this.tastatur.getOben()) { // nach oben gehen
				this.kamera.aendern(0, 0, tastges);
				for(iEntity entity : this.entities) {
					entity.aendern(0, 0, -tastges);
				}
			}
			
			if (this.tastatur.getUnten()) { // nach unten gehen
				this.kamera.aendern(0, 0, -tastges);
				for(iEntity entity : this.entities) {
					entity.aendern(0, 0, tastges);
				}
			}
			this.tastatur.update();
			
	} 
	catch(Exception e){
		System.out.println(e.getMessage());
	}
	}
	public void render(Graphics g) {
		for(iEntity entity : this.entities) {
			entity.render(g);
		}
	}
	public void rotate(boolean dir, double xGrad,double yGrad, double zGrad) {
		for(iEntity entity : this.entities) {
			entity.rotate(dir, xGrad, yGrad, zGrad);
		}
	}


}
