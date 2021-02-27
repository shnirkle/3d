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

	public void init(Eingabe eingabe) {
		//		this.entities.add(BasicEntityBuilder.createQuader(0, 0, 0, 1, 1, 1))
		//		this.entities.add(BasicEntityBuilder.createWürfel(0, 0, 2, 1));
		//		this.entities.add(BasicEntityBuilder.createWürfel(0, 0, 1, 1));
		//		this.entities.add(BasicEntityBuilder.createSpaceShip(0,0,18));
		this.entities.add(BasicEntityBuilder.createTeapot(0, 0, 16));
		//		this.entities.add(BasicEntityBuilder.createDreieck(new Punkt(0,0,0), new Punkt(0,-1,0), new Punkt(1,-1,0)));
		this.tastatur = eingabe.tastatur;
	}

	public void update() {
		
		this.tastatur.update();

		//		Theta++;
		//		this.rotate(Theta, 0, 0);
		//		this.entities.get(0).aendern(0, 0, Math.sin(Math.toRadians(Theta)) * 20);

		if (this.tastatur.getVorne())
		{

			Kamera.forw(0.005);

			//			System.out.println("skyr");
		}
		if (this.tastatur.getHinten())
		{

			Kamera.backw(0.005);

			//			System.out.println("skyr");
		}
		if (this.tastatur.getLinks())
		{
			Kamera.rot(-0.03);
		}
		if (this.tastatur.getRechts())
		{
			Kamera.rot(0.03);
		}
		if (this.tastatur.getOben())
		{
			Kamera.up(2);
		}
		if (this.tastatur.getUnten())
		{
			Kamera.up(-2);
		}
		Kamera.updateCam();
	}

	public void render(Graphics g) {
		Renderer.clear();
		for (Objekt obj : this.entities)
		{
			obj.render();
		}
		Renderer.render(g);
	}

	public void rotate(double xGrad, double yGrad, double zGrad) {
		for (Objekt obj : this.entities)
		{

			this.rotate(obj, xGrad, yGrad, zGrad);
		}
	}

	public void rotate(Objekt w, double xGrad, double yGrad, double zGrad) {

		xGrad = Math.toRadians(xGrad);
		yGrad = Math.toRadians(yGrad);
		zGrad = Math.toRadians(zGrad);

		w.rotate(xGrad, yGrad, zGrad);

	}

}
