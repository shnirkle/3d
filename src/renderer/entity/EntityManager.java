package renderer.entity;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import renderer.entity.builder.BasicEntityBuilder;
import renderer.steuerung.*;

public class EntityManager {
	private List<iEntity> entities;
	public int Theta = 1;
	
	public EntityManager() {
		this.entities = new ArrayList<iEntity>();

	}

	public void init(Eingabe eingabe) {

		this.entities.add(BasicEntityBuilder.createWürfel(0, 0, 0, 1));

//		this.entities.add(BasicEntityBuilder.createDreieck(new Punkt(0,0,0), new Punkt(0,-1,0), new Punkt(1,-1,0)));

	}

	public void update() {
		Theta++;
		double ThetaB = Theta * 0.60f;
		this.rotate(0, ThetaB, Theta);
//		this.entities.get(0).aendern(Math.sin(Math.toRadians(Theta)) * 8 - 10, 0, Math.sin(Math.toRadians(Theta)) * 8 - 10);
		

	}

	public void render(Graphics g) {
		for (iEntity entity : this.entities) {
			entity.render(g);
		}
	}

	public void rotate( double xGrad, double yGrad, double zGrad) {
		for (iEntity entity : this.entities) {
			xGrad = Math.toRadians(xGrad);
			yGrad = Math.toRadians(yGrad);
			zGrad = Math.toRadians(zGrad);

			entity.rotate( xGrad, yGrad, zGrad);
		}
	}

}
