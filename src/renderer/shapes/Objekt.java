package renderer.shapes;

import java.awt.Color;
import java.util.List;

public class Objekt {

	private Polygon3D[] polygons;

	public Objekt(Color color, Polygon3D... polygons) {
		this.polygons = polygons;

	}

	public Objekt(Polygon3D... polygons) {
		//this.color = Color.WHITE;
		this.polygons = polygons;

	}

	public Objekt(List<Polygon3D> polys) {
		this.polygons = polys.toArray(new Polygon3D[polys.size()]);
	}

	public void render() {
		for (Polygon3D poly : this.polygons)
		{
			poly.calc();
		}
	}

		
	public void aendern(double x, double y, double z) {
		for (Polygon3D p : this.polygons)
		{
			p.aendern(x, y, z);
		}
	}

	public void rotate(double xGrad, double yGrad, double zGrad) {
		for (Polygon3D p : this.polygons)
		{
			p.rotate(xGrad, yGrad, zGrad);

		}
		this.sortieren();
	}

	private void sortieren() {
		//		Polygon3D.polygonSortieren(this.polygons);
	}

	public Polygon3D[] getPolygons() {
		return this.polygons;
	}
}
