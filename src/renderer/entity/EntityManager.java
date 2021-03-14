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
	float speed = 0.1f;
	float sens = 0.05f;

	public void init(Eingabe eingabe) {
		//		this.entities.add(BasicEntityBuilder.createQuader(0, 0, 0, 1, 1, 1));
		//		this.entities.add(BasicEntityBuilder.createWürfel(0, 0, 10, 1));
		//		this.entities.add(BasicEntityBuilder.createWürfel(0, 0, 1, 1));
		//		this.entities.add(BasicEntityBuilder.createSpaceShip(0, 0, 8));
		this.entities.add(BasicEntityBuilder.createSpaceShip(0, 0, 0));

		this.tastatur = eingabe.tastatur;
	}

	public void update() {

		this.tastatur.update();

		Theta++;

		
		//this.entities.get(0).aendern(0, 0, (float) Math.sin(Math.toRadians(Theta)) * 20);	//ein einzelnes Objekt bewegen

		if (this.tastatur.getVorne())
		{
			Kamera.vorwaerts(speed);
		}

		if (this.tastatur.getHinten())
		{
			Kamera.vorwaerts(-speed);
		}

		if (this.tastatur.getLinks())
		{
			Kamera.rotierenLR(sens);
		}

		if (this.tastatur.getRechts())
		{
			Kamera.rotierenLR(-sens);
		}

		if (this.tastatur.getOben())
		{
			Kamera.up(-sens);
		}

		if (this.tastatur.getUnten())
		{
			Kamera.up(sens);
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
