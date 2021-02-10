package renderer.entity;

import java.awt.Graphics;

public interface iEntity {

	
		void render(Graphics g);
		
		void aendern(double x, double y, double z);
		
		void rotate( double xGrad, double yGrad, double zGrad);




}
