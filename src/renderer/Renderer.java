package renderer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import renderer.shapes.PrimTri;

public class Renderer {
	private static List<PrimTri> polys = new ArrayList<PrimTri>();
	private static boolean wireFrame = false;

	public static void addPoly(PrimTri p) {
		polys.add(p);

	}

	//Rendermethode für die Dreiecke (ursprünglich Polygone, deshalb der Name)

	public static void render(Graphics g) {
		Renderer.sortList();
		g.fillRect(0, 0, Anzeige.WIDTH * 2, Anzeige.HEIGHT * 2);
		if (polys.isEmpty())
			return;
		for (PrimTri pp : polys)
		{ //pp = Projektionspolygone
			Polygon p = new Polygon();
			if (!wireFrame)
			{
				g.setColor(pp.color);
			} else
			{
				g.setColor(Color.white);
			}
			p.addPoint((int) pp.points[0].x, (int) pp.points[0].y);
			p.addPoint((int) pp.points[1].x, (int) pp.points[1].y);
			p.addPoint((int) pp.points[2].x, (int) pp.points[2].y);
			if (!wireFrame)
			{
				g.fillPolygon(p);
			} else
			{
				g.drawPolygon(p);
			}
		}

	}

	//Sortieren der Dreiecke, um sie in die richtige Reihenfolge zum Anzeigen zu bringen
	public static void setWireFrame(boolean w) {
		wireFrame = w;
	}
	private static void sortList() {

		Collections.sort(polys, new Comparator<PrimTri>() {
			@Override
			public int compare(PrimTri p2, PrimTri p1) {
				return Double.compare(p1.getAvgZ(), p2.getAvgZ());
			}
		});
	}

	public static void clear() {
		polys.clear();

	}
}
