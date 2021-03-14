package renderer.entity;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import renderer.Renderer;
import renderer.entity.builder.BasicEntityBuilder;
import renderer.shapes.Objekt;
import renderer.steuerung.Eingabe;
import renderer.steuerung.Tastatur;
import renderer.world.Kamera;

public class EntityManager {
	private List<Objekt> entities;
	
	public int Theta = 1;

	private Tastatur tastatur;

	public EntityManager() {
		this.entities = new ArrayList<Objekt>();

	}

	//Geschwindigkeit / Sensitivität für die Bewegung
	float speed = 1f;
	float sens = 0.05f;

	public void init(Eingabe eingabe) {
		//		this.entities.add(BasicEntityBuilder.createQuader(0, 0, 0, 1, 1, 1));
		//		this.entities.add(BasicEntityBuilder.createWürfel(0, 0, 10, 1));
		//		this.entities.add(BasicEntityBuilder.createWürfel(0, 0, 1, 1));
		//		this.entities.add(BasicEntityBuilder.createSpaceShip(0, 0, 8));
		this.entities.add(BasicEntityBuilder.createTeapot(0, 0, 0));

		for (int i = 0; i < 15; i++)
		{
			for (int j = 0; j < 15; j++)
			{
				for (int j2 = 0; j2 < 15; j2++)
				{
					this.entities.add(BasicEntityBuilder.createWürfel(i * 10, j * 10, j2 * 10, 1));
				}
			}
		}
		this.tastatur = eingabe.tastatur;
	}

	public void update() {

		this.tastatur.update();

		Theta++;

//		this.entities.get(0).aendern(0, 0, 0.02f *Theta);	//ein einzelnes Objekt bewegen
		//////////////////////////////////////////////////
		// W     -    Nach oben drehen                    
		// A     -    Nach links drehen                   
		// S     -    Nach unten drehen                    
		// D     -    Nach rechts drehen                   
		// Q     -    Gegen den Uhrzeigersinn rotieren    
		// E     -    Mit dem Uhrzeigersinn rotieren        
		// STRG -    Negative Beschleunigung                
		// Leer -    Positive Beschleunigung               
		//////////////////////////////////////////////////
		if (this.tastatur.getLeer())
		{
			Kamera.vorwaerts(speed);
		}
		if (this.tastatur.getQ())
		{
			Kamera.rotierenLR(0, 0, -sens);
		}
		if (this.tastatur.getE())
		{
			Kamera.rotierenLR(0, 0, sens);
		}
		if (this.tastatur.getA())
		{
			Kamera.rotierenLR(0, sens, 0);
		}

		if (this.tastatur.getW())
		{
			Kamera.rotierenLR(-sens, 0, 0);
		}

		if (this.tastatur.getS())
		{
			Kamera.rotierenLR(sens, 0, 0);
		}

		if (this.tastatur.getD())
		{
			Kamera.rotierenLR(0, -sens, 0);
		}

		if (this.tastatur.getSTRG())
		{
			Kamera.vorwaerts(-speed);
		}

		Kamera.updateCam();
	}

	//alle Objekte rendern

	public void render(Graphics g) {
		Renderer.clear();
		for (Objekt obj : this.entities)
		{
			obj.render();
		}
		Renderer.render(g);
	}

	//alle Objekte rotieren

	public void rotate(float xGrad, float yGrad, float zGrad) {
		for (Objekt obj : this.entities)
		{

			this.rotate(obj, xGrad, yGrad, zGrad);
		}
	}

	//1 Objekt rotieren

	public void rotate(Objekt w, float xGrad, float yGrad, float zGrad) {

		xGrad = (float) Math.toRadians(xGrad);
		yGrad = (float) Math.toRadians(yGrad);
		zGrad = (float) Math.toRadians(zGrad);

		w.rotate(xGrad, yGrad, zGrad);

	}

}
