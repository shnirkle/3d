package renderer.entity;

import java.awt.Graphics;

public interface iEntity {

	
		void render(Graphics g);
		void rotate(boolean UZ, double xGrad, double yGrad, double zGrad);




}
