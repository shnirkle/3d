package renderer.shapes;

import java.awt.Color;
import java.util.List;

import renderer.punkt.Matrix;

public class Objekt {
	
	private Polygon3D[] polygons;
	private float xGrad, yGrad, zGrad;
	private float xOff, yOff, zOff;
	public Objekt(Color color, Polygon3D... polygons) {
		this.polygons = polygons;

	}

	//Objekt aus Polygons
	
	public Objekt(Polygon3D... polygons) {
		//this.color = Color.WHITE;
		this.polygons = polygons;

	}

	public Objekt(List<Polygon3D> polys) {
		this.polygons = polys.toArray(new Polygon3D[polys.size()]);
	}
	
	//Rendern für die Rotationen unserer Rotationsmatrixen / Weltmatrix

	public void render() {
		
		Matrix xRot = Matrix.rotateAxisX(xGrad);
		Matrix yRot = Matrix.rotateAxisY(yGrad);
		Matrix zRot = Matrix.rotateAxisZ(zGrad);
		
		Matrix mTrans = Matrix.aenderKoordinaten(xOff, yOff, zOff);
		
		Matrix weltMat = new Matrix();
		weltMat.matrixInitialisierung();
		
		weltMat = Matrix.multMatrix_Matrix(zRot, xRot);
		weltMat = Matrix.multMatrix_Matrix(yRot, weltMat);
		weltMat = Matrix.multMatrix_Matrix(weltMat, mTrans);
		
		
		for (Polygon3D poly : this.polygons)
		{
			poly.calc(weltMat);
		} 
	}

	
	//Abweichung (Offset) beim Rotieren beheben
		
	public void aendern(float x, float y, float z) {
		xOff +=x;
		yOff +=y;
		zOff +=z;
	}
	
	//Rotieren, dann sortieren
	
	public void rotate(float xGrad,float yGrad, float zGrad) {
		this.xGrad = xGrad;
		this.yGrad = yGrad;
		this.zGrad = zGrad;
		this.sortieren();
	}

	private void sortieren() {
		//		Polygon3D.polygonSortieren(this.polygons);
	}

	public Polygon3D[] getPolygons() {
		return this.polygons;
	}
}
