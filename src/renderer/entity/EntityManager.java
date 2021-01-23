package renderer.entity;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import renderer.entity.builder.BasicEntityBuilder;

public class EntityManager {
	private List<iEntity> entities;
	
	public EntityManager() {
		this.entities = new ArrayList<iEntity>();
	}

	public void init() {
//		this.entities.add(BasicEntityBuilder.createWürfel(50, 0, 0, 0));
	this.entities.add(BasicEntityBuilder.createAsteroid(100, 0, 0, 0, 0.8));
	this.rotate(true, 0, 90, 0);
	}

	
	public void update() {
		this.rotate(true, 0, 0, 1);	
	}
	public void render(Graphics g) {
		for(iEntity entity : this.entities) {
			entity.render(g);
		}
	}
	private void rotate(boolean dir, double xGrad,double yGrad, double zGrad) {
		for(iEntity entity : this.entities) {
			entity.rotate(dir, xGrad, yGrad, zGrad);
		}
	}


}
