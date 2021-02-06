package renderer.entity;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import renderer.entity.builder.BasicEntityBuilder;
import renderer.punkt.Punkt;
import renderer.steuerung.*;

public class EntityManager {
	private List<iEntity> entities;
	public int a;
	public EntityManager() {
		this.entities = new ArrayList<iEntity>();
		
	}

	public void init(Eingabe eingabe) {

		this.entities.add(BasicEntityBuilder.createWürfel(-2, 0, 3, 1));

	}
	

	
	
	public void update() {

		this.rotate(true, 0, 0, 0.005);
		

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
