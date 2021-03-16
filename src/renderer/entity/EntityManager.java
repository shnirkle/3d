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
	final float speed = 0.5f;
	float sens = 0.05f;
	float acc = 0.01f;
	float AccVW = 0.0f;
	float AccRW = 0.0f;

	private boolean changed;

	private List<Objekt> tempEntities;

	public void init(Eingabe eingabe) {

		
		this.createSolarSystemNOTSCALE();
		
		
		
		this.tastatur = eingabe.tastatur;
	}

	public void update() {
		if (changed)
		{

			entities = tempEntities;
//			this.entities.get(0).aendern(0, 0, entities.get(0).zOff / 2);
//			this.scale(entities.get(0), 0.5f);
			
			changed = false;
		}
		this.tastatur.update();
		
		Theta++;
		
		//		this.entities.get(0).aendern(0, 0, 0.02f *Theta);	//ein einzelnes Objekt bewegen
		//////////////////////////////////////////////////
		// W     -    Nach vorne gehen                    
		// A     -    Nach links gehen                   
		// S     -    Nach hinten gehen                    
		// D     -    Nach rechts gehen                     
		// STRG -    Nach unten gehen                
		// Leer -    Nach oben gehen      
		//W + S - Bremsen
		//Pfeiltasten - Sicht/Umschauen
		//////////////////////////////////////////////////
		if (this.tastatur.getLinks())
		{
			Kamera.rotierenLR(0, sens, 0);
		}

		if (this.tastatur.getOben())
		{
			Kamera.rotierenLR(-sens, 0, 0);
		}

		if (this.tastatur.getUnten())
		{
			Kamera.rotierenLR(sens, 0, 0);
		}

		if (this.tastatur.getRechts())
		{
			Kamera.rotierenLR(0, -sens, 0);
		}

		if (this.tastatur.getA())
		{
			Kamera.rechts(speed);
		}

		if (this.tastatur.getD())
		{
			Kamera.rechts(-speed);
		}

		if (this.tastatur.getLeer())
		{
			Kamera.oben(speed);
		}

		if (this.tastatur.getSTRG())
		{
			Kamera.oben(-speed);
		}

		if (this.tastatur.getW() && this.tastatur.getS())
		{

			//Sehr kleine Geschwindigkeiten werden auf Null gesetzt, damit das Steuern angenehmer ist

			if (Math.abs(Kamera.VEL) < 0.2f)
			{
				Kamera.VEL = 0.0f;
			} else
			{
				Kamera.VEL *= 0.75f;
			}

		} else
		{

			if (this.tastatur.getW())
			{
				AccRW = 0;
				if (AccVW < 3)
				{
					AccVW += acc;
				}
				Kamera.vorwaerts(AccVW*0.1f);
			}

			if (this.tastatur.getS())
			{
				AccVW = 0;
				if (AccRW > -3)
				{
					AccRW -= acc;
				}
				Kamera.vorwaerts(AccRW*0.1f);
			}

		}
		if (this.tastatur.getEcp())
		{
			Anzeige.isPaused = true;
			@SuppressWarnings("unused")
			PauseView pauseview = new PauseView();
		}
		Kamera.updateCam();
	}

	//alle Objekte rendern
	public void clearentities() {
		changed = true;
		tempEntities = new ArrayList<Objekt>();
	}

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
	public void scale(Objekt w, float scale) {
		w.scale(scale);
	}
	
	//Objektemethoden für die Buttons in Optionen
	
	public void createTeapot() {
		clearentities();
		tempEntities.add(BasicEntityBuilder.createTeapot(new Color(109,188,254), 0, 0, 8));
	}

	public void createGrid() {
		clearentities();
		for (int i = 0; i < 15; i++)
		{
			for (int j = 0; j < 15; j++)
			{
				for (int j2 = 0; j2 < 15; j2++)
				{
					tempEntities.add(BasicEntityBuilder.createWürfel(Color.WHITE, i * 10, j * 10, j2 * 10, 1));
				}
			}
		}
	}
	
	public void createRainbowGrid() {
		clearentities();
		for (int i = 0; i < 15; i++)
		{
			for (int j = 0; j < 15; j++)
			{
				for (int j2 = 0; j2 < 15; j2++)
				{
					tempEntities.add(BasicEntityBuilder.createWürfel(new Color((float)Math.random(),(float)Math.random(),(float)Math.random()), i * 10, j * 10, j2 * 10, 1));
				}
			}
		}
	}

	public void createEarth_Moon() {
		clearentities();
		tempEntities.add(BasicEntityBuilder.createSphere(Color.BLUE, new Punkt(0, 0, 200), 120, 110)); //Erde
		tempEntities.add(BasicEntityBuilder.createSphere(Color.GRAY, new Punkt(400, 0, 200), 60, 60)); //Mond
	}

	public void createSolarSystemNOTSCALE() {
		clearentities();
		tempEntities.add(BasicEntityBuilder.createSphere(new Color(242, 136, 30), new Punkt(0, 0, 150), 100f, 50)); //SONNE
		tempEntities.add(BasicEntityBuilder.createSphere(new Color(187, 187, 187), new Punkt(-150, 0, 150), 4.8f, 14)); //Merkur
		tempEntities.add(BasicEntityBuilder.createSphere(new Color(122, 86, 46), new Punkt(-200, 0, 150), 12.1f, 14)); //Venus
		tempEntities.add(BasicEntityBuilder.createSphere(new Color(46, 65, 106), new Punkt(-250, 0, 150), 12.1f, 38)); //Erde
		tempEntities.add(BasicEntityBuilder.createSphere(new Color(189, 128, 97), new Punkt(-320, 0, 150), 12.7f, 14)); //Mars
		tempEntities.add(BasicEntityBuilder.createSphere(new Color(201, 177, 158), new Punkt(-878, 0, 150), 70.0f, 14)); //Jupiter
		tempEntities.add(BasicEntityBuilder.createSphere(new Color(192, 166, 140), new Punkt(-1527, 0, 150), 70.0f, 14)); //Saturn
		tempEntities.add(BasicEntityBuilder.createRing(Color.GRAY, -1527, 0, 150, 110)); //Saturnringe
		tempEntities.add(BasicEntityBuilder.createSphere(new Color(209, 249, 244), new Punkt(-2884, 0, 150), 50.7f, 14)); //Uranus
		tempEntities.add(BasicEntityBuilder.createSphere(new Color(60, 82, 199), new Punkt(-4509, 0, 150), 49.2f, 14)); //Neptun
	}

	public void createSolarSystem(float groessenskale, float abstandsskale) {
		clearentities();
		tempEntities.add(BasicEntityBuilder.createSphere(new Color(242, 136, 30), new Punkt(1, 0, 500), 1392700 * groessenskale, 50));
		tempEntities.add(BasicEntityBuilder.createSphere(new Color(187, 187, 187), new Punkt(58000000f * abstandsskale, 0, 500), 4879 * groessenskale, 14)); //Merkur
		tempEntities.add(BasicEntityBuilder.createSphere(new Color(122, 86, 46), new Punkt(108000000f * abstandsskale, 0, 500), 12104 * groessenskale, 14)); //Venus
		tempEntities.add(BasicEntityBuilder.createSphere(new Color(46, 65, 106), new Punkt(150000000f * abstandsskale, 0, 500), 12104 * groessenskale, 38)); //Erde
		tempEntities.add(BasicEntityBuilder.createSphere(new Color(189, 128, 97), new Punkt(228000000f * abstandsskale, 0, 500), 12742 * groessenskale, 14)); //Mars
		tempEntities.add(BasicEntityBuilder.createSphere(new Color(201, 177, 158), new Punkt(778000000f * abstandsskale, 0, 500), 139820 * groessenskale, 14)); //Jupiter
		tempEntities.add(BasicEntityBuilder.createSphere(new Color(192, 166, 140), new Punkt(1427000000f * abstandsskale, 0, 500), 116460 * groessenskale, 14)); //Saturn
		tempEntities.add(BasicEntityBuilder.createRing(Color.GRAY, 1427000000f * abstandsskale, 0, 500, 110)); //Saturnringe
		tempEntities.add(BasicEntityBuilder.createSphere(new Color(209, 249, 244), new Punkt(2884000000f * abstandsskale, 0, 500), 50724 * groessenskale, 14)); //Uranus
		tempEntities.add(BasicEntityBuilder.createSphere(new Color(60, 82, 199), new Punkt(4509000000f * abstandsskale, 0, 500), 49244 * groessenskale, 14)); //Neptun
	}

	public void createSaturn() {
		clearentities();
		tempEntities.add(BasicEntityBuilder.createSphere(new Color(192, 166, 140), new Punkt(0, 0, 200), 120, 110)); //Saturn
		tempEntities.add(BasicEntityBuilder.createRing(Color.GRAY, 0, 0, 200, 150)); //Saturnringe
	}

}
