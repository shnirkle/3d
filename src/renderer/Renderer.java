package renderer;

import java.awt.Graphics;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import renderer.shapes.PrimTri;

public class Renderer {
	private static List<PrimTri> polys = new ArrayList<PrimTri>();
	
	public static void addPoly(PrimTri p) {
		polys.add(p);
		

	}

	public static void render(Graphics g) {
		Renderer.sortList();
		g.fillRect(0, 0, Anzeige.WIDTH * 2, Anzeige.HEIGHT * 2);
		if(polys.isEmpty()) return;
		for(PrimTri  pp: polys) {
			Polygon p = new Polygon();
			g.setColor(pp.color);
			p.addPoint((int) pp.points[0].x,(int) pp.points[0].y);
			p.addPoint((int) pp.points[1].x,(int) pp.points[1].y);
			p.addPoint((int) pp.points[2].x,(int) pp.points[2].y);
			g.fillPolygon(p);  
		}
		
		
	}

	private static void sortList() {
		
		
	
		Collections.sort(polys, new Comparator<PrimTri>() {
	        @Override
	        public int compare(PrimTri p2, PrimTri p1)
	        {
	            return Double.compare(p1.getAvgZ(), p2.getAvgZ());
	        }
	    });
}

	public static void clear() {
		polys.clear();
		
	}
}
