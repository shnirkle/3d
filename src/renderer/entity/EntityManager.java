package renderer.entity;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import renderer.Renderer;
import renderer.entity.builder.BasicEntityBuilder;
import renderer.shapes.Objekt;
import renderer.steuerung.Eingabe;

public class EntityManager {
	private List<Objekt> entities;
	
	public int Theta = 1;
	 
	public EntityManager() {
		this.entities = new ArrayList<Objekt>();

	}

	public void init(Eingabe eingabe) {
//		this.entities.add(BasicEntityBuilder.createQuader(0, 0, 0, 1, 1, 1))
//		this.entities.add(BasicEntityBuilder.createWürfel(0, 0, 2, 1));
//		this.entities.add(BasicEntityBuilder.createWürfel(0, 0, 3, 1));
		this.entities.add(BasicEntityBuilder.createSpaceShip(0,3,6));
//		this.entities.add(BasicEntityBuilder.createDreieck(new Punkt(0,0,0), new Punkt(0,-1,0), new Punkt(1,-1,0)));

	}

	public void update() {
		Theta++;
		double ThetaB = Theta * 0.60f;
		this.rotate(180 , 180+ ThetaB, 0);
//		this.rotate(entities.get(1), 45, 45 + ThetaB, 45);
//		this.entities.get(0).aendern(0, 0, Math.sin(Math.toRadians(Theta)) * 20);
		

	}

	public void render(Graphics g) {
		Renderer.clear();
		for (Objekt obj : this.entities) {
			obj.render();
		}
		Renderer.render(g);
	}

	public void rotate( double xGrad, double yGrad, double zGrad) {
		for (Objekt obj : this.entities) {
			

			this.rotate(obj, xGrad, yGrad, zGrad);
		}
	}
	public void rotate(Objekt w, double xGrad, double yGrad, double zGrad) {
		
			xGrad = Math.toRadians(xGrad);
			yGrad = Math.toRadians(yGrad);
			zGrad = Math.toRadians(zGrad);

			w.rotate( xGrad, yGrad, zGrad);
		
	}

}
