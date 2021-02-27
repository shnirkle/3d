package renderer.shapes;

import java.awt.Color;
import java.util.List;

import renderer.punkt.Matrix;

public class Objekt {

	private Polygon3D[] polygons;
	private double xGrad, yGrad, zGrad;
	private float xOff, yOff, zOff;
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
		
		Matrix xRot = Matrix.rotateAxisX(xGrad);
		Matrix yRot = Matrix.rotateAxisY(yGrad);
		Matrix zRot = Matrix.rotateAxisZ(zGrad);
		
		Matrix mTrans = Matrix.aender(xOff, yOff, zOff);
		
		Matrix weltMat = new Matrix();
		weltMat.matrixInitialisierung();
		
		weltMat = Matrix.matrixMult(zRot, xRot);
//		weltMat = Matrix.matrixMult(yRot, weltMat);
		weltMat = Matrix.matrixMult(weltMat, mTrans);
		
		
		for (Polygon3D poly : this.polygons)
		{
			poly.calc(weltMat);
		} 
	}

		
	public void aendern(double x, double y, double z) {
		xOff += (float) x;
		yOff += (float) y;
		zOff += (float) z;
	}
	public void rotate(double xGrad, double yGrad, double zGrad) {
		this.xGrad += xGrad;
		this.yGrad += yGrad;
		this.zGrad += zGrad;
		this.sortieren();
	}

	private void sortieren() {
		//		Polygon3D.polygonSortieren(this.polygons);
	}

	public Polygon3D[] getPolygons() {
		return this.polygons;
	}
}
