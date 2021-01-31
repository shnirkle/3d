package renderer.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import renderer.entity.builder.BasicEntityBuilder;
import renderer.punkt.Punkt;

public class EntityManager {
	private List<iEntity> entities;
	
	public EntityManager() {
		this.entities = new ArrayList<iEntity>();
	}

	public void init() {
//		this.entities.add(BasicEntityBuilder.createWürfel(50, 0, 0, 0));
	this.entities.add(BasicEntityBuilder.createSphere(Color.BLUE, new Punkt(0,0,0), 100));
//	this.rotate(true, 0, 0, 0);
	}

	
	public void update() {
		this.rotate(true, 0, 0, 0);	
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
