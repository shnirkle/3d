package renderer.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import renderer.Anzeige;
import renderer.PauseView;
import renderer.Renderer;
import renderer.entity.builder.BasicEntityBuilder;
import renderer.punkt.Punkt;
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

//		this.entities.add(BasicEntityBuilder.createTeapot(0, 0, 8));
//
//		for (int i = 0; i < 15; i++)
//		{
//			for (int j = 0; j < 15; j++)
//			{
//				for (int j2 = 0; j2 < 15; j2++)
//				{
//					this.entities.add(BasicEntityBuilder.createWürfel(i * 10, j * 10, j2 * 10, 1));
//				}
//			}
//		}
		this.createSolarSystem(0.00001f, 0.00000001f);
		//		 

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
			Kamera.vorwaerts(-speed * 5);
		}
		if (this.tastatur.getEcp())
		{
			Anzeige.isPaused = true;
			PauseView pauseview = new PauseView();
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

	public void createSolarSystem(float groessenskale, float abstandsskale) {
		this.entities.add(BasicEntityBuilder.createSphere(Color.blue, new Punkt(0,0,500), 1392700 * groessenskale, 120));
		this.entities.add(BasicEntityBuilder.createSphere(Color.BLUE, new Punkt(58000000f   * abstandsskale, 0, 500), 4879 * groessenskale, 80)); //Merkur
		this.entities.add(BasicEntityBuilder.createSphere(Color.BLUE, new Punkt(108000000f   * abstandsskale, 0, 500), 12104 * groessenskale, 80)); //Venus
		this.entities.add(BasicEntityBuilder.createSphere(Color.BLUE, new Punkt(150000000f  * abstandsskale, 0, 500), 12104 * groessenskale, 14)); //Erde
		this.entities.add(BasicEntityBuilder.createSphere(Color.BLUE, new Punkt(228000000f  * abstandsskale, 0, 500), 12742 * groessenskale, 14)); //Mars
		this.entities.add(BasicEntityBuilder.createSphere(Color.BLUE, new Punkt(778000000f  * abstandsskale, 0, 500), 139820 * groessenskale, 14)); //Jupiter
		this.entities.add(BasicEntityBuilder.createSphere(Color.BLUE, new Punkt(1427000000f * abstandsskale, 0, 500), 116460 * groessenskale, 14)); //Saturn
		this.entities.add(BasicEntityBuilder.createSphere(Color.BLUE, new Punkt(2884000000f * abstandsskale, 0, 500), 50724 * groessenskale, 14)); //Uranus
		this.entities.add(BasicEntityBuilder.createSphere(Color.BLUE, new Punkt(4509000000f * abstandsskale, 0, 500), 49244 * groessenskale, 14)); //Neptun
	}

}
