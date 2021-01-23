package renderer.entity.builder;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import renderer.entity.Entity;
import renderer.entity.iEntity;
import renderer.punkt.Punkt;
import renderer.shapes.Polygon3D;
import renderer.shapes.Würfel;

public class BasicEntityBuilder {
	
	private static Color red = Color.RED;
	private static Color blue = Color.BLUE;
	public static iEntity createWürfel(double s, double centerX, double centerY, double centerZ) {

		Punkt p1 = new Punkt(centerX + s / 2, centerY + -s / 2, centerZ + -s / 2);
		Punkt p2 = new Punkt(centerX + s / 2, centerY + s / 2, centerZ + -s / 2); 
		Punkt p3 = new Punkt(centerX + s / 2, centerY + s / 2, centerZ + s / 2);
		Punkt p4 = new Punkt(centerX + s / 2, centerY + -s / 2, centerZ + s / 2);
		Punkt p5 = new Punkt(centerX + -s / 2, centerY + -s / 2, centerZ + -s / 2);
		Punkt p6 = new Punkt(centerX + -s / 2, centerY + s / 2, centerZ + -s / 2);
		Punkt p7 = new Punkt(centerX + -s / 2, centerY + s / 2, centerZ + s / 2);
		Punkt p8 = new Punkt(centerX + -s / 2, centerY + -s / 2, centerZ + s / 2);

		Würfel w = new Würfel(new Polygon3D(Color.PINK, p5, p6, p7, p8), new Polygon3D(Color.WHITE, p1, p2, p3, p4),
				new Polygon3D(Color.YELLOW, p1, p2, p6, p5), new Polygon3D(Color.ORANGE, p1, p5, p8, p4),
				new Polygon3D(Color.CYAN, p2, p6, p7, p3), new Polygon3D(Color.GREEN, p4, p3, p7, p8));

		List<Würfel> ws = new ArrayList<Würfel>();
		ws.add(w);
		return new Entity(ws);

	}
	
	public static iEntity createAsteroid(double s, double centerX, double centerY, double centerZ, double länge)  {
		
		Punkt p1 = new Punkt(centerX, centerY, centerZ + s * länge);
		Punkt p2 = new Punkt(centerX + s / 2, centerY + s / 2, centerZ); 
		Punkt p3 = new Punkt(centerX + s / 2, centerY - s / 2, centerZ);
		Punkt p4 = new Punkt(centerX - s / 2, centerY + s / 2, centerZ);
		Punkt p5 = new Punkt(centerX - s / 2, centerY  -s / 2, centerZ);
		Punkt p6 = new Punkt(centerX , centerY , centerZ -s * länge);
		
		Würfel w = new Würfel( 
				new Polygon3D(red, p1, p2, p3),
				new Polygon3D(p1, p2, p4),
				new Polygon3D(red, p1, p4, p5),
				new Polygon3D(p1, p5, p3),
				new Polygon3D(p6, p2, p3),
				new Polygon3D(blue, p6, p2, p4),
				new Polygon3D(p6, p4, p5),
				new Polygon3D(blue, p6, p5, p3)
				);
				
		
		List<Würfel> ws = new ArrayList<Würfel>();
		ws.add(w);
		
		return new Entity(ws);
	}
}
