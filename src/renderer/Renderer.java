package renderer;

import java.awt.Graphics;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import renderer.punkt.Vektor;
import renderer.shapes.Polygon3D;

public class Renderer {
	private static List<Polygon3D> polys = new ArrayList<Polygon3D>();
	public static void addPoly(Polygon3D p) {
		polys.add(p);
		Renderer.sortList();

	}

	public static void render(Graphics g) {
		for(Polygon3D  p3: polys) {
			Polygon p = new Polygon();
			g.setColor(p3.shade);
			Vektor[] pp = p3.getPrPunkte();
			p.addPoint((int) pp[0].x,(int) pp[0].y);
			p.addPoint((int) pp[1].x,(int) pp[1].y);
			p.addPoint((int) pp[2].x,(int) pp[2].y);
			g.fillPolygon(p);
		}
		
		
	}

	private static void sortList() {
		
		
	
		Collections.sort(polys, new Comparator<Polygon3D>() {
	        @Override
	        public int compare(Polygon3D p2, Polygon3D p1)
	        {
	            return Double.compare(p1.avgZ, p2.avgZ);
	        }
	    });
}

	public static void clear() {
		polys.clear();
		
	}
}
