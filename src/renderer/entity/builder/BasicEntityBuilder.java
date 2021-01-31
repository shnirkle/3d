package renderer.entity.builder;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import renderer.entity.Entity;
import renderer.entity.iEntity;
import renderer.point.MyPoint;
import renderer.punkt.Punkt;
import renderer.shapes.MyPolygon;
import renderer.shapes.Polygon3D;
import renderer.shapes.Tetrahedron;
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
	
public static iEntity createSphere(Color c, Punkt m, double r)  {
		
	Punkt[] punkte = new Punkt[200];
	for (int i = 0; i < 200; i++) {
		punkte[i] = new Punkt(0, 0, 0);
	}
	int count = 0;
	Punkt punkt = new Punkt(0,0,0);
	double theta;
	double phi;
	double dt = (2*Math.PI)/10;
	double dp = Math.PI/10;
	Polygon3D[] polys = new Polygon3D[5000];
	for (int i = 0; i < 5000; i++) {
		polys[i] = new Polygon3D(new Punkt(0, 0, 0), new Punkt(0, 0, 0), new Punkt(0, 0, 0));
	}
	//Punktmenge der Sphäre berechnen (10 Breitengrade, 10 Längengrade)
			for(int pi = 0; pi <= 10; pi++) {
				phi = pi * dp;
				for(int ti = 0; ti <= 10; ti++) {
					theta = ti * dt;
					punkt.x = (r * Math.sin(theta) * Math.sin(phi)) + m.x;
					punkt.y = r * Math.cos(phi) + m.y;
					punkt.z = r * Math.cos(theta) * Math.sin(phi) + m.z;
					punkte[count].x = punkt.x;
					punkte[count].y = punkt.y;
					punkte[count].z = punkt.z;
					count++;
				}
			}
			count = 0;
	//Dreiecke aus der Punktmenge machen
			for(int pi = 0; pi <= 10; pi++) {
				for(int ti = 0; ti < 10; ti++) {
					int x0 = ti;
					int x1 = ti+1;
					int y0 = pi * 11;
					int y1 = (pi + 1) * 11;
					polys[count] = new Polygon3D(Color.BLUE, punkte[x0+y0], punkte[x0+y1], punkte[x1+y0]);
					polys[count+1] = new Polygon3D(Color.RED, punkte[x1+y0], punkte[x0+y1], punkte[x1+y1]);
					count += 2;
				}
			}
	//aus Dreiecken letztendliche Kugel bauen
			Würfel sphere = new Würfel(c, polys);
				
		
		List<Würfel> ws = new ArrayList<Würfel>();
		ws.add(sphere);
		
		return new Entity(ws);
	}
	
}
